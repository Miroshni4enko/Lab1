package view;

import model.Task;

import javax.swing.*;

/**
 * Created by Слава on 31.12.2015.
 */
public abstract class NewTask extends JFrame{
    abstract public Task getTask();
    abstract public void  setTaskFrame();
    abstract public JButton getjButtonAdd();
}
