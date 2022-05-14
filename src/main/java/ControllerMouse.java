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

    public void checkMouse(int arg, Button[] masOfButtons) {
        glfwSetCursorPosCallback(window1, (window1, posX, posY) -> {

            glfwSetMouseButtonCallback(window1, (window, button, action, mods) -> {
                user.argument(arg, posX, posY, cellSize, masOfButtons);
               /* if (button == GLFW_MOUSE_BUTTON_LEFT) {
                    if (us == 0) {
                        user.button = user.clickOnButton(posX / cellSize, gridHeight - (posY / cellSize), user.allButtonsForType) + 1;
                    } else if (us == 1) {
                        user.level = user.clickOnButton(posX / cellSize, gridHeight - (posY / cellSize), user.allButtonsForLevel) + 1;

                    } else if (us == 2) {
                        user.end = user.clickOnButton(posX / cellSize, gridHeight - (posY / cellSize), user.allButtonsOfEndGame) + 1;
                    }
                }*/

            });
        });


    }


    private void stop(long wind) {
        glfwSetWindowShouldClose(wind, true);
    }
}
