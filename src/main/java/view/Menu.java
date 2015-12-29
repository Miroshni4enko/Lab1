package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Слава on 22.12.2015.
 */
public class Menu  extends  JMenuBar{
    private JMenuItem repeat;
    private JMenuItem nrepeat;
    private JMenuItem exitItem;
    private JMenuItem calendar;
    private JMenuItem remove;
    private JMenuItem change;

    public JMenuItem getRemove() {
        return remove;
    }

    public JMenuItem getChange() {
        return change;
    }

    public Menu() {
        setMenu();
    }

    public JMenuItem getCalendar() {
        return calendar;
    }

    public JMenuItem getRepeat() {
        return repeat;
    }

    public JMenuItem getNrepeat() {
        return nrepeat;
    }

    public JMenuItem getExitItem() {
        return exitItem;
    }

    public void  setMenu() {
        Font font = new Font("Verdana", Font.PLAIN, 11);
        JMenu fileMenu = new JMenu("Menu");
        fileMenu.setFont(font);

        JMenu newMenu = new JMenu("New task");
        newMenu.setFont(font);
        fileMenu.add(newMenu);

         repeat = new JMenuItem("Repeat");
        repeat.setFont(font);
        newMenu.add(repeat);

         nrepeat = new JMenuItem("Not Repeat");
        nrepeat.setFont(font);
        newMenu.add(nrepeat);

        JMenu task = new JMenu("Task");
        task.setFont(font);
        fileMenu.add(task);

        remove = new JMenuItem("Remove");
        remove.setFont(font);
        task.add(remove);

        change = new JMenuItem("Change");
        change.setFont(font);
        task.add(change);

        calendar = new JMenuItem("Calendar");
        calendar.setFont(font);
        fileMenu.add(calendar);

        fileMenu.addSeparator();

        exitItem = new JMenuItem("Exit");
        exitItem.setFont(font);
        fileMenu.add(exitItem);

        this.add(fileMenu);
    }
}
