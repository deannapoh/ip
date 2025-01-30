package boo.misc;

import boo.task.Task;
import boo.task.TaskList;

import java.util.HashMap;

public class StorageStub extends Storage{

    public StorageStub() {
        super(""); // Passing an empty file path
    }

    @Override
    public void saveTask(HashMap<Integer, Task> taskMap){
    }

    @Override
    public HashMap<Integer, Task> loadTasks(){
       HashMap<Integer, Task> taskMap = new HashMap<>();
       taskMap.put(1, new Task("Assignment"));
       taskMap.put(2, new Task("Assignment"));
       return taskMap;
    }
}
