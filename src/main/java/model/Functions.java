package model;

import org.jetbrains.annotations.NotNull;

public class Functions {

    public static boolean isWallForSnake(@NotNull Wall wall, @NotNull SnakesBody snakesHead) {
        if (snakesHead.x >= Math.min(wall.x1, wall.x2) && snakesHead.x <= Math.max(wall.x1, wall.x2)) {
            return snakesHead.y >= Math.min(wall.y1, wall.y2) && snakesHead.y <= Math.max(wall.y1, wall.y2);
        } else return false;
    }

    public static boolean isBonusForSnake(@NotNull Bonus bonus, @NotNull SnakesBody snakesHead) {
        return bonus.x == snakesHead.x && bonus.y == snakesHead.y;
    }

}
