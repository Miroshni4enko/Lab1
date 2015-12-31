package controller;

import model.TextTaskIO;
import view.MainFrame;

import java.io.File;

/**
 * Created by Слава on 22.12.2015.
 */
public class TestProgram {
    public static void main(String[] args) {
        File f = new File("Manager.txt");
        MainFrame frame = new MainFrame();
        TaskController con = new TaskController(frame, f,new TextTaskIO());
    }
}
