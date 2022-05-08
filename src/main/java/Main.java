public class Main {
    private static int gridHeight = 40;
    private static int gridWidth = 80;
    private static int cellSize = 20;
    private static int numberOfWalls = 50; // количество стен, если 0 - игра без стен (больше 50 - играть довольно трудно)
    private static boolean withReplays = true; // пишем реплеи?
    private static int windowHeight = gridHeight * cellSize;
    private static int windowWidth = gridWidth * cellSize;

    public static void main(String[] args) {
        HelloGL helloGL = new HelloGL(windowWidth, windowHeight, gridWidth, gridHeight, "Snake", numberOfWalls, withReplays);
        helloGL.run();
    }
}