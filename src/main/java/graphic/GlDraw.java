package graphic;

import model.Bonus;
import model.Wall;
import model.ModelMainSnake;
import model.user.Button;
import model.user.ModelUser;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL12;

import java.awt.image.BufferedImage;

import static graphic.Texture.sizeOfImageDirectory;
import static java.lang.Math.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class GlDraw {
    private int gridHeight, gridWidth;
    private double lastTimeForTakts = 0.0;
    private int takt = 1;
    private double lastTime = 0.0;
    private int image = 0;

    public GlDraw(int gridHeight, int gridWidth) {
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

    private void drawSnake(@NotNull ModelMainSnake snake) {
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

    /**
     *
     * @param user текущий пользователь, что работает с интерфейсом
     * @param size количество кнопок
     * @param indexImage индекс текстуры
     *  index:
     *                   1 - начальный экран
     *                   2 - выбор уровня со стенами
     *                   3 - экран окончания
     *                   9 - ничего не выводим
     */

    public void askUserLevelInButton11(ModelUser user, int size, int indexImage) {
        glColor3f(0.0f, 1.0f, 0.0f);

        for (int i = 0; i < size; i++) {
            glBegin(GL_LINE_LOOP); // отрисовка кнопок

            glVertex2f(convertX(user.numMasButton[i].x1), convertY(user.numMasButton[i].y1));
            glVertex2f(convertX(user.numMasButton[i].x2), convertY(user.numMasButton[i].y2));
            glVertex2f(convertX(user.numMasButton[i].x3), convertY(user.numMasButton[i].y3));
            glVertex2f(convertX(user.numMasButton[i].x4), convertY(user.numMasButton[i].y4));
            glEnd();

            //если выключить работать будет приятнее
            if (indexImage != 9) drawSMTH(user, indexImage);
        }
        //glColor3f(0.0f, 0.0f, 0.0f);
    }

    /**
     *
     * @param styleGridWithGrid решетка в клеточку
     * @param changeForReplay модель, та, что отрисовывается и принимает от контроллера команды
     */
    public void drawGame(boolean styleGridWithGrid, ModelMainSnake changeForReplay) {
        if (styleGridWithGrid) drawGrid();
        drawWalls(changeForReplay.masOfWalls);
        drawBonus(changeForReplay.masOfBonuses[changeForReplay.nowBonus]); // как только бонус съедят, достанем другой из массива

        drawSnake(changeForReplay);
    }

    /**
     * функция текстур
     * без нее, к сожалению, все работает гораздо быстрее
     * @param user смотрим, сколько кнопок, чтобы понимать размеры кнопок
     * @param indexSmth индекс текстуры для отрисовки
     */
    private void drawSMTH(ModelUser user, int indexSmth) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        switch (indexSmth) {
            case (1): // рисуем кнопки старта (их 4)
                drawImage(user.numMasButton[0].x1,
                        user.numMasButton[0].y1,
                        user.numMasButton[0].x2,
                        user.numMasButton[0].y2,
                        user.numMasButton[user.numButtons - 1].x3,
                        user.numMasButton[user.numButtons - 1].y3,
                        user.numMasButton[user.numButtons - 1].x4,
                        user.numMasButton[user.numButtons - 1].y4,
                        indexSmth);
                break;
            case (2):// рисуем кнопки уровня (их 5)
                drawImage(user.numMasButton[0].x1,
                        user.numMasButton[0].y1,
                        user.numMasButton[0].x2,
                        user.numMasButton[0].y2,
                        user.numMasButton[user.numButtons - 1].x3,
                        user.numMasButton[user.numButtons - 1].y3,
                        user.numMasButton[user.numButtons - 1].x4,
                        user.numMasButton[user.numButtons - 1].y4,
                        1 + 4); // добавим кнопки старта

                break;
            case (3):// рисуем кнопки окончания (их 2)
                // отличаются тем, что горизонтальные
                drawImage(user.numMasButton[0].x1,
                        user.numMasButton[0].y1,
                        user.numMasButton[user.numButtons - 1].x2,
                        user.numMasButton[user.numButtons - 1].y2,
                        user.numMasButton[user.numButtons - 1].x3,
                        user.numMasButton[user.numButtons - 1].y3,
                        user.numMasButton[0].x4,
                        user.numMasButton[0].y4,
                        1 + 4 + 5); // добавим кнопки старта и уровня

                break;
            default:
                break;
        }

     /*   switch (indexSmth) {
            case (1): // рисуем кнопки старта (их 4)
                for (int i = 0; i < user.numButtons; i++) {
                    drawImage(user.numMasButton[i].x1,
                            user.numMasButton[i].y1,
                            user.numMasButton[i].x2,
                            user.numMasButton[i].y2,
                            user.numMasButton[i].x3,
                            user.numMasButton[i].y3,
                            user.numMasButton[i].x4,
                            user.numMasButton[i].y4,
                            i + 1);
                }
                break;
            case (2):// рисуем кнопки уровня (их 5)
                for (int i = 0; i < user.numButtons; i++) {
                    drawImage(user.numMasButton[i].x1,
                            user.numMasButton[i].y1,
                            user.numMasButton[i].x2,
                            user.numMasButton[i].y2,
                            user.numMasButton[i].x3,
                            user.numMasButton[i].y3,
                            user.numMasButton[i].x4,
                            user.numMasButton[i].y4,
                            i + 1 + 4); // добавим кнопки старта
                }
                break;
            case (3):// рисуем кнопки окончания (их 2)
                for (int i = 0; i < user.numButtons; i++) {
                    drawImage(user.numMasButton[i].x1,
                            user.numMasButton[i].y1,
                            user.numMasButton[i].x2,
                            user.numMasButton[i].y2,
                            user.numMasButton[i].x3,
                            user.numMasButton[i].y3,
                            user.numMasButton[i].x4,
                            user.numMasButton[i].y4,
                            i + 1 + 4 + 5); // добавим кнопки старта и уровня
                }
                break;
            default:
                break;
        }*/


    }

    /**
     * очень хочется сделать так, чтобы не нужно было загружать постоянно текстуры
     * но, не получается что-то активировать...
     * @param x1 координата
     * @param y1 координата
     * @param x2 координата
     * @param y2 координата
     * @param x3 координата
     * @param y3 координата
     * @param x4 координата
     * @param y4 координата
     * @param index индекс текстуры
     */
    private void drawImage(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, int index) {

        int id = Texture.Texture(index);

        // int id = Texture.masImages[(index-1)];
        System.out.println(index);
        //BufferedImage im = setTexture(index);
        //int id = texture.masImages[index];
        glBindTexture(GL_TEXTURE_2D, id);

        glBegin(GL_QUADS);

        glTexCoord2f(0, 0);
        glVertex2d(convertX(x1), convertY(y1));

        glTexCoord2f(1, 0);
        glVertex2d(convertX(x2), convertY(y2));

        glTexCoord2f(1, 1);
        glVertex2d(convertX(x3), convertY(y3));

        glTexCoord2f(0, 1);
        glVertex2d(convertX(x4), convertY(y4));

        glEnd();
        glDeleteTextures(id);
    }


}
