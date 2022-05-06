import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Replays {
    StringBuilder result = new StringBuilder();
    double timeForTakts = 0.0;
    int takt = 0;
    int simInLine = 0;

    public void writeParameters(boolean withWalls, Wall[] walls, AllBonuses bonuses) {
        if (withWalls) {
            result.append("t").append("\n");
            for (int i = 0; i < walls.length; i++) {
                result.append(walls[i].x1).append(" ").append(walls[i].y1).append(" ");
                result.append(walls[i].x2).append(" ").append(walls[i].y2).append("\n");
            }
        } else result.append("f").append("\n");
        result.append("b").append("\n");
        for (int i = 0; i < bonuses.bonusesExist.length; i++) {
            result.append(bonuses.bonusesExist[i].x).append(" ").append(bonuses.bonusesExist[i].y).append("\n");
        }
        result.append("GO").append("\n");
    }

    public void writeWays(int key) {
        double currentTime = glfwGetTime();
        if (currentTime - timeForTakts > 0.1) { // delay всегда один
            timeForTakts = currentTime;
            result.append(key);
            simInLine++;
            if (simInLine > 100) {
                simInLine = 0;
                result.append("\n");
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("Replays/replayTest.txt"))) {
                writer.append(result); //!!!!!!!!!!!!
                // add bonuses
            } catch (IOException e) {
                throw new IllegalArgumentException("File");
            }
        }
    }


}