
public class SnakeModel {
    SnakesBody[] arrayOfBodys = new SnakesBody[300];

    SnakeModel(float x, float y) {
        this.arrayOfBodys[0] = new SnakesBody(x, y); //начальное положение змейки
    }

    public SnakesBody getHeadXAndY() {
        return arrayOfBodys[0];
    }

    public void change(int newWay) {
        for (int i = arrayOfBodys.length - 1; i > 0; i--) arrayOfBodys[i] = arrayOfBodys[i - 1];

        if (newWay == 0) { // up
            arrayOfBodys[0].y++;
            // !!!!!!!!!!!!!! сделать проверку на стены!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        } else if (newWay == 1) { // low
            arrayOfBodys[0].y--;
        } else if (newWay == 2) { // left
            arrayOfBodys[0].x--;
        } else if (newWay == 3) { // right
            arrayOfBodys[0].y++;
        } else throw new IllegalArgumentException("Illegal way");
    }

}
