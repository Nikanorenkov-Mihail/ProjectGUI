import org.jetbrains.annotations.NotNull;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glColor3f;

public class AskUser { // Модель пользователя для интерфейса
    // Объект пользователь создается каждый раз после игры
    Button[] allButtonsForType = new Button[4]; // меняем количество кнопок
    Button[] allButtonsForLevel = new Button[5];
    Button[] allButtonsOfEndGame = new Button[2];
    Button[] allButtonsOfReplays = new Button[Replays.numberOfReplays()];
    private int gridHeight, gridWidth;
    int button = 9;
    int level = 9;
    int end = 9;
    int numberOfReplay = 9;

    public AskUser(int gridHeight, int gridWidth) {
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
        addButtonsVertical(allButtonsForType);
    }

    public int argument(int arg, double posX, double posY, int cellSize, Button[] allButtons) {
        switch (arg) {
            case (0):
                button = clickOnButton(posX / cellSize, gridHeight - (posY / cellSize), allButtons) + 1;
                break;
            case (1):
                level = clickOnButton(posX / cellSize, gridHeight - (posY / cellSize), allButtons) + 1;
                break;
            case (2):
                end = clickOnButton(posX / cellSize, gridHeight - (posY / cellSize), allButtons) + 1;
                break;
            case (3):
                numberOfReplay = clickOnButton(posX / cellSize, gridHeight - (posY / cellSize), allButtons) + 1;
            default:
                return 9;
        }
        return 9;
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

    public boolean isRightNumber(int numberOfReplay) {
        return numberOfReplay > 0 && numberOfReplay <= Replays.numberOfReplays();
    }
}
