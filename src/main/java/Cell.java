/*
public class Cell { // не используется, скорее всего удалится
    int x;
    int y;
    int isSnakeHere;
    //может быть бонус(0), стена(1), пусто(2), змея(количество счетов = значение - 2)

    Cell(int x, int y, int isSnakeHere) {
        this.x = x;
        this.y = y;
        this.isSnakeHere = isSnakeHere;
    }

    public void changeTime(boolean needChange) {
        if (needChange && isSnakeHere > 2) {
            this.isSnakeHere--;
        }
    }

    public void hoeToWrite() {
        if (this.isSnakeHere == 0) {
            // return bonus;
        } else if (this.isSnakeHere == 1) {
            // return wall;
        } else if (this.isSnakeHere == 2) {
            //return emptyCell;
        } else if (this.isSnakeHere > 2) {
            // return bodyOfSnake
        }
    }
}
*/