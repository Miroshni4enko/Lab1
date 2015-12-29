package controller;

import model.*;
import view.*;
import view.Calendar;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import java.util.Timer;

/**
 * Created by Слава on 22.12.2015.
 */
public class TaskController {
    private List<Task> tasks;
    private MyFrame myFrame;
    private File file;
    private Task taskIndex;
    public TaskController(MyFrame myFrame,File file) {
        this.myFrame = myFrame;
        this.file = file;
        setTasks();
        tasksTimer(tasks);
        setListener();

    }
    public void setTasks(){
        tasks = new ArrayList<Task>();
        TaskIO.readText(tasks, file);
        myFrame.getList().addTasks(tasks);
    }
    public void setListener (){
        myFrame.getList().addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent e) {
                        int index = myFrame.getList().getAnchorSelectionIndex();
                        taskIndex =  tasks.get(index);
                        myFrame.getDp().setData(taskIndex);
                    }
                });

        Menu menu =  myFrame.getMenu();
        menu.getExitItem().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menu.getRepeat().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final TaskRepeat m = new TaskRepeat();
                m.getjButton().addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Task newTask =  m.getTask();
                        timer(newTask);
                        tasks.add(newTask);
                        myFrame.getList().addTask(newTask);
                        m.setVisible(false);
                    }
                    });
            }
        });
        menu.getNrepeat().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final TaskNotRepeat m = new TaskNotRepeat();
                m.getjButton().addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Task newTask =  m.getTask();
                        timer(newTask);
                        tasks.add(newTask);
                        myFrame.getList().addTask(newTask);
                        m.setVisible(false);
                    }
                });
            }
        });

        menu.getChange().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               if ( taskIndex != null){
                   final TaskChange change = new TaskChange(taskIndex);
                   change.getjButton().addActionListener(new ActionListener() {
                       public void actionPerformed(ActionEvent e) {
                           change.setTask();
                           if(taskIndex.isTimer()) {
                               taskIndex.getTimer().cancel();
                               timer(taskIndex);
                           }else timer(taskIndex);
                           myFrame.getList().addTasks(tasks);
                           change.setVisible(false);
                       }
                   });
               }
            }
        });
        menu.getRemove().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if ( taskIndex != null){
                    if(taskIndex.isTimer()) taskIndex.getTimer().cancel();
                    tasks.remove(taskIndex);
                    myFrame.getList().removeTask(taskIndex);
                }
            }
        });

        menu.getCalendar().addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final Calendar calendar = new Calendar();
                calendar.getGoButton().addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Date from = calendar.getFrom().getDate();
                        Date to = calendar.getTo().getDate();

                        SortedMap<Date,Set<Task>> test2  = Tasks.calendar(tasks,from,to);
                        Set<SortedMap.Entry<Date,Set<Task>>> set = test2.entrySet();

                        DefaultListModel dateModel = new DefaultListModel();
                        final Set[] s = new Set[set.size()];
                        int i=0;
                        for (Map.Entry<Date,Set<Task>> entry : set){
                            dateModel.addElement(entry.getKey());
                            s[i++] =  entry.getValue();
                        }
                        calendar.getListDate().setModel(dateModel);
                        calendar.getListDate().addListSelectionListener(
                                new ListSelectionListener() {
                                    public void valueChanged(ListSelectionEvent e) {
                                        int index = calendar.getListDate().getAnchorSelectionIndex();
                                        final DefaultListModel taskModel = new DefaultListModel();
                                        Iterator<Task> it = s[index].iterator();
                                        while(it.hasNext()) {
                                            Task element = it.next();
                                            taskModel.addElement(element.getTitle());
                                        }
                                        calendar.getListTask().setModel(taskModel);

                                    }
                                }
                        );
                    }
                });
            }

        });

        myFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                Object[] options = { "Да", "Нет!" };
                int n = JOptionPane
                        .showOptionDialog(event.getWindow(), "Закрыть Manager?",
                                "Подтверждение", JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, options,
                                options[0]);
                if (n == 0) {
                    TaskIO.writeText(tasks,file);
                    event.getWindow().setVisible(false);
                    System.exit(1);
                }
            }
        });
    }
    public void timer(final Task task){
        if(task.nextTimeAfter(new Date()) == null)return;
        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if(task.isRepeated()&& task.getEndTime().before(new Date())) {
                    timer.cancel();
                    task.setTimer(null);
                }
                JOptionPane.showMessageDialog(myFrame,"Time to do "+task.getTitle() );
                if (!task.isRepeated()){
                    timer.cancel();
                    task.setTimer(null);
                }
            }
        };
        if(task.isRepeated()){
            timer.scheduleAtFixedRate(timerTask,task.nextTimeAfter(new Date()),task.getRepeatInterval()*1000);
        }else {
            timer.schedule(timerTask,task.nextTimeAfter(new Date()));
        }
        task.setTimer(timer);
    }
    public void tasksTimer(List<Task> tasks) {
        for (Task task : tasks)
            timer(task);
    }
}