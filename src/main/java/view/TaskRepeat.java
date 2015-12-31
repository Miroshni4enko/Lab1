package view;

import com.toedter.calendar.JDateChooser;
import model.Task;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;

/**
 * Created by Слава on 22.12.2015.
 */
public class TaskRepeat extends NewTask {
    JButton jButton;
    JTextField title;
    JDateChooser start;
    JDateChooser end;
    JCheckBox active;
    JTextField interval;
    boolean chActive;
    private JPanel panel;
    public JButton getjButtonAdd() {
        return jButton;
    }

   /* public JTextField getInterval() {
        return interval;
    }

    public JTextField getTitlle() {
        return title;
    }*/

    public TaskRepeat(){
        setTaskFrame();
    }
    public void  setTaskFrame() {
         panel = new JPanel(new MigLayout());
         title = new JTextField(15);
         start = new JDateChooser();
         start.setDateFormatString("yyyy-MM-dd HH:mm:ss.S ");
         end = new JDateChooser();
         end.setDateFormatString("yyyy-MM-dd HH:mm:ss.S");
         active = new JCheckBox();
        active.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                chActive = true;
            }
        });
         interval = new JTextField(15);
        final JLabel ltitle = new JLabel("Title: ");
        final JLabel lstart = new JLabel("Start: ");
        final JLabel lend = new JLabel("End: ");
        final JLabel linterval = new JLabel("Interval: ");
        final JLabel lactive = new JLabel("Active:");
        jButton = new JButton("Add task");
        panel.add(ltitle);
        panel.add(title,"wrap");
        panel.add(lstart);
        panel.add(start, "wrap");
        panel.add(lend);
        panel.add(end, "wrap");
        panel.add(linterval);
        panel.add(interval, "wrap");
        panel.add(lactive);
        panel.add(active,"wrap");
       // comboBox();
        panel.add(jButton,"dock south");
        this.add(panel);
        this.setTitle("New");
        this.setSize(240,200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
    public Task getTask(){
        if (title.getText().equals("")|this.start.getDate()==null|this.end.getDate()==null|
                this.interval.getText().equals("")|this.interval.getText().equals("0"))
            JOptionPane.showMessageDialog(this,"One of the fields are filled");

        String title = this.title.getText();
        Date start = this.start.getDate();
        Date end = this.end.getDate();
        int interval = Integer.parseInt(this.interval.getText());
        Task task =  new Task(title, start, end, interval);
        task.setActive(chActive);
        return task;
    }
    /*public void comboBox (){
        Font font = new Font("Verdana", Font.PLAIN, 5);
        String[] comboTime = new String[60];
        for (int i = 0 ;i<60;i++){
        comboTime[i] = String.valueOf(i+1);
        };
        final JLabel label = new JLabel(" ");
        label.setAlignmentX(LEFT_ALIGNMENT);
        label.setFont(font);


        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox box = (JComboBox)e.getSource();
                String item = (String)box.getSelectedItem();
                label.setText(item);
            }
        };

        JComboBox comboSec = new JComboBox(comboTime);
        comboSec.setToolTipText("MAza");
        comboSec.setFont(font);
        comboSec.setAlignmentX(LEFT_ALIGNMENT);
        comboSec.addActionListener(actionListener);

        JComboBox comboMin = new JComboBox(comboTime);
        comboMin.setToolTipText("MAza");
        comboMin.setFont(font);
        comboMin.setAlignmentX(LEFT_ALIGNMENT);
        comboMin.addActionListener(actionListener);
        panel.add(label);
        panel.add(comboSec);
        panel.add(comboMin,"wrap");
    }*/

}

