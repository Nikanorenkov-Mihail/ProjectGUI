package model.user;

import functions.Replays;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class ModelUser { // Модель пользователя для интерфейса
    // Объект пользователь создается каждый раз после игры

    int gridHeight, gridWidth;
    public int button = 9;
    public int level = 9;
    public int end = 9;
    public int numberOfReplay = 9;

    // для одной функции кнопок
    public int numButtons = 0;
    public Button[] numMasButton = new Button[10];

    public ModelUser(int gridHeight, int gridWidth) {
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
    }

    /**
     * функция временная, каждый раз будет заново создавать кнопки
     *
     * @param numForButton создаем нужное количство кнопок
     */
    public void addButtonsVertical1(int numForButton) {
        numButtons = numForButton;
        for (int i = 0; i < numButtons; i++) {
            numMasButton[i] = new Button(1, (i == 0) ? gridHeight - 1 : gridHeight / numButtons * (numButtons - i),
                    gridWidth / 2, (i == 0) ? gridHeight - 1 : gridHeight / numButtons * (numButtons - i),
                    gridWidth / 2, (i == numButtons - 1) ? 1 : gridHeight / numButtons * (numButtons - 1 - i),
                    1, (i == numButtons - 1) ? 1 : gridHeight / numButtons * (numButtons - 1 - i));
        }
    }

    /**
     * функция временная, каждый раз будет заново создавать кнопки
     *
     * @param numForButton создаем нужное количство кнопок
     */
    public void addButtonsHorizontal1(int numForButton) {
        numButtons = numForButton;
        for (int i = 0; i < numForButton; i++) {
            numMasButton[i] = new Button(((i == 0) ? 2 : gridWidth / numForButton * i) - 1, gridHeight / 3 * 2,
                    (gridWidth / numForButton * (i + 1)) - 1, gridHeight / 3 * 2,
                    (gridWidth / numForButton * (i + 1)) - 1, gridHeight / 3,
                    ((i == 0) ? 2 : gridWidth / numForButton * i) - 1, gridHeight / 3);
        }
    }

    public static boolean isRightNumber(int numberOfReplay) {
        return numberOfReplay > 0 && numberOfReplay <= Replays.numberOfReplays();
    }

    public static boolean isRightNumberTest(int numberOfReplay) {
        return numberOfReplay > 0 && numberOfReplay <= Replays.numberOfReplaysTest();
    }

    /**
     * принимаем действия пользователя
     *
     * @param user этого пользователя слушаем
     */
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
                    Random random = new Random();
                    user.level = random.nextInt(4);
                }
                break;
            default:
                throw new IllegalArgumentException("Error cant be here" + user.button + " " + user.numButtons);
        }
    }
}
