import java.util.HashSet;
import java.util.Set;

public class Snake {
    int x; // начальная точка
    int y;
    Set<Cell> bodyOfSnake = new HashSet<>();


    Snake(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void addPart(int x, int y) {
        this.bodyOfSnake.add(new Cell(x, y,1 ));
    }

    public void changePoint(){

    }

}
