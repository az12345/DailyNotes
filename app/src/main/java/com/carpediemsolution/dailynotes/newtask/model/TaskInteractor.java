package com.carpediemsolution.dailynotes.newtask.model;

import android.support.annotation.NonNull;

import com.carpediemsolution.dailynotes.dao.HelperFactory;
import com.carpediemsolution.dailynotes.model.Task;
import com.carpediemsolution.dailynotes.utils.Log;

import java.sql.SQLException;

public class TaskInteractor implements TaskInteractorImpl {

    private Task task;

    @Override
    public void saveItem(@NonNull String taskData,
                         @NonNull TaskLoaderListener taskLoaderListener) {
        if (task == null) {
            task = new Task();
        }
        task.setData(taskData);
        task.setDone(false);

        try {
            new HelperFactory().saveTask(task);
            taskLoaderListener.onFinished();

        } catch (SQLException e) {
            Log.v("db error:  ".concat(e.toString()));
            taskLoaderListener.onError();
        }
    }

    @Override
    public void editItem(@NonNull TaskLoaderListener loaderListener, int idItem) {
        try {
            HelperFactory.getTaskById(idItem);
        } catch (SQLException e) {
            loaderListener.onError();
            e.printStackTrace();
        }
    }

    @Override
    public void getItemById(@NonNull TaskLoaderListener loaderListener, int idItem) {
        try {
            task = HelperFactory.getTaskById(idItem);

            loaderListener.showTask(task.getDate(), task.getData(), task.getImageUri());
        } catch (SQLException e) {
            loaderListener.onError();
            e.printStackTrace();
        }
    }
}
