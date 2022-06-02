import controller.Controller;
import controller.ControllerMouse;
import graphic.GlDraw;
import model.Display;
import model.ModelMainSnake;
import model.user.ModelUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class MainTest {
    // игровые параметры
    /*    private static int gridHeight = 40;
        private static int gridWidth = 80;
        private static int cellSize = 20;
        private static boolean withReplays = false; // пишем реплеи?
        private static int windowHeight = gridHeight * cellSize;
        private static int windowWidth = gridWidth * cellSize;
        private static double delayForController = 0.3;
        private static boolean styleGridWithGrid = false;*/
    @Test
    public void snakesTest() {

        // для тестов есть специальные тестовые файлы
        // они просматриваются как реплеи и в них проиграны основные сложные моменты

        GlDraw glDraw = new GlDraw(40, 80);
        ModelUser user = new ModelUser(40, 80);
        ModelMainSnake changeModel = new ModelMainSnake(40, 80, false);
        ControllerMouse controlMouse = new ControllerMouse(user, 20, 40, 80);
        Controller controller = new Controller(changeModel, 0.3); ///////////////
        Display helloGL = new Display(1600, 800, 80, 40, "Snake", 20,
                false, 0.3, false,
                glDraw, user, controlMouse, changeModel, controller, true);
        helloGL.run();


        // выбираем один из тестов
        // если змейка проходит сквозь стену или не ест бонус, или не увеличивается, значит что-то не так

    }
}
