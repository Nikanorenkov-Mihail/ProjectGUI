public class Main {
    private static int gridHeight = 40;
    private static int gridWidth = 80;
    private static int cellSize = 20;
    private static boolean withReplays = true; // пишем реплеи?
    private static int windowHeight = gridHeight * cellSize;
    private static int windowWidth = gridWidth * cellSize;
    private static double delayForController = 0.3;
    private static boolean styleGridWithGrid =false;

    public static void main(String[] args) {
        System.out.println("name10.txt".equals("name" + 10 + ".txt"));
        Display helloGL = new Display(windowWidth, windowHeight, gridWidth, gridHeight, "Snake", cellSize, withReplays, delayForController, styleGridWithGrid);
        helloGL.run();
    }
}