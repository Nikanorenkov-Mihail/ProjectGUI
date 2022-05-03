
public class SnakesBody { // это не используем
    int x;
    int y;
    // можно еще разные цвета добавить


    SnakesBody(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void changePoint(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

}
