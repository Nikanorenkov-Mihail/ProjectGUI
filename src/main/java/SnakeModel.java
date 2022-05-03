import java.util.LinkedList;

public class SnakeModel {
    LinkedList<SnakesBody> arrayOfBodys = new LinkedList<>();


    SnakeModel(int x, int y) {
        this.arrayOfBodys.add(new SnakesBody(x, y)); //начальное положение змейки
    }

    public SnakesBody getHeadXAndY() {
        return arrayOfBodys.get(0);
    }

}
