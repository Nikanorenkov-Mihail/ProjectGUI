import org.jetbrains.annotations.NotNull;

public class Wall {
    float x1;
    float y1;
    float x2;
    float y2;

    Wall(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    private float toRightFloat(float parametr) {
        return Math.round(parametr * (float) Math.pow(10, 2)) / (float) Math.pow(10, 2);
    }

    public Wall newRandomWall() {
        float leftLimit = -1.0f;
        float rightLimit = 1.0f;
        float x1 = (float) (leftLimit + (Math.random() * (rightLimit - leftLimit)));
        float y1 = (float) (leftLimit + (Math.random() * (rightLimit - leftLimit)));
        float x2 = (float) ((1 - Math.abs(x1) < 1 - Math.abs(y1)) ? x1 : (1 - Math.abs(x1)) / (1 + (Math.random() * (3 - 1))));
        float y2 = (float) ((1 - Math.abs(y1) < 1 - Math.abs(x1)) ? y1 : (1 - Math.abs(y1)) / (1 + (Math.random() * (3 - 1))));
        //               какая точка дальше от стены по своей оси, от той точки пойдет стена вверх или направо
        //               стена может быть в 1/2/3 (примерно) раза меньше расстояния

        return new Wall(toRightFloat(x1), toRightFloat(y1), toRightFloat(x2), toRightFloat(y2));
    }

    public boolean equals(@NotNull Wall another) {
        return this.x1 == another.x1 && this.x2 == another.x2 && this.y1 == another.y1 && this.y2 == another.y2;
    }

    public static void main(String[] args) {
        Bonus n1 = new Bonus(1, 2);
        n1.newRandomBonus();

    }
}
