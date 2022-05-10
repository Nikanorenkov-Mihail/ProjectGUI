import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class SnakesChanges {
    int gridWidth;
    int gridHeight;
    SnakeModel model = new SnakeModel(10, 10);
    Wall[] masOfWalls;
    Bonus[] masOfBonuses;
    int nowBonus = 0;
    int lastWay = 0;
    int lastWayNotMe = 0; //last command
    boolean noCommands = true;
    boolean wayIntoMe = false;
    boolean withReplays;

    public SnakesChanges(int gridWidth, int gridHeight, Wall[] masOfWalls, Bonus[] masOfBonuses, boolean withReplays) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.masOfWalls = masOfWalls;
        this.masOfBonuses = masOfBonuses;
        this.withReplays = withReplays;
        wallsWithBonus();
        startPointInWall();
    }

    private void wallsWithBonus() { // бонусы не попадают на стены
        for (int naVsykyiSluchay = 0; naVsykyiSluchay < 10; naVsykyiSluchay++) {
            for (int i = 0; i < masOfBonuses.length; i++) {
                for (int k = 0; k < masOfWalls.length; k++) {
                    if (masOfBonuses[i].x >= Math.min(masOfWalls[k].x1, masOfWalls[k].x2) && masOfBonuses[i].x <= Math.max(masOfWalls[k].x1, masOfWalls[k].x2)) {
                        if (masOfBonuses[i].y >= Math.min(masOfWalls[k].y1, masOfWalls[k].y2) && masOfBonuses[i].y <= Math.max(masOfWalls[k].y1, masOfWalls[k].y2)) {
                            masOfBonuses[i] = new Bonus(1, 1).newRandomBonus(gridHeight, gridWidth);

                        }
                    }
                }
            }
        }
    }

    private void startPointInWall() {
        int x = model.getHeadXAndY().x;
        // первые ходы не будут в стене
        for (int naVsykyiSluchay = 0; naVsykyiSluchay < 10; naVsykyiSluchay++) {
            for (int k = 0; k < masOfWalls.length; k++) {
                if (x >= Math.min(masOfWalls[k].x1, masOfWalls[k].x2) && x <= Math.max(masOfWalls[k].x1, masOfWalls[k].x2)) {

                    for (int y = model.getHeadXAndY().y; y < (model.getHeadXAndY().y + 7); y++) {
                        if (y >= Math.min(masOfWalls[k].y1, masOfWalls[k].y2) && y <= Math.max(masOfWalls[k].y1, masOfWalls[k].y2)) {
                            masOfWalls[k] = Wall.newRandomWallForGrid(gridHeight, gridWidth);
                            k--;
                        }
                    }

                }
            }
        }
    }

    private void isWallHere() {

        for (int i = 0; i < masOfWalls.length; i++) {
            if (Functions.isWallForSnake(masOfWalls[i], model.getHeadXAndY())) {
                double curTime = glfwGetTime();
                double timeToStop = curTime + 2;
                while (timeToStop - curTime > 0) {
                    curTime = glfwGetTime();
                }

                throw new IllegalArgumentException("Game over  Your score: " + (nowBonus + 1) * 5);
            }
        }
    }

    private void isBonusHere() {
        if (Functions.isBonusForSnake(masOfBonuses[nowBonus], model.getHeadXAndY())) {
            nowBonus++;
            if (nowBonus == masOfBonuses.length) throw new IllegalArgumentException("Last bonus");
            model.arrayOfBodys.add(model.arrayOfBodys.size(), new SnakesBody(model.arrayOfBodys.get(model.arrayOfBodys.size() - 1).x, model.arrayOfBodys.get(model.arrayOfBodys.size() - 1).y));
        }

    }

    public void newWay() {
        if (masOfWalls.length == 0) {
            way(lastWay);
            isBonusHere();
        } else {
            way(lastWay);
            isWallHere();
            isBonusHere();
        }
        isNewWayNotSnake();
    }

    public void way(int newWay) {
        switch (newWay) {
            case (0): // up

                if (!(model.arrayOfBodys.size() > 1 && model.arrayOfBodys.get(1).equalsXY(new SnakesBody(model.getHeadXAndY().x, model.getHeadXAndY().y + 1)))) {
                    newWayForAllBodys();
                    model.arrayOfBodys.get(0).y++;
                    lastWay = 0;
                    lastWayNotMe = lastWay;


                } else wayIntoMe = true;

                if (model.arrayOfBodys.get(0).y > gridHeight) model.arrayOfBodys.get(0).y = 1;
                break;
            case (1): // low

                if (!(model.arrayOfBodys.size() > 1 && model.arrayOfBodys.get(1).equalsXY(new SnakesBody(model.getHeadXAndY().x, model.getHeadXAndY().y - 1)))) {
                    newWayForAllBodys();
                    model.arrayOfBodys.get(0).y--;
                    lastWay = 1;
                    lastWayNotMe = lastWay;

                } else wayIntoMe = true;

                if (model.arrayOfBodys.get(0).y < 1) model.arrayOfBodys.get(0).y = gridHeight;
                break;
            case (2): // left

                if (!(model.arrayOfBodys.size() > 1 && model.arrayOfBodys.get(1).equalsXY(new SnakesBody(model.getHeadXAndY().x - 1, model.getHeadXAndY().y)))) {
                    newWayForAllBodys();
                    model.arrayOfBodys.get(0).x--;
                    lastWay = 2;
                    lastWayNotMe = lastWay;

                } else wayIntoMe = true;

                if (model.arrayOfBodys.get(0).x < 1) model.arrayOfBodys.get(0).x = gridWidth;
                break;
            case (3): // right

                if (!(model.arrayOfBodys.size() > 1 && model.arrayOfBodys.get(1).equalsXY(new SnakesBody(model.getHeadXAndY().x + 1, model.getHeadXAndY().y)))) {
                    newWayForAllBodys();
                    model.arrayOfBodys.get(0).x++;
                    lastWay = 3;
                    lastWayNotMe = lastWay;

                } else wayIntoMe = true;

                if (model.arrayOfBodys.get(0).x > gridWidth) model.arrayOfBodys.get(0).x = 1;
                break;
            default:
                throw new IllegalArgumentException("Illegal way");
        }

        if (wayIntoMe) wayInMe();


    }

    private void wayInMe() {

        newWayForAllBodys();

        switch (lastWayNotMe) {
            case (0):
                model.arrayOfBodys.get(0).y++;
                if (model.arrayOfBodys.get(0).y > gridHeight) model.arrayOfBodys.get(0).y = 1;
                break;
            case (1):
                model.arrayOfBodys.get(0).y--;
                if (model.arrayOfBodys.get(0).y < 1) model.arrayOfBodys.get(0).y = gridHeight;
                break;
            case (2):
                model.arrayOfBodys.get(0).x--;
                if (model.arrayOfBodys.get(0).x < 1) model.arrayOfBodys.get(0).x = gridWidth;
                break;
            case (3):
                model.arrayOfBodys.get(0).x++;
                if (model.arrayOfBodys.get(0).x > gridWidth) model.arrayOfBodys.get(0).x = 1;
                break;
        }
        wayIntoMe = false;

    }

    private void newWayForAllBodys() {
        for (int i = model.arrayOfBodys.size() - 1; i > 0; i--) {
            model.arrayOfBodys.set(i, new SnakesBody(model.arrayOfBodys.get(i - 1).x, model.arrayOfBodys.get(i - 1).y));
        }
    }

    private void isNewWayNotSnake() {

        for (int i = 2; i < model.arrayOfBodys.size(); i++) {
            if (model.arrayOfBodys.get(i).equalsXY(model.getHeadXAndY())) {
                double curTime = glfwGetTime();
                double timeToStop = curTime + 2;
                while (timeToStop - curTime > 0) {
                    curTime = glfwGetTime();
                }
                throw new IllegalArgumentException("Game over");
            }
        }

    }
}