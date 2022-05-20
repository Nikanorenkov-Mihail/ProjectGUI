package model.user;

import functions.Replays;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class ModelUser { // Модель пользователя для интерфейса
    // Объект пользователь создается каждый раз после игры
    public Button[] allButtonsForType = new Button[4]; // можем менять количество кнопок
    public Button[] allButtonsForLevel = new Button[5];
    public Button[] allButtonsOfEndGame = new Button[2];
    public Button[] allButtonsOfReplays = new Button[Replays.numberOfReplays()];
    int gridHeight, gridWidth;
    public int button = 9;
    public int level = 9;
    public int end = 9;
    public int numberOfReplay = 9;

    public ModelUser(int gridHeight, int gridWidth) {
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
        addButtonsVertical(allButtonsForType);
    }

    public int argument(int arg, double posX, double posY, int cellSize, Button[] allButtons) {

        if (arg == 0) { // для мышки нужно перевернуть координаты
            button = clickOnButton(posX / cellSize, gridHeight - (posY / cellSize), allButtons) + 1;
        } else if (arg == 1) {
            level = clickOnButton(posX / cellSize, gridHeight - (posY / cellSize), allButtons) + 1;

        } else if (arg == 2) {
            end = clickOnButton(posX / cellSize, gridHeight - (posY / cellSize), allButtons) + 1;
        } else if (arg == 3) {
            numberOfReplay = clickOnButton(posX / cellSize, gridHeight - (posY / cellSize), allButtons) + 1;
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
            if (clickX >= allButtons[i].x1 && clickX <= allButtons[i].x2) {
                if (clickY >= allButtons[i].y4 && clickY <= allButtons[i].y2) {
                    return i;
                }
            }
        }
        return 8;
    }

    public static boolean isRightNumber(int numberOfReplay) {
        return numberOfReplay > 0 && numberOfReplay <= Replays.numberOfReplays();
    }

    public void whatButtonInStart(@NotNull ModelUser user) {
        switch (user.button) {
            case (1):
                user.level = 0;
                break;
            case (4):
                //!!!!!!!!!! сделать картинку с котиком
                Random rand = new Random();
                if (rand.nextBoolean()) {
                    user.level = 0; // игра без стен
                } else {
                    user.addButtonsVertical(user.allButtonsForLevel);
                    Random random = new Random();
                    user.level = random.nextInt(4);
                }
                break;
            default:
                throw new IllegalArgumentException("Error cant be here" + user.button + " " + user.allButtonsForType.length);
        }
    }
}
