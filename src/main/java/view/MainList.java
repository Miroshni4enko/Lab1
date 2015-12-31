package view;

import model.*;

import javax.swing.*;
import java.util.List;
import java.util.Set;

/**
 * Created by Слава on 22.12.2015.
 */
public class MainList extends JList {
    DefaultListModel listModel;
    public MainList(List<Task> tasks) {
        addTasks(tasks);
    }

    public void addTasks(List<Task> tasks) {
        listModel = new DefaultListModel();
        if (tasks == null|| tasks.size() == 0) {
            this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            this.setModel(listModel);
           return;
        } else {
            for (Task t : tasks) {
                listModel.addElement(t.getTitle());
            }
        }
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.setModel(listModel);
    }
    public void addTask(Task task){
        listModel.addElement(task.getTitle());
    }
    public void removeTask(Task task){
        listModel.removeElement(task.getTitle());
    }
}

