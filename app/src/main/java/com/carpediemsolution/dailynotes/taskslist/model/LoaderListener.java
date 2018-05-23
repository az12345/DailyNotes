package com.carpediemsolution.dailynotes.taskslist.model;

import com.carpediemsolution.dailynotes.model.Task;
import java.util.List;


public interface LoaderListener {

    void onFinished(List<Task> tasks);
}
