
import org.jetbrains.annotations.NotNull;

public class Bonus {
    float x;
    float y;

    Bonus(float x, float y) {
        this.x = x;
        this.y = y;
    }

    private float toRightFloat(float parametr) {
        return Math.round(parametr * (float) Math.pow(10, 2)) / (float) Math.pow(10, 2);
    }

    public Bonus newRandomBonus() {
        float leftLimit = -1.0f;
        float rightLimit = 1.0f;
        float x1 = leftLimit + (float) (Math.random() * (rightLimit - leftLimit));
        float y1 = leftLimit + (float) (Math.random() * (rightLimit - leftLimit));

        return new Bonus(toRightFloat(x1), toRightFloat(y1));
    }

    public boolean equals(@NotNull Bonus another) {
        return this.x == another.x && this.y == another.y;
    }

    public static void main(String[] args) {
        Bonus n1 = new Bonus(1, 2);
        n1.newRandomBonus();
    }
}
