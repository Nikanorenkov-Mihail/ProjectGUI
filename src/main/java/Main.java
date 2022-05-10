public class Main {
    private static int gridHeight = 40;
    private static int gridWidth = 80;
    private static int cellSize = 20;
    private static boolean withReplays = true; // пишем реплеи?
    private static int windowHeight = gridHeight * cellSize;
    private static int windowWidth = gridWidth * cellSize;

    public static void main(String[] args) {
        HelloGL helloGL = new HelloGL(windowWidth, windowHeight, gridWidth, gridHeight, "Snake",cellSize, withReplays);
        helloGL.run();
    }
}