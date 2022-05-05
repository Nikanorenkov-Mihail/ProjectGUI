import static org.lwjgl.glfw.GLFW.*;

public class Controller {
    SnakesChanges change;
    long window1;

    public Controller(SnakesChanges change,  long window1){
       this.change = change;
        this.window1 = window1;
    }

    double lastTimeForKeys = 0.0;
    public void control() {
        double currentTime = glfwGetTime();
        if (currentTime - lastTimeForKeys > 0.3) {
            change.noCommands = true;
            lastTimeForKeys = currentTime;

            glfwSetKeyCallback(window1, (window, key, scancode, action, mods) -> {

                if (key == GLFW_KEY_ESCAPE)
                    glfwSetWindowShouldClose(window, true);
                else if (key == GLFW_KEY_W) {
                    change.lastWay = 0;
                }
                else if (key == GLFW_KEY_S) {
                    change.lastWay = 1;
                }
                else if (key == GLFW_KEY_A) {
                    change.lastWay = 2;
                }
                else if (key == GLFW_KEY_D) {
                    change.lastWay = 3;
                }

            });
            change.newWay();//едет по lastWay
        }
    }
}
