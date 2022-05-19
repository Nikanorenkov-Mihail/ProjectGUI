import static org.lwjgl.glfw.GLFW.*;

public class Controller {
    ModelSnake change;
    long window1;
    boolean pauseControl = false;
    double delay;
    int counterForDelay = 0;
    double lastTimeForKeys = 0.0;
    int counterForStr = 0;
    String allWays = "";


    public Controller(ModelSnake change, double delay) {
        this.change = change;
        this.delay = delay;

    }

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

    public boolean isTimeToChangeWay() {
        double currentTime = glfwGetTime();
        if (currentTime - lastTimeForKeys > delay) {

            lastTimeForKeys = currentTime;


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

    public void controllerForReplays(long window1) {
        if (pauseControl || glfwGetKey(window1, GLFW_KEY_O) == GLFW_PRESS) pauseStop();
        if (glfwGetKey(window1, GLFW_KEY_P) == GLFW_PRESS) pause();

        try {
            double currentTime = glfwGetTime();
            if (currentTime - lastTimeForKeys > delay) {

                lastTimeForKeys = currentTime;

                if (!pauseControl) {
                    change.lastWay = (int) allWays.charAt(counterForStr) - 48;
                    change.newWay();
                    changeDelay();
                    counterForStr++;
                }

            }
        } catch (StringIndexOutOfBoundsException e) { // случается при прерывании игры ESCпом
            throw new IllegalArgumentException("Game over  Your score: " + (change.nowBonus + 1) * 5);
        }
    }

    public void whatWayInReplay(String allWays) {
        this.allWays = allWays;
    }


}
