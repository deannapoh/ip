package boo.misc;

import boo.task.Task;

import java.util.HashMap;

/**
 * Represents a simple implementation of the Storage class that is used for unit testing.
 * No tasks are saved into a hard drive.
 * There are only 2 tasks present in the tasksMap.
 */
public class StorageStub extends Storage{

    /**
     * Constructs a mock Storage object.
     * Nothing is saved in a hard disk.
     */
    public StorageStub() {
        super(""); // passing an empty file path
    }

    /**
     * Mock saveTask method.
     * Nothing is saved in the hard disk.
     *
     * @param tasksMap List of tasks.
     */
    @Override
    public void saveTask(HashMap<Integer, Task> tasksMap){
    }

    /**
     * Mock loadTask method.
     * Only two tasks are present in the list of tasks.
     */
    @Override
    public HashMap<Integer, Task> loadTasks(){
       HashMap<Integer, Task> tasksMap = new HashMap<>();
       tasksMap.put(1, new Task("Assignment"));
       tasksMap.put(2, new Task("Assignment"));
       return tasksMap;
    }
}
