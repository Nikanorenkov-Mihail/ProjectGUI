public class SnakesChanges {
    int gridWidth;
    int gridHeight;
    SnakeModel model = new SnakeModel(10, 10);
    Wall[] masOfWalls;
    Bonus[] masOfBonuses;
    int nowBonus = 0;
    int lastWay = 0;
    int lastWayNotMe = 0;
    boolean noCommands = true;
    boolean wayIntoMe = false;
    boolean withReplays;
    int keyForReplays = 0;
    Replays replay = new Replays();

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

    /*   public void wayWithWalls(int newWay) {

           if (newWay == 0) { // up
               lastWay = 0;
               newWayForAllBodys();

               model.arrayOfBodys.get(0).y++;
               if (model.arrayOfBodys.size() > 1 && model.arrayOfBodys.get(1).equalsXY(new SnakesBody(model.getHeadXAndY().x, model.getHeadXAndY().y+1)))
                   throw new IllegalArgumentException("Game over");
               isWallHere();
               isBonusHere();

               if (model.arrayOfBodys.get(0).y > gridHeight) model.arrayOfBodys.get(0).y = 1;
               // !!!!!!!!!!!!!! сделать проверку на стены!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
           } else if (newWay == 1) { // low
               lastWay = 1;
               newWayForAllBodys();
               model.arrayOfBodys.get(0).y--;
               if (model.arrayOfBodys.size() > 1 && model.arrayOfBodys.get(1).equalsXY(new SnakesBody(model.getHeadXAndY().x, model.getHeadXAndY().y-1)))
                   throw new IllegalArgumentException("Game over");
               isWallHere();
               isBonusHere();

               if (model.arrayOfBodys.get(0).y < 1) model.arrayOfBodys.get(0).y = gridHeight;
           } else if (newWay == 2) { // left
               lastWay = 2;
               newWayForAllBodys();
               model.arrayOfBodys.get(0).x--;
               if (model.arrayOfBodys.size() > 1 && model.arrayOfBodys.get(1).equalsXY(new SnakesBody(model.getHeadXAndY().x-1, model.getHeadXAndY().y)))
                   throw new IllegalArgumentException("Game over");
               isWallHere();
               isBonusHere();
               if (model.arrayOfBodys.get(0).x < 1) model.arrayOfBodys.get(0).x = gridWidth;
           } else if (newWay == 3) { // right
               lastWay = 3;
               newWayForAllBodys();
               model.arrayOfBodys.get(0).x++;
               if (model.arrayOfBodys.size() > 1 && model.arrayOfBodys.get(1).equalsXY(new SnakesBody(model.getHeadXAndY().x+1, model.getHeadXAndY().y)))
                   throw new IllegalArgumentException("Game over");
               isWallHere();
               isBonusHere();

               if (model.arrayOfBodys.get(0).x > gridWidth) model.arrayOfBodys.get(0).x = 1;
           } else throw new IllegalArgumentException("Illegal way");
       }
   */
    public void way(int newWay) {
        switch (newWay) {
            case (0): // up

                if (!(model.arrayOfBodys.size() > 1 && model.arrayOfBodys.get(1).equalsXY(new SnakesBody(model.getHeadXAndY().x, model.getHeadXAndY().y + 1)))) {
                    newWayForAllBodys();
                    model.arrayOfBodys.get(0).y++;
                    lastWay = 0;
                    lastWayNotMe = lastWay;

                    keyForReplays = lastWay;
                } else wayIntoMe = true;

                if (model.arrayOfBodys.get(0).y > gridHeight) model.arrayOfBodys.get(0).y = 1;
                break;
            case (1): // low

                if (!(model.arrayOfBodys.size() > 1 && model.arrayOfBodys.get(1).equalsXY(new SnakesBody(model.getHeadXAndY().x, model.getHeadXAndY().y - 1)))) {
                    newWayForAllBodys();
                    model.arrayOfBodys.get(0).y--;
                    lastWay = 1;
                    lastWayNotMe = lastWay;
                    keyForReplays = lastWay;
                } else wayIntoMe = true;

                if (model.arrayOfBodys.get(0).y < 1) model.arrayOfBodys.get(0).y = gridHeight;
                break;
            case (2): // left

                if (!(model.arrayOfBodys.size() > 1 && model.arrayOfBodys.get(1).equalsXY(new SnakesBody(model.getHeadXAndY().x - 1, model.getHeadXAndY().y)))) {
                    newWayForAllBodys();
                    model.arrayOfBodys.get(0).x--;
                    lastWay = 2;
                    lastWayNotMe = lastWay;
                    keyForReplays = lastWay;
                } else wayIntoMe = true;

                if (model.arrayOfBodys.get(0).x < 1) model.arrayOfBodys.get(0).x = gridWidth;
                break;
            case (3): // right

                if (!(model.arrayOfBodys.size() > 1 && model.arrayOfBodys.get(1).equalsXY(new SnakesBody(model.getHeadXAndY().x + 1, model.getHeadXAndY().y)))) {
                    newWayForAllBodys();
                    model.arrayOfBodys.get(0).x++;
                    lastWay = 3;
                    lastWayNotMe = lastWay;
                    keyForReplays = lastWay;
                } else wayIntoMe = true;

                if (model.arrayOfBodys.get(0).x > gridWidth) model.arrayOfBodys.get(0).x = 1;
                break;
            default:
                throw new IllegalArgumentException("Illegal way");
        }

        if (wayIntoMe) wayInMe();

        if (withReplays) replay.writer(keyForReplays);

    }

    private void wayInMe() {

        newWayForAllBodys();
        keyForReplays = lastWayNotMe;
        switch (lastWayNotMe) {
            case (0):
                model.arrayOfBodys.get(0).y++;
                break;
            case (1):
                model.arrayOfBodys.get(0).y--;
                break;
            case (2):
                model.arrayOfBodys.get(0).x--;
                break;
            case (3):
                model.arrayOfBodys.get(0).x++;
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
            if (model.arrayOfBodys.get(i).equalsXY(model.getHeadXAndY()))
                throw new IllegalArgumentException("Game over");
        }

    }
}
/*
        if (newWay == 0) { // up
            lastWay = 0;
            if (model.arrayOfBodys.size() > 1 && model.arrayOfBodys.get(1).equalsXY(new SnakesBody(model.getHeadXAndY().x, model.getHeadXAndY().y + 1)))
                throw new IllegalArgumentException("Game over");

            newWayForAllBodys();
            model.arrayOfBodys.get(0).y++;


            if (model.arrayOfBodys.get(0).y > gridHeight) model.arrayOfBodys.get(0).y = 1;

        } else if (newWay == 1) { // low
            lastWay = 1;
            if (model.arrayOfBodys.size() > 1 && model.arrayOfBodys.get(1).equalsXY(new SnakesBody(model.getHeadXAndY().x, model.getHeadXAndY().y - 1)))
                throw new IllegalArgumentException("Game over");

            newWayForAllBodys();
            model.arrayOfBodys.get(0).y--;

            if (model.arrayOfBodys.get(0).y < 1) model.arrayOfBodys.get(0).y = gridHeight;
        } else if (newWay == 2) { // left
            lastWay = 2;
            if (model.arrayOfBodys.size() > 1 && model.arrayOfBodys.get(1).equalsXY(new SnakesBody(model.getHeadXAndY().x - 1, model.getHeadXAndY().y)))
                throw new IllegalArgumentException("Game over");

            newWayForAllBodys();
            model.arrayOfBodys.get(0).x--;

            if (model.arrayOfBodys.get(0).x < 1) model.arrayOfBodys.get(0).x = gridWidth;
        } else if (newWay == 3) { // right
            lastWay = 3;
            if (model.arrayOfBodys.size() > 1 && model.arrayOfBodys.get(1).equalsXY(new SnakesBody(model.getHeadXAndY().x + 1, model.getHeadXAndY().y)))
                throw new IllegalArgumentException("Game over");

            newWayForAllBodys();
            model.arrayOfBodys.get(0).x++;

            if (model.arrayOfBodys.get(0).x > gridWidth) model.arrayOfBodys.get(0).x = 1;
        } else throw new IllegalArgumentException("Illegal way");*/