public class SnakesChanges {
    int gridWidth;
    int gridHeight;
    SnakeModel model = new SnakeModel(10, 10);
    Wall[] masOfWalls;
    Bonus[] masOfBonuses;
    int nowBonus = 0;
    int lastWay = 0;

    public SnakesChanges(int gridWidth, int gridHeight, Wall[] masOfWalls, Bonus[] masOfBonuses) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.masOfWalls = masOfWalls;
        this.masOfBonuses = masOfBonuses;
        wallsWithBonus();
    }

    private void wallsWithBonus() { // бонусы не попадают на стены
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


    private void isWallHere() {

        for (int i = 0; i < masOfWalls.length; i++) {
            if (Functions.isWallForSnake(masOfWalls[i], model.getHeadXAndY())) {
                throw new IllegalArgumentException("Game over");
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

    public void newWay(int newWay) {
        if (masOfWalls.length == 0) {
            wayWithoutWalls(newWay);
        } else {
            wayWithWalls(newWay);
        }

    }

    public void wayWithWalls(int newWay) {

        if (newWay == 0) { // up
            lastWay = 0;
            newWayForAllBodys();
            model.arrayOfBodys.get(0).y++;
            isWallHere();
            isBonusHere();

            if (model.arrayOfBodys.get(0).y > gridHeight) model.arrayOfBodys.get(0).y = 1;
            // !!!!!!!!!!!!!! сделать проверку на стены!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        } else if (newWay == 1) { // low
            lastWay = 1;
            newWayForAllBodys();
            model.arrayOfBodys.get(0).y--;
            isWallHere();
            isBonusHere();

            if (model.arrayOfBodys.get(0).y < 1) model.arrayOfBodys.get(0).y = gridHeight;
        } else if (newWay == 2) { // left
            lastWay = 2;
            newWayForAllBodys();
            model.arrayOfBodys.get(0).x--;
            isWallHere();
            isBonusHere();
            if (model.arrayOfBodys.get(0).x < 1) model.arrayOfBodys.get(0).x = gridWidth;
        } else if (newWay == 3) { // right
            lastWay = 3;
            newWayForAllBodys();
            model.arrayOfBodys.get(0).x++;
            isWallHere();
            isBonusHere();

            if (model.arrayOfBodys.get(0).x > gridWidth) model.arrayOfBodys.get(0).x = 1;
        } else throw new IllegalArgumentException("Illegal way");
    }

    public void wayWithoutWalls(int newWay) {

        if (newWay == 0) { // up
            lastWay = 0;
            newWayForAllBodys();
            model.arrayOfBodys.get(0).y++;

            isBonusHere();

            if (model.arrayOfBodys.get(0).y > gridHeight) model.arrayOfBodys.get(0).y = 1;
            // !!!!!!!!!!!!!! сделать проверку на стены!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        } else if (newWay == 1) { // low
            lastWay = 1;
            newWayForAllBodys();
            model.arrayOfBodys.get(0).y--;

            isBonusHere();

            if (model.arrayOfBodys.get(0).y < 1) model.arrayOfBodys.get(0).y = gridHeight;
        } else if (newWay == 2) { // left
            lastWay = 2;
            newWayForAllBodys();
            model.arrayOfBodys.get(0).x--;

            isBonusHere();

            if (model.arrayOfBodys.get(0).x < 1) model.arrayOfBodys.get(0).x = gridWidth;
        } else if (newWay == 3) { // right
            lastWay = 3;
            newWayForAllBodys();
            model.arrayOfBodys.get(0).x++;

            isBonusHere();

            if (model.arrayOfBodys.get(0).x > gridWidth) model.arrayOfBodys.get(0).x = 1;
        } else throw new IllegalArgumentException("Illegal way");
    }

    private void newWayForAllBodys() {
        System.out.print("+++++++" + +model.arrayOfBodys.get(0).x + "      " + model.arrayOfBodys.get(0).y);
        for (int i = model.arrayOfBodys.size() - 1; i > 0; i--) {
            model.arrayOfBodys.set(i, new SnakesBody(model.arrayOfBodys.get(i - 1).x, model.arrayOfBodys.get(i - 1).y));
            System.out.print("    " + model.arrayOfBodys.get(i).x + "      " + model.arrayOfBodys.get(i).y);


        }
        System.out.println();
    }
}
