import static org.lwjgl.glfw.GLFW.*;

public class ControllerMouse {
    long window1;

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

    public void checkMouse(boolean typeOrLevel) {
        glfwSetCursorPosCallback(window1, (window1, posX, posY) -> {

            glfwSetKeyCallback(window1, (window, key, scancode, action, mods) -> {
                if (key == GLFW_KEY_ESCAPE) stop(window);
            });

            glfwSetMouseButtonCallback(window1, (window, button, action, mods) -> {
                if (button == GLFW_MOUSE_BUTTON_LEFT) {
                    if (typeOrLevel) {
                        user.button = user.clickOnButton(posX / cellSize, gridHeight - (posY / cellSize)) + 1;
                    } else {
                        user.level = user.clickOnButton(posX / cellSize, gridHeight - (posY / cellSize)) + 1;
                    }
                }

            });
        });


    }

    public void controlForLevels() {

    }

    private void stop(long wind) {
        glfwSetWindowShouldClose(wind, true);
    }
}
