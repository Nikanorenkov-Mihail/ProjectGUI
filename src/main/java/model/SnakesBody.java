package model;

import org.jetbrains.annotations.NotNull;

/**
 * самое главное в змейке - ее тело
 * а если тел много...
 */
public class SnakesBody {
    public int x;
    public int y;

    public SnakesBody(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void changePoint(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

    public boolean equalsXY(@NotNull SnakesBody another) {
        if (x != another.x) return false;
        return y == another.y;
    }
}
