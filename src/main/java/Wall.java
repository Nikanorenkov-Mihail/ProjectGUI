
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class Wall {
    int x1;
    int y1;
    int x2;
    int y2;

    Wall(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        System.out.println(x1 + " " + y1 + " " + x2 + " " + y2 + " ");
    }

    public static @NotNull Wall newRandomWallForGrid(int gridHeight, int gridWidth) {
        Random random = new Random();
        boolean b = random.nextBoolean();
        int x = random.nextInt(gridWidth);
        int y = random.nextInt(gridHeight);
        int x2 = x + (b ? 0 : random.nextInt(8) + 2);
        int y2 = y + (!b ? 0 : random.nextInt(8) + 2);
        return new Wall(x, y, x2, y2);
    }

    public static Wall @NotNull [] newRandomWallsForGrid(int gridHeight, int gridWidth, int size) {
        Wall[] walls = new Wall[size];
        if (size != 0) {
            for (int i = 0; i < size; i++) walls[i] = newRandomWallForGrid(gridHeight, gridWidth);
        }
        return walls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Wall wall = (Wall) o;

        if (x1 != wall.x1) return false;
        if (y1 != wall.y1) return false;
        if (x2 != wall.x2) return false;
        return y2 == wall.y2;
    }
}