import static org.lwjgl.glfw.GLFW.*;

public class Controller {
    SnakesChanges change;
    long window1;
    boolean pauseControl = false;
    double delay;
    int counterForDelay = 0;


    public Controller(SnakesChanges change, long window1, double delay) {
        this.change = change;
        this.window1 = window1;
        this.delay = delay;
    }

    double lastTimeForKeys = 0.0;

    public void control() {
        double currentTime = glfwGetTime();
        if (currentTime - lastTimeForKeys > delay) {
            change.noCommands = true;
            lastTimeForKeys = currentTime;

            if (pauseControl) {
                glfwSetKeyCallback(window1, (window, key, scancode, action, mods) -> {
                    if (key == GLFW_KEY_N) {
                        pauseControl = false;
                    } else if (key == GLFW_KEY_ESCAPE) {
                        stop(window);
                    }
                });

            } else {
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
                });
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
        if (change.nowBonus != counterForDelay && delay > 0.1) {
            delay -= 0.03;
            counterForDelay++;
        }
    }
}
