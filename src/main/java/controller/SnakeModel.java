package controller;

import java.util.LinkedList;

/**
 * змейка это массив маленьких тел
 */
public class SnakeModel {
    public LinkedList<SnakesBody> arrayOfBodys = new LinkedList<>();

    public SnakeModel(int x, int y) {
        this.arrayOfBodys.add(new SnakesBody(x, y)); //начальное положение змейки
    }

    public SnakesBody getHeadXAndY() {
        return arrayOfBodys.get(0);
    }

}
