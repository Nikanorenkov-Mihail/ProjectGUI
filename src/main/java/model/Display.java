package model;

import controller.Controller;
import controller.ControllerMouse;
import functions.Replays;
import graphic.GlDraw;
import graphic.Texture;
import model.user.Button;
import model.user.ModelUser;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.awt.image.BufferedImage;
import java.nio.*;
import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * все действия происходят тут
 */
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
    private final GlDraw glDraw;
    private ModelUser user;
    private ControllerMouse controlMouse;
    private ModelMainSnake changeModel;
    private Controller controller;
    private boolean isItTest;

    public Display(int width, int height,
                   int gridWidth,
                   int gridHeight,
                   String name,
                   int cellSize,
                   boolean withReplays,
                   double delayForController,
                   boolean styleGridWithGrid,
                   GlDraw glDraw,
                   ModelUser user,
                   ControllerMouse controlMouse,
                   ModelMainSnake changeModel,
                   Controller controller,
                   boolean isItTest) {
        this.height = height;
        this.width = width;
        this.name = name;
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
        this.cellSize = cellSize;
        this.withReplays = withReplays;
        this.delayForController = delayForController;
        this.styleGridWithGrid = styleGridWithGrid;
        this.glDraw = glDraw;
        this.user = user;
        this.controlMouse = controlMouse;
        this.changeModel = changeModel;
        this.controller = controller;
        this.isItTest = isItTest;
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

        //glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
        //  if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
        //      glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        //});

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

        //glfwSwapInterval(1);
        // Make the window visible
        glfwShowWindow(window);
    }

    private void loop() {

        while (!glfwWindowShouldClose(window)) {

            GL.createCapabilities();
            glEnable(GL_TEXTURE_2D);
            glEnable(GL_BLEND);

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            // Tests
            if (isItTest) {
                windowUserInterfaceHorizontalButton(3, glDraw, user, controlMouse, Replays.numberOfReplaysTest(), true);

                changeModel.gameOrReplay(true, user, true); // модель для реплея и игры общая, функция лишь меняет некоторые параметры
                try {
                    windowWithReplay(changeModel, glDraw, controller);
                } catch (IllegalArgumentException e) {
                    System.out.println("Something Wrong");
                }
            } else {

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

                windowUserInterfaceVerticalButton(0, glDraw, user, controlMouse, 4, false);

                if (user.button == 3) {
                    if (Replays.numberOfReplays() == 0) {
                        System.out.println("Directory with replays is empty");

                    } else {
                        windowUserInterfaceHorizontalButton(3, glDraw, user, controlMouse, Replays.numberOfReplays(), true);

                        changeModel.gameOrReplay(true, user, false); // модель для реплея и игры общая, функция лишь меняет некоторые параметры

                        try {
                            windowWithReplay(changeModel, glDraw, controller);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Game over Your score: " + ((changeModel.nowBonus == 0) ? 1 : changeModel.nowBonus * 5));
                        }

                    }
                } else {
                    if (user.button == 2) {
                        windowUserInterfaceVerticalButton(1, glDraw, user, controlMouse, 5, true);
                    } else {
                        user.whatButtonInStart(user);
                    }
                    changeModel.gameOrReplay(false, user, false);

                    try {
                        windowWithGame(changeModel, glDraw, controller);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Game over Your score: " + ((changeModel.nowBonus == 0) ? 1 : changeModel.nowBonus * 5));
                    }
                }
            }
            windowUserInterfaceHorizontalButton(2, glDraw, user, controlMouse, 2, false);

            if (user.end == user.numButtons)
                glfwSetWindowShouldClose(window, true);
            else if (user.end == 1) {
                toStartValue(); // новая игра, возвращаем все модели к первоначальному состоянию
            }
        }
    }
    // на каждое действие после выбора пользователя новое окно(сцена)

    /**
     * в этом окне происходит действие игры
     *
     * @param change     модель
     * @param glDraw     отрисовка
     * @param controller контроллер
     *                   все разделено
     */
    private void windowWithGame(ModelMainSnake change, GlDraw glDraw, Controller controller) {
        Replays replay = new Replays(gridHeight, gridWidth);
        if (withReplays) {
            replay.writeParameters((change.masOfWalls.length != 0), change.masOfWalls, change.masOfBonuses);
        }

        while (!glfwWindowShouldClose(window)) { // Окно с самой игрой
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            glDraw.drawGame(styleGridWithGrid, change);
            change.lastWay = controller.control(window);

            if (!controller.pauseControl && controller.isTimeToChangeWay())
                change.newWay();// говорим змейке двигаться в нужный delay без паузы
            if (withReplays) replay.writeWays(change.lastWayNotMe, controller.delay); // опять реплеи

            glfwSwapBuffers(window); // swap the color buffers
            glfwPollEvents();
        }
    }

    /**
     * в этом окне происходит показ реплея
     *
     * @param changeForReplay      модель реплея
     * @param glDraw               отрисовка
     * @param controllerForReplays контроллер
     *                             все разделено
     */
    private void windowWithReplay(@NotNull ModelMainSnake changeForReplay, GlDraw glDraw, @NotNull Controller controllerForReplays) {
        controllerForReplays.whatWayInReplay(changeForReplay.allWays);
        while (!glfwWindowShouldClose(window)) { // Окно реплея
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            glDraw.drawGame(styleGridWithGrid, changeForReplay);

            if (!controllerForReplays.pauseControl && controllerForReplays.isTimeToChangeWay()) {
                changeForReplay.lastWay = controllerForReplays.controllerForReplays(window);
                changeForReplay.newWay();// говорим змейке двигаться в нужный delay без паузы
            }

            glfwSwapBuffers(window); // swap the color buffers
            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

    /**
     * тут мы спрашиваем пользователя, просим куда-нибудь тыкнуть, сделать выбор
     * в данном случае выбор вертикальный
     *
     * @param arg          в зависимости от этого меняем модель пользователя
     * @param glDraw       отрисовка
     * @param user         сам юзер, его модель
     * @param controlMouse контроллер мышки, для тыкания пользователем по кнопкам
     * @param numOfButtons количество кнопок
     * @param isForLevel   важно для отрисовки
     */
    private void windowUserInterfaceVerticalButton(int arg, GlDraw glDraw, @NotNull ModelUser user, ControllerMouse controlMouse, int numOfButtons, boolean isForLevel) {

        user.addButtonsVertical1(numOfButtons);
        int counter = 9;

        while (counter == 9) { // Окно выбора уровня игры

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            counter = controlMouse.checkMouse(user, window);

            glDraw.askUserLevelInButton11(user, user.numButtons, (isForLevel) ? 2 : 1);

            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        } // само окно выбора уровня игры со стенами

        switch (arg) {
            case (0):
                user.button = counter;
                break;
            case (1):
                user.level = counter;
                break;
            case (2):
                user.end = counter;
                break;
            case (3):
                user.numberOfReplay = counter;
                break;
            default:
                break;
        }
    }

    /**
     * тут мы спрашиваем пользователя, просим куда-нибудь тыкнуть, сделать выбор
     * в данном случае выбор горизонтальный
     *
     * @param arg          в зависимости от этого меняем модель пользователя
     * @param glDraw       отрисовка
     * @param user         сам юзер, его модель
     * @param controlMouse контроллер мышки, для тыкания пользователем по кнопкам
     * @param numOfButtons количество кнопок
     */
    private void windowUserInterfaceHorizontalButton(int arg, GlDraw glDraw, @NotNull ModelUser user, ControllerMouse controlMouse, int numOfButtons, boolean isReplay) {


        user.addButtonsHorizontal1(numOfButtons);
        int counter = 9;

        while (counter == 9) { // Окно выбора уровня игры

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            glDraw.askUserLevelInButton11(user, user.numButtons, (isReplay) ? 9 : 3);
            counter = controlMouse.checkMouse(user, window);

            // уюирать текстуру картинку тут
            //glDraw.drawImage(35,85,45,85, 45,80,35,80, 13);
            glDraw.drawImage(35, 10, 45, 10, 45, 5, 35, 5, 12);


            glfwSwapBuffers(window); // swap the color buffers
            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        } // само окно выбора уровня игры со стенами

        switch (arg) {
            case (0):
                user.button = counter;
                break;
            case (1):
                user.level = counter;
                break;
            case (2):
                user.end = counter;
                break;
            case (3):
                user.numberOfReplay = counter;
                break;
            default:
                break;
        }
    }

    /**
     * функция позволяет в конце игры продолжить заниматься любимым делом - играть в Змейку
     * просто берет и заново создает модель, контроллеры, отрисовку
     */
    private void toStartValue() {
        System.out.println(gridHeight + " " + gridWidth);
        user = new ModelUser(gridHeight, gridWidth);
        changeModel = new ModelMainSnake(gridHeight, gridWidth, withReplays);
        controlMouse = new ControllerMouse(user, cellSize, gridHeight, gridWidth);
        controller = new Controller(changeModel, delayForController);

        glEnd();
        //System.out.println(changeModel.gridHeight + " " + changeModel.gridWidth);
    }
}