import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Replays {
    StringBuilder result = new StringBuilder();
    double timeForTakts = 0.0;
    int simInLine = 0;
    Wall[] masOfWalls;
    AllBonuses bonus = new AllBonuses();
    String allWays = "";
    String nameForReplayFile;

    public Replays() {
        nameForReplayFile = searchNewNameForReplayFile();
    }

    public void writeParameters(boolean withWalls, Wall[] walls, AllBonuses bonuses) {
        if (withWalls) {
            result.append("t ").append(walls.length).append("\n");
            for (int i = 0; i < walls.length; i++) {
                result.append(walls[i].x1).append(" ").append(walls[i].y1).append(" ");
                result.append(walls[i].x2).append(" ").append(walls[i].y2).append("\n");
            }
        } else result.append("f").append("\n");
        result.append("b ").append(bonuses.bonusesExist.length).append("\n");
        for (int i = 0; i < bonuses.bonusesExist.length; i++) {
            result.append(bonuses.bonusesExist[i].x).append(" ").append(bonuses.bonusesExist[i].y).append("\n");
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
                // add bonuses
            } catch (IOException e) {
                throw new IllegalArgumentException("FileWrite");
            }
        }
    }

    public void watchReplayForStr(String file) {

        try {
            int counter = 0;
            BufferedReader br = new BufferedReader(new FileReader(file));
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
                    System.out.println(x1 + " " + y1 + " " + x2 + " " + y2);
                    line = br.readLine();
                }
            } else if (line.split(" ")[0].equals("f")) {
                masOfWalls = new Wall[0];
                line = br.readLine(); //должно быть b - bonus
            } else throw new IllegalArgumentException("ErrorFileContext");
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
            counter = 0; // теперь используется для ходов
            while (line != null) { // записываем ходы
                allWays += line;
                line = br.readLine();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private @NotNull String searchNewNameForReplayFile() {
        String file = "Replays"; // тут можно поменять имя папки для просмотра реплеев

        String name = "Replay";
        File dir = new File(file);
        if (!dir.isDirectory() || !dir.exists()) {
            new File(file).mkdir();
            dir = new File(file);
        }

        File[] listFiles = dir.listFiles();
        String nameToFind = name + listFiles.length + ".txt";
        if (listFiles.length != 0) {
            if (!isOnlyRightFilesInDirectory(listFiles, name))
                throw new IllegalArgumentException("Wrong directory"); // проверка на правильность содержимого папки
            // проверку можно убрать, потому, что мы пишем только "правильные названия файлов"
            // но, не убираем, кто знает, что запишет в папку пользователь
        }

        return file + "/" + name + listFiles.length + ".txt";
    }

    private boolean isOnlyRightFilesInDirectory(File @NotNull [] listFiles, String name) {
        int counter = 0;
        for (File f : listFiles) {
            if (f.getName().equals(name + counter + ".txt")) { // файлы строго по порядку
                // написать бы еще сортировку...
                counter++;
            } else return false;

        }
        return true;
    }

}
