import org.jetbrains.annotations.NotNull;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.util.Objects;
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
    private final double delayForController;
    private final boolean withReplays;
    private final boolean styleGridWithGrid;
    private final int cellSize;

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
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
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
            assert vidmode != null;
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
        AskUser user = new AskUser(gridHeight, gridWidth);
        ControllerMouse controlMouse = new ControllerMouse(user, window, cellSize, gridHeight, gridWidth);

        while (!glfwWindowShouldClose(window)) {
            GL.createCapabilities();

            glClearColor(0.0f, 0.0f, 0.0f, 0.0f);


            // Спрашиваем, что хочет пользователь
            // 1 - игра без стен
            // 2 - игра со стенами, после выбрать уровень сложности
            // 3 - просмотр реплея, номер реплея задается кнопкой на экране, как и все...
            // 4 - рандомный уровень игры

            // Правила игры
            // ешь как можно больше бонусов
            // не стоит врезаться в себя/стены
            // Движение поддерживают кнопки-стрелки и WSAD
            // P - pause
            // O - go after pause
            windowUserInterfaceVerticalButton(0, user.allButtonsForType, glDraw, user, controlMouse);

            if (user.button == 3) {
                Replays replay = new Replays(gridHeight, gridWidth);

                user.addButtonsHorizontal(user.allButtonsOfReplays);
                windowUserInterfaceHorizontalButton(3, user.allButtonsOfReplays, glDraw, user, controlMouse);

                replay.watchReplayForStr(user.numberOfReplay); // тут номер реплея

                 ModelSnake changeForReplay = new ModelSnake(gridWidth, gridHeight, replay.masOfWalls, false);
                // модель находится тут, потому что инициализация параметров зависит от уровня игры или стен

                Controller controllerForReplays = new Controller(changeForReplay, window, delayForController, replay.allWays); ///////////////
                try {
                    windowWithReplay(changeForReplay, glDraw, controllerForReplays);
                } catch (IllegalArgumentException e) {
                    System.out.println("Game over Your score: " + changeForReplay.nowBonus * 5);
                    windowUserInterfaceHorizontalButton(2, user.allButtonsOfEndGame, glDraw, user, controlMouse);
                }
                // !!!!!!!!!! сделать красивый экран окончания

            } else {
                if (user.button == 2) user.addButtonsVertical(user.allButtonsForLevel);

                switch (user.button) {
                    case (1):
                        user.level = 0;
                        break;
                    case (2):
                        user.addButtonsVertical(user.allButtonsForLevel);
                        windowUserInterfaceVerticalButton(1, user.allButtonsForLevel, glDraw, user, controlMouse);
                        break;
                    case (4):
                        //!!!!!!!!!! сделать картинку с котиком
                        Random rand = new Random();
                        if (rand.nextBoolean()) {
                            user.level = 0;
                        } else {
                            user.addButtonsVertical(user.allButtonsForLevel);
                            Random random = new Random();
                            user.level = random.nextInt(4);
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("Error cant be here" + user.button);
                }

                ModelSnake change = new ModelSnake(gridWidth, gridHeight, Wall.newRandomWallsForGrid(gridHeight, gridWidth, user.level * 25), withReplays);
                Controller controller = new Controller(change, window, delayForController, "");

                try {
                    windowWithGame(change, glDraw, controller);
                } catch (IllegalArgumentException e) {
                    System.out.println("Game over Your score: " + change.nowBonus * 5);
                    windowUserInterfaceHorizontalButton(2, user.allButtonsOfEndGame, glDraw, user, controlMouse);
                }
            }

            if (user.end == user.allButtonsOfEndGame.length) glfwSetWindowShouldClose(window, true);
            else if (user.end == 1) {
                user = new AskUser(gridHeight, gridWidth);
                controlMouse = new ControllerMouse(user, window, cellSize, gridHeight, gridWidth);
            }
        }
    }

    private void windowWithGame(ModelSnake change, GlDraw glDraw, Controller controller) {
        Replays replay = new Replays(gridHeight, gridWidth);
        if (withReplays) {
            replay.writeParameters((change.masOfWalls.length != 0), change.masOfWalls, change.masOfBonuses);
        }
        while (!glfwWindowShouldClose(window)) { // Окно с самой игрой
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            glDraw.drawGame(styleGridWithGrid, change, controller);
            glfwSwapBuffers(window); // swap the color buffers
            controller.control();
            if (withReplays) replay.writeWays(change.lastWayNotMe, controller.delay); /////////////////////

            glfwPollEvents();
        }
    }

    private void windowWithReplay(ModelSnake changeForReplay, GlDraw glDraw, Controller controllerForReplays) {
        while (!glfwWindowShouldClose(window)) { // Окно реплея
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            controllerForReplays.controllerForReplays();
            glDraw.drawGame(styleGridWithGrid, changeForReplay, controllerForReplays);
            glfwSwapBuffers(window); // swap the color buffers


            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

    private void windowUserInterfaceVerticalButton(int arg, Button[] masOfButton, GlDraw glDraw, @NotNull AskUser user, ControllerMouse controlMouse) {
        user.addButtonsVertical(user.allButtonsForLevel);
        int counter = 9;

        while (counter == 9) { // Окно выбора уровня игры

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            glColor3f(1.0f, 0.0f, 0.0f);

            glDraw.askUserLevelInButton(masOfButton);
            controlMouse.checkMouse(arg, masOfButton);
            switch (arg) {
                case (0):
                    counter = user.button;
                    break;
                case (1):
                    counter = user.level;
                    break;
                case (2):
                    counter = user.end;
                    break;

            }

            glfwSwapBuffers(window); // swap the color buffers


            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        } // само окно выбора уровня игры со стенами
    }

    private void windowUserInterfaceHorizontalButton(int arg, Button[] masOfButton, GlDraw glDraw, @NotNull AskUser user, ControllerMouse controlMouse) {


        // !!!!!!!!!! сделать красивый экран окончания
        user.addButtonsHorizontal(masOfButton);
        int counter = 9;
        while (counter == 9) { // Окно выбора действия после игры (1 - основное меню; 2 - выход из игры)
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            glColor3f(1.0f, 1.0f, 0.0f);

            glDraw.askUserLevelInButton(masOfButton);
            controlMouse.checkMouse(arg, masOfButton);

            switch (arg) {
                case (0):
                    counter = user.button;
                    break;
                case (1):
                    counter = user.level;
                    break;
                case (2):
                    counter = user.end;
                    break;
                case (3):
                    counter = user.numberOfReplay;
                    break;
            }
            glfwSwapBuffers(window); // swap the color buffers

            glfwPollEvents();
        }
    }

}