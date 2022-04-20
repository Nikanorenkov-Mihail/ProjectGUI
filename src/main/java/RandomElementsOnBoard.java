import java.util.LinkedList;

public class RandomElementsOnBoard { // нужен ли отдельный класс
    LinkedList<Bonus> bonusesRandom = new LinkedList<>();
    LinkedList<Wall> wallsRandom = new LinkedList<>();

    private float toRightFloat(float parametr) {
        return Math.round(parametr * (float) Math.pow(10, 2)) / (float) Math.pow(10, 2);
    }

    public void addRandomBonus() {
        this.bonusesRandom.add(new Bonus(0, 0).newRandomBonus());
    }

    public void addRandomWall() {
        this.wallsRandom.add(new Wall(0, 0, 0, 0).newRandomWall());
    }


    public boolean isWall(float x, float y) {
        for (Wall element : wallsRandom) {
            if (x > Math.min(element.x1, element.x2) && x < (element.x1 + element.x2 - Math.min(element.x1, element.x2))) {
                return y > Math.min(element.y1, element.y2) && x < (element.y1 + element.y2 - Math.min(element.y1, element.y2));
            }
        }
        return false;
    }

    public boolean isBonus(float x, float y) {
        for (Bonus element : bonusesRandom) {
            if (x == element.x) {
                return y == element.y;
            }
        }
        return false;
    }

}
