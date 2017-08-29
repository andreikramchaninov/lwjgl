package logic;

import engine.Engine;
import engine.LogicInterface;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        //setting libs location
        System.setProperty("java.library.path", "lib");
        System.setProperty("org.lwjgl.librarypath", new File("lib/natives").getAbsolutePath());

        try {
            LogicInterface logicInterface = new MyLogic();
            Engine engine = new Engine("TEST", 1600, 900, true, logicInterface);
            engine.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

}
