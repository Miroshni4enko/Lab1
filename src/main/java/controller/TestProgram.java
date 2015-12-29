package controller;

import view.MyFrame;

import java.io.File;

/**
 * Created by Слава on 22.12.2015.
 */
public class TestProgram {
    public static void main(String[] args) {
        File f = new File("Manager.txt");
        MyFrame frame = new MyFrame();
        TaskController con = new TaskController(frame, f);
    }
}
