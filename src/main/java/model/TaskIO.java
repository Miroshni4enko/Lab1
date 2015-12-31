package model;

import java.io.File;
import java.util.List;

/**
 * Created by Слава on 31.12.2015.
 */
public abstract class TaskIO {
    abstract public void read(List<Task> tasks, File file);
    abstract public void write(List<Task> tasks, File file);
}
