import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.util.Random;
import java.util.Scanner;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class HelloGL {

    // The window handle
    private long window;
    private final int width, height;
    private final int gridWidth, gridHeight;
    private final String name;
    private Wall[] walls;

    HelloGL(int width, int height, int gridWidth, int gridHeight, String name, int sizeOfWalls) {
        this.height = height;
        this.width = width;
        this.name = name;
        this.walls = Wall.newRandomWallsForGrid(gridHeight, gridWidth, sizeOfWalls);
        ;
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
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

        GL.createCapabilities();

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        GlDraw glDraw = new GlDraw(gridHeight, gridWidth);

        AllBonuses allBonuses = new AllBonuses();
        allBonuses.addRandoBonuses(gridHeight, gridWidth);
        SnakesChanges change = new SnakesChanges(gridWidth, gridHeight, walls, allBonuses.bonusesExist);

        Controller controller = new Controller(change, window);

        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            glDraw.drawWalls(walls);
            glDraw.drawBonus(allBonuses.bonusesExist[change.nowBonus]); // как только бонус съедят, достанем другой из массива

            glDraw.drawSnake(change);

            glfwSwapBuffers(window); // swap the color buffers

            controller.control();

            glDraw.drawFPS();

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

}