import controller.Controller;
import controller.ControllerMouse;
import graphic.GlDraw;
import model.Display;
import model.ModelMainSnake;
import model.user.ModelUser;

public class Main {

    // параметры игры
    private static int gridHeight = 40;
    private static int gridWidth = 80;
    private static int cellSize = 20;
    private static boolean withReplays = false; // пишем реплеи?
    private static int windowHeight = gridHeight * cellSize;
    private static int windowWidth = gridWidth * cellSize;
    private static double delayForController = 0.3;
    private static boolean styleGridWithGrid = false;

    public static void main(String[] args) {
        GlDraw glDraw = new GlDraw(gridHeight, gridWidth);
        ModelUser user = new ModelUser(gridHeight, gridWidth);
        ModelMainSnake changeModel = new ModelMainSnake(gridHeight, gridWidth, withReplays);
        ControllerMouse controlMouse = new ControllerMouse(user, cellSize, gridHeight, gridWidth);
        Controller controller = new Controller(changeModel, delayForController); ///////////////
        Display helloGL = new Display(windowWidth, windowHeight, gridWidth, gridHeight, "Snake", cellSize, withReplays, delayForController, styleGridWithGrid, glDraw, user, controlMouse, changeModel, controller);
        helloGL.run();
    }
}