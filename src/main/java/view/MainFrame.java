package view;


import model.Task;
import net.miginfocom.swing.MigLayout;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by Слава on 21.12.2015.
 */

public class MainFrame extends JFrame {
    private List<Task> collection;
    private DataPanel dp;
    private MainList list;
    private Menu menu;
    private static final Logger log = Logger.getLogger(MainFrame.class);
    public List<Task> getCollection() {
        return collection;
    }

    public MainFrame() {
        super("Manager");
        createGUI();
    }

    public void setCollection(List<Task> collection) {
        this.collection = collection;
    }

    public Menu getMenu() {
        return menu;
    }

    public DataPanel getDp() {
        return this.dp;
    }

    public MainList getList() {
        return list;
    }

    void createGUI() {
        log.debug("Start Procass");
        menu = new Menu();
        this.setJMenuBar(menu);

        final JPanel panel = new JPanel(new MigLayout());
        list = new MainList(collection);
        JScrollPane jsp = new JScrollPane(list);
        jsp.setPreferredSize(new Dimension(100, 100));
        panel.add(jsp);

        StartPanel mp = new StartPanel();
        dp = new DataPanel(null);
        panel.add(mp);
        panel.add(dp,"wrap");


        this.add(panel);
        this.setSize(430, 200);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }


}
