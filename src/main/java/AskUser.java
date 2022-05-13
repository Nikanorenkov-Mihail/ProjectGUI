import org.jetbrains.annotations.NotNull;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glColor3f;

public class AskUser {
    Button[] allButtonsForType = new Button[4]; // меняем количество кнопок
    Button[] allButtonsForLevel = new Button[5];
    Button[] allButtonsOfEndGame = new Button[2];
    private int gridHeight, gridWidth;
    int button = 9;
    int level = 9;
    int end = 9;

    public AskUser(int gridHeight, int gridWidth) {
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
        addButtonsVertical(allButtonsForType);
    }

    public void addButtonsVertical(Button @NotNull [] allButtons) {
        for (int i = 0; i < allButtons.length; i++) {
            allButtons[i] = new Button(1, (i == 0) ? gridHeight - 1 : gridHeight / allButtons.length * (allButtons.length - i),
                    gridWidth / 2, (i == 0) ? gridHeight - 1 : gridHeight / allButtons.length * (allButtons.length - i),
                    gridWidth / 2, (i == allButtons.length - 1) ? 1 : gridHeight / allButtons.length * (allButtons.length - 1 - i),
                    1, (i == allButtons.length - 1) ? 1 : gridHeight / allButtons.length * (allButtons.length - 1 - i));

        }
    }

    public void addButtonsHorizontal(Button @NotNull [] allButtons) {
        for (int i = 0; i < allButtons.length; i++) {
            allButtons[i] = new Button(((i == 0) ? 2 : gridWidth / allButtons.length * i) - 1, gridHeight / 3 * 2,
                    (gridWidth / allButtons.length * (i + 1)) - 1, gridHeight / 3 * 2,
                    (gridWidth / allButtons.length * (i + 1)) - 1, gridHeight / 3,
                    ((i == 0) ? 2 : gridWidth / allButtons.length * i) - 1, gridHeight / 3);
        }
    }

    public int clickOnButton(double clickX, double clickY, Button @NotNull [] allButtons) {
        for (int i = 0; i < allButtons.length; i++) {
            System.out.println(allButtons[i].x1 + " " + allButtons[i].y1);
            System.out.println(clickX + " " + clickY);
            if (clickX >= allButtons[i].x1 && clickX <= allButtons[i].x2) {
                if (clickY >= allButtons[i].y4 && clickY <= allButtons[i].y2) {
                    return i;
                }

            }

        }
        return 9;
    }

    public int startWindow(GlDraw glDraw, @NotNull AskUser user, ControllerMouse controlMouse) {
        while (user.button == 9) { // интерфейс пользователя перед игрой

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            glColor3f(1.0f, 0.0f, 0.0f);
            glDraw.askUserInButton(user.allButtonsForType);
            controlMouse.checkMouse(0);
            glfwSwapBuffers(controlMouse.window1); // swap the color buffers


            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        } // само окно начала игры
        return 1;
    }
}
