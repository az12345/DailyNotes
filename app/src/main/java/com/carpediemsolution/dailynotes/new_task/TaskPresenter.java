package com.carpediemsolution.dailynotes.new_task;


import com.arellomobile.mvp.InjectViewState;
import com.carpediemsolution.dailynotes.base.BasePresenter;
import com.carpediemsolution.dailynotes.dao.HelperFactory;
import com.carpediemsolution.dailynotes.model.Task;

import java.sql.SQLException;
import java.util.Date;

@InjectViewState
public class TaskPresenter extends BasePresenter<TaskView> {

    private static final String TAG = TaskPresenter.class.getSimpleName();

    public void saveTask(Task task) {
        if (task != null && task.getTask() != null && !("").equals(task.getTask())) {

            task.setTaskDate(new Date(System.currentTimeMillis()));
            task.setDone(false);

            try {
                HelperFactory.getHelper().getTaskDAO().create(task);
                getViewState().showSaveSuccess();

            } catch (SQLException e) {
                getViewState().showError("Error while saving!");
            }
            // Log.d(LOG_TAG, "added " + task);
        } else getViewState().showMessageTaskIsEmpty();
    }
}
