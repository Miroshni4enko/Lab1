package view;

import com.toedter.calendar.JDateChooser;
import model.Task;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

/**
 * Created by Слава on 23.12.2015.
 */
public class Calendar extends JFrame {
    Set<Task> collection;
    DataPanel dp;
    JList listDate;
    JList listTask;
    JDateChooser to;
    JDateChooser from;
    JButton goButton;

    public Calendar() {
        super("Calendar");
        createGUI();
    }

    public Set<Task> getCollection() {
        return collection;
    }

    public JList getListTask() {
        return listTask;
    }

    public JButton getGoButton() {
        return goButton;
    }

    public JList getListDate() {
        return listDate;
    }

    public JDateChooser getTo() {
        return to;
    }

    public JDateChooser getFrom() {
        return from;
    }

    public void setCollection(Set<Task> collection) {
        this.collection = collection;
    }

    public DataPanel getDp() {
        return this.dp;
    }

    void createGUI() {
        final JPanel panel = new JPanel(new MigLayout());

        final JLabel from = new JLabel("From");
        final JLabel to = new JLabel("to");
        panel.add(from);
        panel.add(to,"wrap");

        this.from = new JDateChooser();
        this.from.setDateFormatString("yyyy-MM-dd HH:mm:ss.S");
        panel.add(this.from);

        this.to = new JDateChooser();
        this.to.setDateFormatString("yyyy-MM-dd HH:mm:ss.S");
        panel.add(this.to);

        goButton = new JButton("Go");
        panel.add(goButton,"wrap");

        listDate = new JList();
        JScrollPane jsp = new JScrollPane(listDate);
        jsp.setPreferredSize(new Dimension(215, 100));
        panel.add(jsp);

        listTask = new JList();
        JScrollPane jsp1 = new JScrollPane(listTask);
        jsp1.setPreferredSize(new Dimension(200, 100));
        panel.add(jsp1);

        this.add(panel);
        this.setSize(450, 220);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}



