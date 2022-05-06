import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Replays {


    public void writer(int key) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Replays/replayTest.txt"))) {
            writer.write(key + "\n");
        } catch (IOException e) {
            throw new IllegalArgumentException("File");
        }
    }

}
