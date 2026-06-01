package org.example;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageProcessor {
    BufferedImage img;

    public void read(String path) throws IOException {
        File file = new File(path);

        img = ImageIO.read(file);
    }

    public void save(String path) throws IOException {
        File toSave = new File(path);
        ImageIO.write(img,"png",toSave);
    }

    public void increaseBrightness(int percent)
    {
        int amount = (int) (percent * 2.55);

        for( int y=0;y< img.getHeight(); y++)
        {
            for(int x=0;x<img.getWidth();x++)
            {
                int color = img.getRGB(x,y);
                int blue = color & 0x0000FF;
                int green = (color & 0x00FF00)>>8;
                int red = (color & 0xFF0000)>>16;
                blue += amount;
                green +=amount;
                red +=amount;

                blue += Math.clamp(blue,0,255);
                green +=Math.clamp(green,0,255);
                red +=Math.clamp(red,0,255);

                img.setRGB(x,y,blue | (green <<8) | (red<<16));
            }
        }
    }

    public void increaseBrightnessThreaded(int percent) throws InterruptedException {
        int cores = Runtime.getRuntime().availableProcessors();
        int columnWidth = img.getWidth()/cores;
        Thread[] threads = new Thread[cores];
        for (int i=0; i<cores; i++)
        {
            addBrighnessWorker worker = new addBrighnessWorker(10*i, i *columnWidth, (i+1)*columnWidth,img);
            threads[i] = new Thread(worker);
            threads[i].start();
        }
        for (int i =0;i<cores;i++)
        {
            threads[i].join();
        }
    }
}
