
import org.jetbrains.annotations.NotNull;

import static java.lang.Math.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.*;

public class GlDraw {
    private int gridHeight, gridWidth;
    private double lastTimeForTakts = 0.0;
    private int takt = 1;
    private double lastTime = 0.0;
    private int image = 0;

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

    private float convertDoubleXToFloat(double x) {
        return (float) (-1.0f + 1.0f / (double) gridWidth * x * 2);
    }

    private float convertDoubleYToFloat(double y) {
        return (float) (-1.0f + 1.0f / (double) gridHeight * y * 2);
    }

    private void drawWalls(Wall @NotNull [] walls) {
        glColor3f(0.0f, 0.0f, 1.0f);
        glBegin(GL_LINES);
        for (int i = 0; i < walls.length; i++) {
            glVertex2f(convertX(walls[i].x1), convertY(walls[i].y1));
            glVertex2f(convertX(walls[i].x2), convertY(walls[i].y2));
        }
        glEnd();
    }

    private void drawBonus(@NotNull Bonus bonus) {
        double currentTime = glfwGetTime();
        if (currentTime - lastTimeForTakts > 0.01) { // delay всегда один
            takt++;
            lastTimeForTakts = currentTime;
            if (takt > 20) takt = 1;
        }

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
            glVertex2f(convertDoubleXToFloat((x + radius * cos(angle))), convertDoubleYToFloat(((y + radius * sin(angle)))));
        }
        glEnd();
    }

    private void drawSnake(@NotNull ModelSnake snake) {
        for (int i = 0; i < snake.model.arrayOfBodys.size(); i++) {
            glBegin(GL_LINE_LOOP);
            glColor3f(1.0f, 0.0f, 0.0f);
            for (int k = 0; k <= 360; k++) {
                double angle = PI * k / 180.0;
                glVertex2f(convertDoubleXToFloat((snake.model.arrayOfBodys.get(i).x + 0.6 * cos(angle))), convertDoubleYToFloat(((snake.model.arrayOfBodys.get(i).y + 0.6 * sin(angle)))));
            }
            glEnd();
        }
    }

    public void drawFPS() {

        double currentTime = glfwGetTime();
        image++;
        if (currentTime - lastTime > 0.5) {
            System.out.println(image);
            lastTime = currentTime;
            image = 0;
        }
    }

    private void drawGrid() {// функция, которая отрисовывает сетку
        glColor3f(0.32f, 0.32f, 0.32f);
        glBegin(GL_LINES);
        for (int i = 0; i < gridHeight; i += 2.5) {
            glVertex2f(convertX(0), convertY(i));
            glVertex2f(convertX(gridWidth), convertY(i));
        }
        for (int j = 0; j < gridWidth; j += 2.5) {
            glVertex2f(convertX(j), convertY(0));
            glVertex2f(convertX(j), convertY(gridHeight));
        }
        glEnd();
    }

    public void askUserInButton(Button @NotNull [] user) {
        glColor3f(0.0f, 1.0f, 0.0f);
        for (int i = 0; i < user.length; i++) {
            glBegin(GL_LINE_LOOP); // отрисовка кнопок

            glVertex2f(convertX(user[i].x1), convertY(user[i].y1));
            glVertex2f(convertX(user[i].x2), convertY(user[i].y2));
            glVertex2f(convertX(user[i].x3), convertY(user[i].y3));
            glVertex2f(convertX(user[i].x4), convertY(user[i].y4));
            glEnd();
        }

    }

    public void askUserLevelInButton(Button @NotNull [] user) {
        glColor3f(1.0f, 0.0f, 0.0f);
        for (int i = 0; i < user.length; i++) {
            glBegin(GL_LINE_LOOP); // отрисовка кнопок

            glVertex2f(convertX(user[i].x1), convertY(user[i].y1));
            glVertex2f(convertX(user[i].x2), convertY(user[i].y2));
            glVertex2f(convertX(user[i].x3), convertY(user[i].y3));
            glVertex2f(convertX(user[i].x4), convertY(user[i].y4));
            glEnd();
        }
    }

    public void drawGame(boolean styleGridWithGrid, ModelSnake changeForReplay) {
        if (styleGridWithGrid) drawGrid();
        drawWalls(changeForReplay.masOfWalls);
        drawBonus(changeForReplay.masOfBonuses[changeForReplay.nowBonus]); // как только бонус съедят, достанем другой из массива

        drawSnake(changeForReplay);
    }


}
