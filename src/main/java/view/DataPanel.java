package view;

import model.Task;
import model.TextTaskIO;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

/**
 * Created by Слава on 22.12.2015.
 */
public class DataPanel extends JPanel {
    private Task task;
    private JLabel start;
    private JLabel time ;
    private JLabel end ;
    private JLabel interval;
    private JCheckBox active;
    public  void newPanel() {
        start = new JLabel(" ");
        time = new JLabel(" ");
        end = new JLabel(" ");
        interval = new JLabel(" ");
        active = new JCheckBox(" ");
        this.setLayout(new MigLayout());
        add(start, "wrap");
        add(time, "wrap");
        add(end, "wrap");
        add(interval, "wrap");
        add(active);
    }
    public DataPanel(Task task) {
        this.task = task;
        newPanel();
        setData(task);
    }
    public  void setText (Task task){

    }
    public void setData(Task task) {
        if (task == null){
            start.setText("-------");
            end.setText("-------");
            interval.setText("-------");
            time.setText("-------");
        }else {
            if (task.isRepeated()) {
                start.setText(task.getStartTime().toString());
                end.setText(task.getEndTime().toString());
                interval.setText("["+ TextTaskIO.intervalToStr(task.getRepeatInterval()));
                time.setText("-------");
                active.setSelected(task.isActive());
            } else {
                start.setText("-------");
                end.setText("-------");
                interval.setText("-------");
                time.setText(task.getTime().toString());
                active.setSelected(task.isActive());

            }
        }
    }
}
