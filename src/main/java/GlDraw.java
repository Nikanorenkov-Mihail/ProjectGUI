
import org.jetbrains.annotations.NotNull;

import static java.lang.Math.*;
import static org.lwjgl.opengl.GL11.*;

public class GlDraw {
    private int gridHeight, gridWidth;

    GlDraw(int gridHeight, int gridWidth) {
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
    }

    private float convertX(int x) {
        return -1.0f + 1.0f / (float) gridWidth * x * 2;
    }

    private float convertY(int y) {
        return -1.0f + 1.0f / (float) gridHeight * y * 2;
    }

    private float convertDoubleX(double x) {
        return (float) (-1.0f + 1.0f / (double) gridWidth * x * 2);
    }

    private float convertDoubleY(double y) {
        return (float) (-1.0f + 1.0f / (double) gridHeight * y * 2);
    }

    public void drawWalls(Wall @NotNull [] walls) {
        glColor3f(0.0f, 0.0f, 1.0f);
        glBegin(GL_LINES);
        for (int i = 0; i < walls.length; i++) {
            glVertex2f(convertX(walls[i].x1), convertY(walls[i].y1));
            glVertex2f(convertX(walls[i].x2), convertY(walls[i].y2));
        }
        glEnd();
    }

    public void drawBonus(@NotNull Bonus bonus, int takt) {
        glColor3f(1.0f, 0.0f, 0.0f);
        int x = bonus.x;
        int y = bonus.y;
        double radius = bonus.radius;
        if (takt == 0) {
            radius -= 0.1;
        } else if (takt <= 4) {
            radius -= 0.2;
        } else if (takt <= 8) {
            radius -= 0.3;
        } else if (takt <= 12) {
            radius -= 0.2;
        } else if (takt <= 16) {
            radius -= 0.1;
        }

        glBegin(GL_LINE_LOOP);
        for (int i = 0; i <= 360; i++) {
            double angle = PI * i / 180.0;
            glVertex2f(convertDoubleX((x + radius * cos(angle))), convertDoubleY(((y + radius * sin(angle)))));
        }
        glEnd();
    }

    public void drawSnake(@NotNull SnakesChanges snake) {
        for (int i = 0; i < snake.model.arrayOfBodys.size() ; i++) {
            glBegin(GL_LINE_LOOP);
            glColor3f(1.0f, 0.0f, 0.0f);
            for (int k = 0; k <= 360; k++) {
                double angle = PI * k / 180.0;
                glVertex2f(convertDoubleX((snake.model.arrayOfBodys.get(i).x + 0.5 * cos(angle))), convertDoubleY(((snake.model.arrayOfBodys.get(i).y + 0.5 * sin(angle)))));
            }
            glEnd();
        }


    }
/*
    public void drawGrid() {// функция, которая отрисовывает сетку
        glColor3f(0.0f, 1.0f, 0.0f);
        glBegin(GL_LINES);
        for (int i = 0; i < gridHeight; i++) {
            glVertex2f(convertX(0), convertY(i));
            glVertex2f(convertX(gridWidth), convertY(i));
        }
        for (int j = 0; j < gridWidth; j++) {
            glVertex2f(convertX(j), convertY(0));
            glVertex2f(convertX(j), convertY(gridHeight));
        }
        glEnd();
    }
*/


}
