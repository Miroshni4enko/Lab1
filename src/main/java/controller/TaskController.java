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
    private MainFrame mainFrame;
    private File file;
    private Task taskIndex;
    private TaskIO taskIO;
    public TaskController(MainFrame mainFrame, File file, TaskIO taskIO) {
        this.mainFrame = mainFrame;
        this.file = file;
        this.taskIO = taskIO;
        setTasks();
        tasksTimer(tasks);
        setListener();

    }
    public void setTasks(){
        tasks = new ArrayList<Task>();

        taskIO.read(tasks, file);
        mainFrame.getList().addTasks(tasks);
    }
    public void setListener () {
        mainListListener(mainFrame.getList());
        Menu menu = mainFrame.getMenu();

        newTaskListener(menu.getRepeat(),true);
        newTaskListener(menu.getNrepeat(), false);
        changeTaskListener(menu.getChange());
        removeListener(menu.getRemove());
        calendarListener(menu.getCalendar());
        exitNotSaveListener(menu.getExitItem());
        exitSaveListener(mainFrame);
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
                JOptionPane.showMessageDialog(mainFrame,"Time to do "+task.getTitle() );
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

    public void mainListListener(JList mainList){
        mainList.addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent e) {
                        int index = mainFrame.getList().getAnchorSelectionIndex();
                        taskIndex = tasks.get(index);
                        mainFrame.getDp().setData(taskIndex);
                    }
                });
    }

    public void removeListener(JMenuItem remove){
        remove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (taskIndex != null) {
                    if (taskIndex.isTimer()) taskIndex.getTimer().cancel();
                    tasks.remove(taskIndex);
                    mainFrame.getList().removeTask(taskIndex);
                }
            }
        });
    }

    public void changeTaskListener(JMenuItem change){
        change.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (taskIndex != null) {
                    final TaskChange change = new TaskChange(taskIndex);
                    change.getjButton().addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            change.setTask();
                            if (taskIndex.isTimer()) {
                                taskIndex.getTimer().cancel();
                                timer(taskIndex);
                            } else timer(taskIndex);
                            mainFrame.getList().addTasks(tasks);
                            change.setVisible(false);
                        }
                    });
                }
            }
        });
    }

    public void  calendarListener(JMenuItem calendar){
        calendar.addActionListener( new ActionListener() {
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
    }


    public void exitSaveListener(JFrame frame){
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                Object[] options = { "Да", "Нет!" };
                int n = JOptionPane
                        .showOptionDialog(event.getWindow(), "Закрыть Manager?",
                                "Подтверждение", JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, options,
                                options[0]);
                if (n == 0) {
                    taskIO.write(tasks,file);
                    event.getWindow().setVisible(false);
                    System.exit(1);
                }
            }
        });
    };

    public void exitNotSaveListener(JMenuItem exit){
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public void newTaskListener(JMenuItem newTaskItem, final boolean isRepeated){
        newTaskItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    final NewTask addTask;
                    if(isRepeated)  addTask = new TaskRepeat();
                    else  addTask = new TaskNotRepeat();
                    addTask.getjButtonAdd().addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Task newTask =  addTask.getTask();
                        timer(newTask);
                        tasks.add(newTask);
                        mainFrame.getList().addTask(newTask);
                        addTask.setVisible(false);
                    }
                });
            }
        });

    }
    /*public void newTaskListener(JTextField task, final JTextField block){
        task.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
            }
             @Override
             public void keyReleased(KeyEvent e) {

             }
        }
        );
    } */

}