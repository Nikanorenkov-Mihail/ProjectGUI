
import static org.lwjgl.opengl.GL11.*;

public class GlDraw {

    public void wallsLevels(int level) {
        /*
        int leftLimit = -1;
        int rightLimit = 1;
        float x = (float) (leftLimit + (Math.random() * (rightLimit - leftLimit)));
        float y = (float) leftLimit + (float) (Math.random() * (rightLimit - leftLimit));
        float x2 = (float) ((1 - Math.abs(x) < 1 - Math.abs(y)) ? x : (1 - Math.abs(x)) / (1 + (Math.random() * (3 - 1))));
        float y2 = (float) ((1 - Math.abs(y) < 1 - Math.abs(x)) ? y : (1 - Math.abs(y)) / (1 + (Math.random() * (3 - 1))));

        //               какая точка дальше от стены по своей оси, от той точки пойдет стена вверх или направо
        //               стена может быть в 1/2/3 (примерно) раза меньше расстояния

        float scale = (float) Math.pow(10, 2); // округление
        y = Math.round(y * scale) / scale;
        x = Math.round(x * scale) / scale;
        y2 = Math.round(y2 * scale) / scale;
        x2 = Math.round(x2 * scale) / scale;

        glBegin(GL_LINES);
        glColor3f(1, 1, 1);
        glVertex2f(x, y);
        glVertex2f(x2, y2);
        glEnd();

         */

        if (level == 1) {
            glBegin(GL_LINES);
            glColor3f(1, 1, 1);
            glVertex2f(0.3f, 0);
            glVertex2f(0.3f, 0.6f);

            glVertex2f(-0.5f, 0);
            glVertex2f(-0.5f, -0.6f);


            glEnd();
        } else if (level == 2) {
            glBegin(GL_LINES);
            glColor3f(1, 1, 1);
            glVertex2f(0.3f, 0);
            glVertex2f(0.3f, 0.6f);

            glVertex2f(-0.3f, 0);
            glVertex2f(-0.3f, -0.6f);

            glVertex2f(0.9f, -0.9f);
            glVertex2f(0.2f, -0.9f);
            glEnd();
        } else if (level == 3) {
            glBegin(GL_LINES);
            glColor3f(1, 1, 1);
            glVertex2f(-0.8f, 0.9f);
            glVertex2f(-0.4f, 0.9f);

            glVertex2f(-0.7f, 0.5f);
            glVertex2f(-0.3f, 0.5f);

            glVertex2f(0.4f, 0.9f);
            glVertex2f(0.4f, 0.2f);

            glVertex2f(-0.4f, -0.7f);
            glVertex2f(0.3f, -0.7f);

            glEnd();
        } else if (level == 4) {
            glBegin(GL_LINES);
            glColor3f(1, 1, 1);
            glVertex2f(-0.8f, 0.9f);
            glVertex2f(-0.4f, 0.9f);

            glVertex2f(-0.7f, 0.5f);
            glVertex2f(-0.3f, 0.5f);

            glVertex2f(0.4f, 0.9f);
            glVertex2f(0.4f, 0.2f);

            glVertex2f(-0.4f, -0.7f);
            glVertex2f(0.3f, -0.7f);

            glVertex2f(0, 0.2f);
            glVertex2f(0, -0.3f);

            glEnd();
        } else throw new IllegalArgumentException("Wrong level");


    }

    public void drawLines() {// функция, которая отрисовывает линии
        glBegin(GL_LINES);
        glColor3f(1.0f, 0.0f, 0.0f);
        for (float i = -1.0f; i <= 1.0f; i += 0.07f) // отрисовываем линии
        {
            glVertex2f(i, -1.0f);
            glVertex2f(i, 1.0f); // рисуем прямые в высоту
            glVertex2f(-1.0f, i);
            glVertex2f(1.0f, i); // рисуем прямые в ширину
        }
        glEnd();
    }

    public static void main(String[] args) {
        new GlDraw().wallsLevels(2);
    }
}
