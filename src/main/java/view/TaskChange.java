package view;

import com.toedter.calendar.JDateChooser;
import model.Task;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;

/**
 * Created by Слава on 23.12.2015.
 */
public class TaskChange extends JFrame{
    private JButton jButton;
    private JTextField title;
    private JDateChooser start;
    private JDateChooser time;
    private JDateChooser end;
    private JCheckBox active;
    private JTextField interval;
    private Task task;
    public JButton getjButton() {
        return jButton;
    }

    public TaskChange(Task task) {
        this.task = task;
        setTaskFrame(task);
    }
    public void  setTaskFrame(final Task task) {
        final JPanel panel = new JPanel(new MigLayout());
        title = new JTextField(task.getTitle(),17);
        time = new JDateChooser(task.getTime());
        time.setDateFormatString("yyyy-MM-dd HH:mm:ss.S");
        start = new JDateChooser(task.getStartTime());
        start.setDateFormatString("yyyy-MM-dd HH:mm:ss.S");
        end = new JDateChooser(task.getEndTime());
        end.setDateFormatString("yyyy-MM-dd HH:mm:ss.S");
        active = new JCheckBox();
        active.setSelected(task.isActive());
        active.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(task.isActive()) task.setActive(false);
                else task.setActive(true);
            }
        });
        interval = new JTextField(String.valueOf(task.getRepeatInterval()),10);
        final JLabel ltitle = new JLabel("Title: ");
        final JLabel lstart = new JLabel("Start: ");
        final JLabel ltime = new JLabel("Time: ");
        final JLabel lend = new JLabel("End: ");
        final JLabel linterval = new JLabel("Interval: ");
        final JLabel lactive = new JLabel("Active:");
        jButton = new JButton("Change task");
        panel.add(ltitle);
        panel.add(title,"wrap");
        panel.add(lstart);
        panel.add(start, "wrap");
        panel.add(ltime);
        panel.add(time, "wrap");
        panel.add(lend);
        panel.add(end, "wrap");
        panel.add(linterval);
        panel.add(interval, "wrap");
        panel.add(lactive);
        panel.add(active,"wrap");
        panel.add(jButton,"dock south");
        this.add(panel);
        this.setTitle("New");
        this.setSize(240,220);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
    public void setTask(){
        String intervalStr = this.interval.getText();
        if(intervalStr!= null && !intervalStr.equals("0") && !intervalStr.equals("")) {
             task.setTitle( this.title.getText());
            int interval = Integer.parseInt(intervalStr);
            Date start = this.start.getDate();
            Date end = this.end.getDate();
            task.setTime(start,end,interval);

        }else {
            task.setTitle(this.title.getText());
            task.setTime(this.time.getDate());
        }
    }

}
