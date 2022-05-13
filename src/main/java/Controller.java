import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWGamepadState;

public class Controller {
    ModelSnake change;
    long window1;
    boolean pauseControl = false;
    double delay;
    int counterForDelay = 0;
    double lastTimeForKeys = 0.0;
    int counterForStr = 0;
    String allWays;


    public Controller(ModelSnake change, long window1, double delay, String allWays) {
        this.change = change;
        this.window1 = window1;
        this.delay = delay;
        this.allWays = allWays;
    }

    public void control() {

        if (pauseControl) {
            if (glfwGetKey(window1, GLFW_KEY_O) == GLFW_PRESS) pauseControl = false;
            if (glfwGetKey(window1, GLFW_KEY_ESCAPE) == GLFW_PRESS) stop(window1);

              /*
                glfwSetKeyCallback(window1, (window, key, scancode, action, mods) -> {
                    if (key == GLFW_KEY_N) {
                        pauseControl = false;
                    } else if (key == GLFW_KEY_ESCAPE) {
                        stop(window);
                    }
                });*/

        } else {
            if (glfwGetKey(window1, GLFW_KEY_W) == GLFW_PRESS || glfwGetKey(window1, GLFW_KEY_UP) == GLFW_PRESS)
                change.lastWay = 0;
            if (glfwGetKey(window1, GLFW_KEY_S) == GLFW_PRESS || glfwGetKey(window1, GLFW_KEY_DOWN) == GLFW_PRESS)
                change.lastWay = 1;
            if (glfwGetKey(window1, GLFW_KEY_A) == GLFW_PRESS || glfwGetKey(window1, GLFW_KEY_LEFT) == GLFW_PRESS)
                change.lastWay = 2;
            if (glfwGetKey(window1, GLFW_KEY_D) == GLFW_PRESS || glfwGetKey(window1, GLFW_KEY_RIGHT) == GLFW_PRESS)
                change.lastWay = 3;
            if (glfwGetKey(window1, GLFW_KEY_P) == GLFW_PRESS) pause();
            if (glfwGetKey(window1, GLFW_KEY_ESCAPE) == GLFW_PRESS) stop(window1);
/*
                glfwSetKeyCallback(window1, (window, key, scancode, action, mods) -> {
                    if (key == GLFW_KEY_ESCAPE)
                        stop(window);
                    else if (key == GLFW_KEY_W || key == GLFW_KEY_UP) {
                        change.lastWay = 0;
                    } else if (key == GLFW_KEY_S || key == GLFW_KEY_DOWN) {
                        change.lastWay = 1;
                    } else if (key == GLFW_KEY_A || key == GLFW_KEY_LEFT) {
                        change.lastWay = 2;
                    } else if (key == GLFW_KEY_D || key == GLFW_KEY_RIGHT) {
                        change.lastWay = 3;
                    } else if (key == GLFW_KEY_P) {
                        pause();
                    }
                });*/
            double currentTime = glfwGetTime();
            if (currentTime - lastTimeForKeys > delay) {
                change.noCommands = true;
                lastTimeForKeys = currentTime;

                change.newWay();//едет по lastWay
                changeDelay();
            }
        }

    }

    private void stop(long wind) {
        glfwSetWindowShouldClose(wind, true);
    }

    private void pause() {
        pauseControl = true;
    }

    private void changeDelay() {
        if (change.nowBonus != counterForDelay && delay > 0.14) {
            delay -= 0.02;
            counterForDelay++;
        }
    }

    public void controllerForReplays() {
        try {

            double currentTime = glfwGetTime();
            if (currentTime - lastTimeForKeys > delay) {
                glfwSetKeyCallback(window1, (window, key, scancode, action, mods) -> {
                    if (key == GLFW_KEY_ESCAPE) {
                        stop(window);
                    }
                });
                if (counterForStr == allWays.length()) {
                    change.newWay();
                    // дальше схватят стену и кинут исключение
                }
                lastTimeForKeys = currentTime;
                change.lastWay = (int) allWays.charAt(counterForStr) - 48;
                change.newWay();
                changeDelay();
                counterForStr++;

            }
        } catch (StringIndexOutOfBoundsException e) { // случается при прерывании игры ESCпом
            throw new IllegalArgumentException("Game over  Your score: " + (change.nowBonus + 1) * 5);
        }
    }

}
