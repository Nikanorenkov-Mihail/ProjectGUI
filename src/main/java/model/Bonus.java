package model;
import java.util.Random;

public class Bonus {
    public int x;
    public int y;
    public double radius = 0.7;

    public Bonus(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int bonusX() {
        return this.x;
    }

    public int bonusY() {
        return this.y;
    }

    public Bonus newRandomBonus(int gridHeight, int gridWidth) {
        Random random = new Random();
        int x = random.nextInt(gridWidth - 2) + 2;
        int y = random.nextInt(gridHeight - 2) + 2;

        return new Bonus(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bonus bonus = (Bonus) o;
        if (x != bonus.x) return false;
        return y == bonus.y;
    }

}
