package com.carpediemsolution.dailynotes.newtask.model;

import android.support.annotation.NonNull;

import com.carpediemsolution.dailynotes.dao.HelperFactory;
import com.carpediemsolution.dailynotes.dao.TaskDao;
import com.carpediemsolution.dailynotes.model.Task;
import com.carpediemsolution.dailynotes.utils.Log;

import java.sql.SQLException;
import java.util.Date;

public class TaskInteractor implements TaskInteractorImpl {


    @Override
    public void loadAllItems(@NonNull String taskData,
                             @NonNull TaskLoaderListener taskLoaderListener) {

        Task task = new Task();
        task.setData(taskData);

       // task.setDate(new Date(System.currentTimeMillis()));
        task.setDone(false);

        try {
           new HelperFactory().saveTask(task);
            taskLoaderListener.onFinished();

        } catch (SQLException e) {
            Log.v("db error:  ".concat(e.toString()));
            taskLoaderListener.onError();
        }
    }
}
