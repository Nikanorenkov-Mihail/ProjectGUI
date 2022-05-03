/*
import java.util.LinkedList;

public class ElementsOnBoard {
    LinkedList<Bonus> bonuses = new LinkedList<>();
    LinkedList<Wall> walls = new LinkedList<>();

  /*  ElementsOnBoard() {
        this.walls = new LinkedList<Wall>();
        this.bonuses = new LinkedList<Bonus>();
    }*/
/*

    private float toRightFloat(float parametr) {
        return Math.round(parametr * (float) Math.pow(10, 2)) / (float) Math.pow(10, 2);
    }

    public void addBonus(int x, int y) {
        this.bonuses.add(new Bonus(x, y, 1));
    }

   // public void addRandomBonus() {
     //   this.bonuses.add(new Bonus(0, 0, 1).newRandomBonus(gridHight, gridWight));
    //}

    //public void addWall(float x1, float y1, float x2, float y2) {
    //    this.walls.add(new Wall(toRightFloat(x1), toRightFloat(y1), toRightFloat(x2), toRightFloat(y2)));
    //}

    //public void addRandomWall() {
    //    this.walls.add(new Wall(0, 0, 0, 0).newRandomWall());
    //}

    public boolean isWall(int x, int y) { // поменять
        for (Wall element : walls) {
            if (x > Math.min(element.x1, element.x2) && x < (element.x1 + element.x2 - Math.min(element.x1, element.x2))) {
                return y > Math.min(element.y1, element.y2) && x < (element.y1 + element.y2 - Math.min(element.y1, element.y2));
            }
        }
        return false;
    }

    public boolean isBonus(int x, int y) { // поменять
        for (Bonus element : bonuses) {
            if (x == element.x) {
                return y == element.y;
            }
        }
        return false;
    }
}
*/