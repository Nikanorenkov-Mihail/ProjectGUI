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
        addAllQuestionsForTypeOfGame();
    }

    private float convertX(int x) {
        return -1.0f + 1.0f / (float) gridWidth * x * 2;
    }

    private float convertY(int y) {
        return -1.0f + 1.0f / (float) gridHeight * y * 2;
    }

    private void addAllQuestionsForTypeOfGame() {
        for (int i = 0; i < allButtonsForType.length; i++) {
            allButtonsForType[i] = new Button(1, (i == 0) ? gridHeight - 1 : gridHeight / allButtonsForType.length * (allButtonsForType.length - i),
                    gridWidth / 2, (i == 0) ? gridHeight - 1 : gridHeight / allButtonsForType.length * (allButtonsForType.length - i),
                    gridWidth / 2, (i == allButtonsForType.length - 1) ? 1 : gridHeight / allButtonsForType.length * (allButtonsForType.length - 1 - i),
                    1, (i == allButtonsForType.length - 1) ? 1 : gridHeight / allButtonsForType.length * (allButtonsForType.length - 1 - i));

        }
        /*
        allButtonsForType[0] = new Button(1, gridHeight - 1,
                gridWidth / 2, gridHeight - 1,
                gridWidth / 2, gridHeight / 3 * 2,
                1, gridHeight / 3 * 2);

        allButtonsForType[1] = new Button(1, gridHeight / 3 * 2,
                gridWidth / 2, gridHeight / 3 * 2,
                gridWidth / 2, gridHeight / 3,
                1, gridHeight / 3
        );

        allButtonsForType[2] = new Button(1, gridHeight / 3,
                gridWidth / 2, gridHeight / 3,
                gridWidth / 2, 1,
                1, 1
        );
        /*
        allButtonsForType[0] = new Button(1, gridHeight - 1,
                gridWidth / 2, gridHeight - 1,
                gridWidth / 2, gridHeight / allButtonsForType.length * 3,
                1, gridHeight / allButtonsForType.length * 3);

        allButtonsForType[1] = new Button(1, gridHeight / allButtonsForType.length * 3,
                gridWidth / 2, gridHeight / allButtonsForType.length * 3,
                gridWidth / 2, gridHeight / allButtonsForType.length * 2,
                1, gridHeight / allButtonsForType.length * 2
        );

        allButtonsForType[2] = new Button(1, gridHeight / allButtonsForType.length * 2,
                gridWidth / 2, gridHeight / allButtonsForType.length * 2,
                gridWidth / 2, gridHeight / allButtonsForType.length,
                1, gridHeight / allButtonsForType.length
        );

        allButtonsForType[3] = new Button(1, gridHeight / allButtonsForType.length,
                gridWidth / 2, gridHeight / allButtonsForType.length,
                gridWidth / 2, 1,
                1, 1
        );*/


    }

    public void addAllQuestionsForLevelOfGameWithWalls() {
        for (int i = 0; i < allButtonsForLevel.length; i++) {
            allButtonsForLevel[i] = new Button(1, (i == 0) ? gridHeight - 1 : gridHeight / allButtonsForLevel.length * (allButtonsForLevel.length - i),
                    gridWidth / 2, (i == 0) ? gridHeight - 1 : gridHeight / allButtonsForLevel.length * (allButtonsForLevel.length - i),
                    gridWidth / 2, (i == allButtonsForLevel.length - 1) ? 1 : gridHeight / allButtonsForLevel.length * (allButtonsForLevel.length - 1 - i),
                    1, (i == allButtonsForLevel.length - 1) ? 1 : gridHeight / allButtonsForLevel.length * (allButtonsForLevel.length - 1 - i));

        }
        /*
        allButtonsForLevel[0] = new Button(1, gridHeight - 1,
                gridWidth / 2, gridHeight - 1,
                gridWidth / 2, gridHeight / allButtonsForLevel.length * 3,
                1, gridHeight / allButtonsForLevel.length * 3);

        allButtonsForLevel[1] = new Button(1, gridHeight / allButtonsForLevel.length * 3,
                gridWidth / 2, gridHeight / allButtonsForLevel.length * 3,
                gridWidth / 2, gridHeight / allButtonsForLevel.length * 2,
                1, gridHeight / allButtonsForLevel.length * 2
        );

        allButtonsForLevel[2] = new Button(1, gridHeight / allButtonsForLevel.length * 2,
                gridWidth / 2, gridHeight / allButtonsForLevel.length * 2,
                gridWidth / 2, gridHeight / allButtonsForLevel.length,
                1, gridHeight / allButtonsForLevel.length
        );

        allButtonsForLevel[3] = new Button(1, gridHeight / allButtonsForLevel.length,
                gridWidth / 2, gridHeight / allButtonsForLevel.length,
                gridWidth / 2, 1,
                1, 1
        );*/
    }

    public void addAllQuestionsForEndGame() {
        for (int i = 0; i < allButtonsOfEndGame.length; i++) {
            allButtonsOfEndGame[i] = new Button(    (i == allButtonsForLevel.length - 1) ? gridWidth - 1 : (i == 0) ? 1 : (gridWidth / allButtonsOfEndGame.length * i) - 1, gridHeight / 3 * 2,
                    (gridWidth / allButtonsOfEndGame.length * (i + 1)) - 1, gridHeight / 3 * 2,
                    (gridWidth / allButtonsOfEndGame.length * (i + 1)) - 1, gridHeight / 3,
                    (i == allButtonsForLevel.length - 1) ? gridWidth - 1 : (i == 0) ? 1 : (gridWidth / allButtonsOfEndGame.length * i) - 1, gridHeight / 3);
        }
        /*
        allButtonsOfEndGame[0] = new Button(1, gridHeight / 3 * 2,
                (gridWidth / allButtonsOfEndGame.length) - 1, gridHeight / 3 * 2,
                (gridWidth / allButtonsOfEndGame.length) - 1, gridHeight / 3,
                1, gridHeight / 3);

        allButtonsOfEndGame[1] = new Button((gridWidth / allButtonsOfEndGame.length) - 1, gridHeight / 3 * 2,
                (gridWidth / allButtonsOfEndGame.length * 2) - 1, gridHeight / 3 * 2,
                (gridWidth / allButtonsOfEndGame.length * 2) - 1, gridHeight / 3,
                (gridWidth / allButtonsOfEndGame.length) - 1, gridHeight / 3);

        allButtonsOfEndGame[2] = new Button((gridWidth / allButtonsOfEndGame.length * 2) - 1, gridHeight / 3 * 2,
                (gridWidth / allButtonsOfEndGame.length * 3) - 1, gridHeight / 3 * 2,
                (gridWidth / allButtonsOfEndGame.length * 3) - 1, gridHeight / 3,
                (gridWidth / allButtonsOfEndGame.length * 2) - 1, gridHeight / 3);*/

    }

    public int clickOnButton1(double clickX, double clickY) {
        for (int i = 0; i < allButtonsForLevel.length; i++) {
            System.out.println(allButtonsForLevel[i].x1 + " " + allButtonsForLevel[i].y1);
            System.out.println(clickX + " " + clickY);
            if (clickX >= allButtonsForLevel[i].x1 && clickX <= allButtonsForLevel[i].x2) {
                if (clickY >= allButtonsForLevel[i].y4 && clickY <= allButtonsForLevel[i].y2) {
                    return i;
                }

            }

        }
        return 9;
    }
    public int clickOnButton2(double clickX, double clickY) {
        for (int i = 0; i < allButtonsOfEndGame.length; i++) {
            System.out.println(allButtonsOfEndGame[i].x1 + " " + allButtonsOfEndGame[i].y1);
            System.out.println(clickX + " " + clickY);
            if (clickX >= allButtonsOfEndGame[i].x1 && clickX <= allButtonsOfEndGame[i].x2) {
                if (clickY >= allButtonsOfEndGame[i].y4 && clickY <= allButtonsOfEndGame[i].y2) {
                    return i;
                }

            }

        }
        return 9;
    }
    public int clickOnButton3(double clickX, double clickY) {
        for (int i = 0; i < allButtonsForType.length; i++) {
            System.out.println(allButtonsForType[i].x1 + " " + allButtonsForType[i].y1);
            System.out.println(clickX + " " + clickY);
            if (clickX >= allButtonsForType[i].x1 && clickX <= allButtonsForType[i].x2) {
                if (clickY >= allButtonsForType[i].y4 && clickY <= allButtonsForType[i].y2) {
                    return i;
                }

            }

        }
        return 9;
    }
}
