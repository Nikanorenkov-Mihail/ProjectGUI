package controller;

import model.ModelMainSnake;

import static org.lwjgl.glfw.GLFW.*;

public class Controller {
    ModelMainSnake change;
    public boolean pauseControl = false;
    public double delay;
    int counterForDelay = 0;
    double lastTime = 0.0;
    int counterForStr = 0;
    String allWays = "";

    public Controller(ModelMainSnake change, double delay) {
        this.change = change;
        this.delay = delay;

    }

    /**
     * сам контроллер
     *
     * @param window1 где все дело происходит
     * @return команда
     */
    public int control(long window1) {
        if (pauseControl) {
            if (glfwGetKey(window1, GLFW_KEY_O) == GLFW_PRESS) pauseStop();
        } else {
            if (glfwGetKey(window1, GLFW_KEY_W) == GLFW_PRESS || glfwGetKey(window1, GLFW_KEY_UP) == GLFW_PRESS)
                return 0;
            if (glfwGetKey(window1, GLFW_KEY_S) == GLFW_PRESS || glfwGetKey(window1, GLFW_KEY_DOWN) == GLFW_PRESS)
                return 1;
            if (glfwGetKey(window1, GLFW_KEY_A) == GLFW_PRESS || glfwGetKey(window1, GLFW_KEY_LEFT) == GLFW_PRESS)
                return 2;
            if (glfwGetKey(window1, GLFW_KEY_D) == GLFW_PRESS || glfwGetKey(window1, GLFW_KEY_RIGHT) == GLFW_PRESS)
                return 3;
            if (glfwGetKey(window1, GLFW_KEY_P) == GLFW_PRESS) pause();
        }
        if (glfwGetKey(window1, GLFW_KEY_ESCAPE) == GLFW_PRESS) return 10;
        return change.lastWay;
    }

    /**
     * таймер для хода
     *
     * @return пора или нет, куда-то идти
     */
    public boolean isTimeToChangeWay() {
        double currentTime = glfwGetTime();
        if (currentTime - lastTime > delay) {
            lastTime = currentTime;
            changeDelay();
            return true;
        }
        return false;
    }

    private void stop() {
        throw new IllegalArgumentException("out");
    }

    private void pause() {
        pauseControl = true;
    }

    private void pauseStop() {
        pauseControl = false;
    }

    private void changeDelay() {
        if (change.nowBonus != counterForDelay && delay > 0.14) {
            delay -= 0.02;
            counterForDelay++;
        }
    }

    public int controllerForReplays(long window1) {
        int temp = 9;
        try {
            if (!pauseControl) {
                temp = (int) allWays.charAt(counterForStr) - 48;
                changeDelay();
                counterForStr++;
            }
        } catch (StringIndexOutOfBoundsException e) { // случается при прерывании игры ESCпом
            throw new IllegalArgumentException("Game over  Your score: " + (change.nowBonus + 1) * 5);
        }
        return temp;
    }

    public void whatWayInReplay(String allWays) {
        this.allWays = allWays;
    }

}
