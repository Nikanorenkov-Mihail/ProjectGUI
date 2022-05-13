import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.util.Random;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Display {

    // какие-то настройки игры, задаются в main

    // The window handle
    private long window;
    private final int width, height;
    private final int gridWidth, gridHeight;
    private final String name;
    private double delayForController;
    private boolean withReplays;
    private boolean styleGridWithGrid;
    private int cellSize;

    Display(int width, int height, int gridWidth, int gridHeight, String name, int cellSize, boolean withReplays, double delayForController, boolean styleGridWithGrid) {
        this.height = height;
        this.width = width;
        this.name = name;
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
        this.cellSize = cellSize;
        this.withReplays = withReplays;
        this.delayForController = delayForController;
        this.styleGridWithGrid = styleGridWithGrid;
    }

    public void run() {
        System.out.println(name + " launched with LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        window = glfwCreateWindow(width, height, name, NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            glfwGetWindowSize(window, pWidth, pHeight);

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically


        glfwMakeContextCurrent(window);

        glfwSwapInterval(1);
        // Make the window visible
        glfwShowWindow(window);
    }

    private void loop() {
        GlDraw glDraw = new GlDraw(gridHeight, gridWidth);
        while (!glfwWindowShouldClose(window)) {
            GL.createCapabilities();

            glClearColor(0.0f, 0.0f, 0.0f, 0.0f);


            // спрашиваем, что хочет пользователь
            // 1 - игра без стен
            // 2 - игра со стенами
            // 3 - просмотр реплея !!! задать номер реплея для просмотра
            // 4 - рандомный уровень игры
            // P - pause
            // O - go after pause
            AskUser user = new AskUser(gridHeight, gridWidth);
            ControllerMouse controlMouse = new ControllerMouse(user, window, cellSize, gridHeight, gridWidth);

            while (user.button == 9) { // // Окно интерфейса с запросом на выбор действия

                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
                glColor3f(1.0f, 0.0f, 0.0f);
                glDraw.askUserInButton(user.allButtonsForType);
                controlMouse.checkMouse(0);
                glfwSwapBuffers(window); // swap the color buffers


                // Poll for window events. The key callback above will only be
                // invoked during this call.
                glfwPollEvents();
            } // само окно начала игры

            if (user.button == 3) {
                Replays replay = new Replays();
                int numberOfReplay = 9;
                user.addButtonsHorizontal(user.allButtonsForLevel);
                while (user.level == 9) {  // Окно выбора реплея ! Нужно сделать нормальный запрос на номер реплея

                    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
                    glColor3f(1.0f, 1.0f, 0.0f);
                    glDraw.askUserLevelInButton(user.allButtonsForLevel);
                    controlMouse.checkMouse(1);
                    glfwSwapBuffers(window); // swap the color buffers


                    // Poll for window events. The key callback above will only be
                    // invoked during this call.
                    glfwPollEvents();
                } // само окно начала игры

                replay.watchReplayForStr(user.level); // тут номер реплея
                ModelSnake changeForReplay = new ModelSnake(gridWidth, gridHeight, replay.masOfWalls, replay.bonus.bonusesExist, false);
                Controller controllerForReplays = new Controller(changeForReplay, window, delayForController, replay.allWays); ///////////////
                try {
                    while (!glfwWindowShouldClose(window)) { // Окно реплея
                        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
                      /*  if (styleGridWithGrid) glDraw.drawGrid();
                        glDraw.drawWalls(changeForReplay.masOfWalls);
                        glDraw.drawBonus(changeForReplay.masOfBonuses[changeForReplay.nowBonus]); // как только бонус съедят, достанем другой из массива

                        glDraw.drawSnake(changeForReplay);
                       */
                        controllerForReplays.controllerForReplays();
                        glDraw.drawGame(styleGridWithGrid, changeForReplay, controllerForReplays);
                        glfwSwapBuffers(window); // swap the color buffers


                        // Poll for window events. The key callback above will only be
                        // invoked during this call.
                        glfwPollEvents();
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Game over Your score: " + changeForReplay.nowBonus * 5);
                }

                // !!!!!!!!!! сделать красивый экран окончания

            } else {

                AllBonuses allBonuses = new AllBonuses();
                allBonuses.addRandoBonuses(gridHeight, gridWidth);

                if (user.button == 2) user.addButtonsVertical(user.allButtonsForLevel);

                switch (user.button) {
                    case (1):
                        user.level = 0;
                        break;
                    case (2):
                        user.addButtonsVertical(user.allButtonsForLevel);
                        while (user.level == 9) { // Окно выбора уровня игры

                            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
                            glColor3f(1.0f, 0.0f, 0.0f);

                            glDraw.askUserLevelInButton(user.allButtonsForLevel);
                            controlMouse.checkMouse(1);


                            glfwSwapBuffers(window); // swap the color buffers


                            // Poll for window events. The key callback above will only be
                            // invoked during this call.
                            glfwPollEvents();
                        } // само окно выбора уровня игры со стенами
                        break;
                    case (4):
                        //!!!!!!!!!! сделать картинку с котиком
                        Random rand = new Random();
                        if (rand.nextBoolean()) {
                            user.level = 0;
                        } else {
                            user.addButtonsVertical(user.allButtonsForLevel);
                            //user.addAllQuestionsForLevelOfGameWithWalls();
                            Random random = new Random();
                            user.level = random.nextInt(4);
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("Error cant be here" + user.button);
                }

                ModelSnake change = new ModelSnake(gridWidth, gridHeight, Wall.newRandomWallsForGrid(gridHeight, gridWidth, user.level * 25), allBonuses.bonusesExist, withReplays);

                Controller controller = new Controller(change, window, delayForController, "");


                try {
                    Replays replay = new Replays();
                    if (withReplays) {
                        replay.writeParameters((change.masOfWalls.length != 0), change.masOfWalls, allBonuses);
                    }
                    while (!glfwWindowShouldClose(window)) { // Окно с самой игрой
                        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
                       /* if (styleGridWithGrid) glDraw.drawGrid();
                        glDraw.drawWalls(change.masOfWalls);
                        glDraw.drawBonus(allBonuses.bonusesExist[change.nowBonus]); // как только бонус съедят, достанем другой из массива

                        glDraw.drawSnake(change);*/
                        glDraw.drawGame(styleGridWithGrid, change, controller);
                        glfwSwapBuffers(window); // swap the color buffers

                        controller.control();


                        if (withReplays) replay.writeWays(change.lastWayNotMe, controller.delay); /////////////////////

                        // Poll for window events. The key callback above will only be
                        // invoked during this call.
                        glfwPollEvents();
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Game over Your score: " + change.nowBonus * 5);

                    // !!!!!!!!!! сделать красивый экран окончания
                    user.addButtonsHorizontal(user.allButtonsOfEndGame);
                    while (user.end == 9) { // интерфейс после игры

                        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
                        glColor3f(1.0f, 1.0f, 0.0f);
                        glDraw.askUserLevelInButton(user.allButtonsOfEndGame);
                        controlMouse.checkMouse(2);
                        glfwSwapBuffers(window); // swap the color buffers


                        // Poll for window events. The key callback above will only be
                        // invoked during this call.
                        glfwPollEvents();
                    }

                    if (user.end == user.allButtonsOfEndGame.length) glfwSetWindowShouldClose(window, true);

                }

            }
        }
    }
}