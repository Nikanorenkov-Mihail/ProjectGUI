public class AskUser {
    Button[] allButtons = new Button[3];
    private int gridHeight, gridWidth;
    int button = 9;

    public AskUser(int gridHeight, int gridWidth) {
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
        addAllQuestions();
    }

    private float convertX(int x) {
        return -1.0f + 1.0f / (float) gridWidth * x * 2;
    }

    private float convertY(int y) {
        return -1.0f + 1.0f / (float) gridHeight * y * 2;
    }

    private void addAllQuestions() {
        allButtons[0] = new Button(1, gridHeight - 1,
                gridWidth / 2, gridHeight - 1,
                gridWidth / 2, gridHeight / 3 * 2,
                1, gridHeight / 3 * 2);

        allButtons[1] = new Button(1, gridHeight / 3 * 2,
                gridWidth / 2, gridHeight / 3 * 2,
                gridWidth / 2, gridHeight / 3,
                1, gridHeight / 3
        );

        allButtons[2] = new Button(1, gridHeight / 3,
                gridWidth / 2, gridHeight / 3,
                gridWidth / 2, 1,
                1, 1
        );


    }

    public int clickOnButton(double clickX, double clickY) {
        boolean inButton = false;
        for (int i = 0; i < 3; i++) {
            System.out.println(allButtons[i].x1 + " " + allButtons[i].y1);
            System.out.println(clickX + " " + clickY);
            if (clickX >= allButtons[i].x1 && clickX <= allButtons[i].x2) {
                if (clickY >= allButtons[i].y4 && clickY <= allButtons[i].y2) {
                    return i;
                }

            }

        }
        return 4;
    }
}
