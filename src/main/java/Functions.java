import org.jetbrains.annotations.NotNull;

public class Functions {

    public boolean isWallForSnake(@NotNull ElementsOnBoard bonusAndWalls, @NotNull SnakesBody snake) {
        return bonusAndWalls.isWall(snake.x, snake.y);
    }

    public boolean isBonusForSnake(@NotNull ElementsOnBoard bonusAndWalls, @NotNull SnakesBody snake) {
        return bonusAndWalls.isBonus(snake.x, snake.y);
    }
}
