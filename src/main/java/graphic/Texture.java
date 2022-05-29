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

    public static int loadTexture(@NotNull BufferedImage im) {
        int[] pixels = new int[im.getWidth() * im.getHeight()];
        pixels = im.getRGB(0, 0, im.getWidth(), im.getHeight(), pixels, 0, im.getWidth());

        ByteBuffer buffer = BufferUtils.createByteBuffer(im.getHeight() * im.getWidth() * BYTES_PER_PIXEL);
        for (int y = 0; y < im.getHeight(); y++) {
            for (int x = 0; x < im.getWidth(); x++) {
                int pixel = pixels[y * im.getWidth() + x];
                buffer.put((byte) ((pixel >> 16) & 0xff)); // Красный
                buffer.put((byte) ((pixel >> 8) & 0xff)); // Зеленый
                buffer.put((byte) ((pixel & 0xff))); // Синий
                buffer.put((byte) ((pixel >> 24) & 0xff)); // Альфа - прозрачность
            }
        }
        buffer.flip();

        int textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, im.getWidth(), im.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        return textureID;

    }


    public static @Nullable BufferedImage loadImage(String loc) {
        try {

            return ImageIO.read(new File(loc));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static BufferedImage setTexture(int index) {
        switch (index) {
            case 1:
                return loadImage("InputPNG/1.jpg");
            case 2:
                return loadImage("InputPNG/2.jpg");
            case 3:
                return loadImage("InputPNG/3.jpg");
            case 4:
                return loadImage("InputPNG/4.jpg");
            case 5:
                return loadImage("InputPNG/5.jpg");
            case 6:
                return loadImage("InputPNG/6.jpg");
            case 7:
                return loadImage("InputPNG/7.jpg");
            case 8:
                return loadImage("InputPNG/8.jpg");
            case 9:
                return loadImage("InputPNG/9.jpg");
            case 10:
                return loadImage("InputPNG/10.jpg");
            case 11:
                return loadImage("InputPNG/11.jpg");
        }
        return loadImage("InputPNG/11.jpg");
    }

    public static void imageIn1() {
        for (int i = 1; i <= Texture.sizeOfImageDirectory(); i++) {
            BufferedImage im = setTexture(i);
            int id = Texture.loadTexture(im);
            masImages[i-1] = id;
            //System.out.println(id);
        }
    }

    public static int sizeOfImageDirectory() {
        File dir = new File("InputPNG");
        if (!dir.isDirectory() || !dir.exists()) {
            return 0;
        }

        File[] listFiles = dir.listFiles();
        return listFiles.length;
    }

}
