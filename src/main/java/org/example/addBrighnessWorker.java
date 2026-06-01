package org.example;

import java.awt.image.BufferedImage;

public class addBrighnessWorker implements Runnable{

    private int percent;
    private int left;
    private int right;
    BufferedImage img;

    public addBrighnessWorker(int percent, int left, int right, BufferedImage img) {
        this.percent = percent;
        this.left = left;
        this.right = right;
        this.img = img;
    }

    public void run() {
        int amount = (int) (percent * 2.55);

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = left; x < right; x++) {
                int color = img.getRGB(x, y);
                int blue = color & 0x0000FF;
                int green = (color & 0x00FF00) >> 8;
                int red = (color & 0xFF0000) >> 16;
                blue += amount;
                green += amount;
                red += amount;

                blue += Math.clamp(blue, 0, 255);
                green += Math.clamp(green, 0, 255);
                red += Math.clamp(red, 0, 255);

                img.setRGB(x, y, blue | (green << 8) | (red << 16));
            }
        }

    }
}