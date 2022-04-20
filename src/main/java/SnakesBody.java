
public class SnakesBody { // это не используем
    float x; // начальная точка
    float y;
    // можно еще разные цвета добавить


    SnakesBody(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void changePoint(float newX, float newY) {
        this.x = newX;
        this.y = newY;
    }

}
