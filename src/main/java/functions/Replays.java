package functions;

import model.AllBonuses;
import model.Bonus;
import model.Wall;
import model.user.ModelUser;
import org.jetbrains.annotations.NotNull;

import java.io.*;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Replays {
    int gridHight, gridWidth;
    StringBuilder result = new StringBuilder();
    double timeForTakts = 0.0;
    int simInLine = 0;
    public Wall[] masOfWalls;
    public AllBonuses bonus;
    public String allWays = "";
    String nameForReplayFile;
    private final static String fileDirectory = "Replays"; // тут можно поменять имя папки для просмотра реплеев
    String name = "Replay";


    public Replays(int gridHight, int gridWidth) {
        this.gridWidth = gridWidth;
        this.gridHight = gridHight;
        bonus = new AllBonuses();
        nameForReplayFile = searchNewNameForReplayFile();
    }

    public void writeParameters(boolean withWalls, Wall[] walls, Bonus[] bonuses) {
        if (withWalls) {
            result.append("t ").append(walls.length).append("\n");
            for (int i = 0; i < walls.length; i++) {
                result.append(walls[i].x1).append(" ").append(walls[i].y1).append(" ");
                result.append(walls[i].x2).append(" ").append(walls[i].y2).append("\n");
            }
        } else result.append("f").append("\n");
        result.append("b ").append(bonuses.length).append("\n");
        for (int i = 0; i < bonuses.length; i++) {
            result.append(bonuses[i].x).append(" ").append(bonuses[i].y).append("\n");
        }
        result.append("GO").append("\n");
    }

    public void writeWays(int key, double delay) {
        double currentTime = glfwGetTime();
        if (currentTime - timeForTakts > delay) {
            timeForTakts = currentTime;
            result.append(key);
            simInLine++;
            if (simInLine > 100) {
                simInLine = 0;
                result.append("\n");
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(nameForReplayFile))) {
                writer.append(result);
            } catch (IOException e) {
                throw new IllegalArgumentException("FileWrite");
            }
        }
    }

    public void watchReplayForStr(int numberOfReplay, boolean isItTest) {
        if (!ModelUser.isRightNumber(numberOfReplay)) throw new IllegalArgumentException("Wrong number of replay");
        if(isItTest){
            if (!ModelUser.isRightNumberTest(numberOfReplay)) throw new IllegalArgumentException("Wrong number of replay");

        }
        try {
            int counter = 0;

            BufferedReader br = new BufferedReader(new FileReader((isItTest) ? "TestFiles/" + numberOfReplay + ".txt" : fileDirectory + "/" + name + numberOfReplay + ".txt"));
            String line = br.readLine();
            if (line.split(" ")[0].equals("t")) {
                int size = Integer.parseInt(line.split(" ")[1]);
                masOfWalls = new Wall[size];

                int x1;
                int y1;
                int x2;
                int y2;
                line = br.readLine();
                while (!line.split(" ")[0].equals("b")) {
                    x1 = Integer.parseInt(line.split(" ")[0]);
                    y1 = Integer.parseInt(line.split(" ")[1]);
                    x2 = Integer.parseInt(line.split(" ")[2]);
                    y2 = Integer.parseInt(line.split(" ")[3]);
                    masOfWalls[counter] = new Wall(x1, y1, x2, y2);
                    counter++;
                    line = br.readLine();
                }
            } else if (line.split(" ")[0].equals("f")) {
                masOfWalls = new Wall[0];
                line = br.readLine(); //должно быть b - bonus
            } else throw new IllegalArgumentException("WrongFileContext");
            counter = 0;

            line = br.readLine();
            int x;
            int y;
            while (!line.split(" ")[0].equals("GO")) { // записываем бонусы в массив бонусов
                x = Integer.parseInt(line.split(" ")[0]);
                y = Integer.parseInt(line.split(" ")[1]);
                bonus.bonusesExist[counter] = new Bonus(x, y);
                counter++;
                line = br.readLine();
            }
            line = br.readLine();
            while (line != null) { // записываем ходы
                allWays += line;
                line = br.readLine();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private @NotNull String searchNewNameForReplayFile() {

        File dir = new File(fileDirectory);
        if (!dir.isDirectory() || !dir.exists()) {
            new File(fileDirectory).mkdir();
            dir = new File(fileDirectory);
        }

        File[] listFiles = dir.listFiles();

        if (listFiles.length != 0) {
            if (!isOnlyRightFilesInDirectory(listFiles, name)) {
                throw new IllegalArgumentException("Wrong directory");
            }// проверка на правильность содержимого папки
            // проверку можно убрать, потому, что мы пишем только "правильные названия файлов"
            // но, не убираем, кто знает, что запишет в папку пользователь
        }

        return fileDirectory + "/" + name + (listFiles.length + 1) + ".txt";
    }

    private boolean isOnlyRightFilesInDirectory(File @NotNull [] listFiles, String name) {
        boolean test = false;
        for (int i = 1; i <= listFiles.length; i++) {
            for (File f : listFiles) {
                if (f.getName().equals(name + (i) + ".txt")) { // файлы не строго по порядку
                    test = true;
                    break;
                }
            }
            if (test) test = false;
            else return false;
        }
        return true;
    }

    // пока что реплеев может быть очень много, что-то с этим нужно сделать...
    public static int numberOfReplays() {
        File dir = new File(fileDirectory);
        if (!dir.isDirectory() || !dir.exists()) {
            return 0;
        }

        File[] listFiles = dir.listFiles();
        return listFiles.length;
    }

    public static int numberOfReplaysTest() {
        File dir = new File("TestFiles");
        if (!dir.isDirectory() || !dir.exists()) {
            return 0;
        }

        File[] listFiles = dir.listFiles();
        return listFiles.length;
    }


}
