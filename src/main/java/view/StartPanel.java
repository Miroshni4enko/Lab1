package view;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;

/**
 * Created by Слава on 21.12.2015.
 */
public class StartPanel extends JPanel{
    public StartPanel() {
        this.setLayout(new MigLayout());
        final JLabel start = new JLabel("Start: ");
        final JLabel time = new JLabel("Time: ");
        final JLabel end = new JLabel("End: ");
        final JLabel interval = new JLabel("Interval: ");
        final JLabel active = new JLabel("Active:");
        add(start, "wrap");
        add(time, "wrap");
        add(end, "wrap");
        add(interval, "wrap");
        add(active);
    }
}
