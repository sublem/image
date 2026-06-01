package org.example;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ImageProcessor processor = new ImageProcessor();

        try {
            processor.read("img.png");
            processor.increaseBrightness(-100);
            processor.save("img_copy.png");
        } catch (IOException e) {
            System.out.println("Nie udalo sie odczytac pliku");
        }


    }
    }