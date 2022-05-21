package controller;

import model.user.Button;
import model.user.ModelUser;
import org.jetbrains.annotations.NotNull;

import static org.lwjgl.glfw.GLFW.*;

public class ControllerMouse {
    long window1;
    ModelUser user;
    int cellSize;
    int gridHeight;
    int gridWidth;
    boolean isButtonRelease = true;

    public ControllerMouse(ModelUser user, int cellSize, int gridHeight, int gridWidth) {
        this.user = user;
        this.cellSize = cellSize;
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
    }

    private int clickOnButton(double clickX, double clickY, Button @NotNull [] allButtons) {
        for (int i = 0; i < allButtons.length; i++) {
            if (clickX >= allButtons[i].x1 && clickX <= allButtons[i].x2) {
                if (clickY >= allButtons[i].y4 && clickY <= allButtons[i].y2) {
                    return i;
                }
            }
        }
        return 8;
    }

    public int checkMouse(Button[] masOfButtons, long wind) { // сделать ретюрн
        this.window1 = wind;
        double[] masX = {0.0}, masY = {0.0};
        glfwGetCursorPos(window1, masX, masY);
        if (glfwGetMouseButton(window1, GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS && !isButtonRelease) {
            isButtonRelease = true;
            System.out.println(masX[0] + " " + masY[0]);
            return clickOnButton(masX[0] / cellSize, gridHeight - (masY[0] / cellSize), masOfButtons) + 1;
        }
        if (glfwGetMouseButton(window1, GLFW_MOUSE_BUTTON_LEFT) == GLFW_RELEASE) {
            isButtonRelease = false;
        }
        return 9;
    }

    private void stop() {
        throw new IllegalArgumentException("out");
    }
}