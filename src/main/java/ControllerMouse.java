import static org.lwjgl.glfw.GLFW.*;

public class ControllerMouse {
    long window1;
    boolean pauseControl = false;
    double delay;
    int counterForDelay = 0;
    double lastTimeForKeys = 0.0;
    int counterForStr = 0;
    AskUser user;
    int cellSize;
    int gridHeight;
    int gridWidth;


    public ControllerMouse(AskUser user, long window, int cellSize, int gridHeight, int gridWidth) {
        this.window1 = window;
        this.user = user;
        this.cellSize = cellSize;
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
    }

    public void checkMouse() { // сделать

        glfwSetCursorPosCallback(window1, (window1, posX, posY) -> {

            glfwSetKeyCallback(window1, (window, key, scancode, action, mods) -> {
                if (key == GLFW_KEY_ESCAPE) stop(window);

            });

            switch (user.clickOnButton(posX / cellSize, gridHeight - (posY / cellSize))) { // переворачиваем Y
                case (0):
                    glfwSetMouseButtonCallback(window1, (window, button, action, mods) -> {
                        if (button == GLFW_MOUSE_BUTTON_LEFT) {
                            user.button = 1;
                        }
                    });
                    break;

                case (1):
                    glfwSetMouseButtonCallback(window1, (window, button, action, mods) -> {
                        if (button == GLFW_MOUSE_BUTTON_LEFT) {
                            user.button = 2;
                        }
                    });
                    break;

                case (2):
                    glfwSetMouseButtonCallback(window1, (window, button, action, mods) -> {
                        if (button == GLFW_MOUSE_BUTTON_LEFT) {
                            user.button = 3;
                        }
                    });
                    break;
            }
        });
    }

    private void stop(long wind) {
        glfwSetWindowShouldClose(wind, true);
    }
}
