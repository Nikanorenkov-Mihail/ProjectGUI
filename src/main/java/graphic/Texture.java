package graphic;

import model.Display;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL46.*;

public class Texture {
    private static final int BYTES_PER_PIXEL = 4;
    public static int[] masImages = new int[sizeOfImageDirectory()];

    /**
     * загружаем и активируем текстуру
     *
     * @param index индекст текстуры
     * @return индекс текстуры
     */
    public static int Texture(int index) {
        int textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        BufferedImage image = setTexture(index);
        int[] pixels = new int[image.getWidth() * image.getHeight()];
        pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getHeight() * image.getWidth() * BYTES_PER_PIXEL);
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = pixels[y * image.getWidth() + x];
                buffer.put((byte) ((pixel >> 16) & 0xff)); // Красный
                buffer.put((byte) ((pixel >> 8) & 0xff)); // Зеленый
                buffer.put((byte) ((pixel & 0xff))); // Синий
                buffer.put((byte) ((pixel >> 24) & 0xff)); // Альфа - прозрачность
            }
        }
        buffer.flip();


        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        //System.out.println(image.getHeight() +"  "+ image.getWidth());
        return textureID;


    }

    /**
     * загружаем изображение
     *
     * @param path путь к текстуре
     * @return изображение загружено
     */
    public static @Nullable BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * понимаем, где какая текстура
     *
     * @param index где лежит текстура по индексу
     * @return возвращаем изображение
     */
    private static BufferedImage setTexture(int index) {
        switch (index) {
            case 1:
            case 3:
            case 4:
            case 2:
                return loadImage("InputPNG/1.jpg");
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return loadImage("InputPNG/2.jpg");
            case 10:
            case 11:
                return loadImage("InputPNG/3.jpg");
            case 12:
                return loadImage("InputPNG/cat.jpg");
            case 13:
                return loadImage("InputPNG/cat2.jpg");
        }
        return loadImage("InputPNG/11.jpg");
    }

    /**
     * на всякий случай проверим количество элементов в директории
     *
     * @return возвращаем количество элементов в директории
     */
    public static int sizeOfImageDirectory() {
        File dir = new File("InputPNG");
        if (!dir.isDirectory() || !dir.exists()) {
            return 0;
        }

        File[] listFiles = dir.listFiles();
        return listFiles.length;
    }

}
