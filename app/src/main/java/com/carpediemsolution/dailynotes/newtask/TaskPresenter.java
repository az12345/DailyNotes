package com.carpediemsolution.dailynotes.newtask;


import com.arellomobile.mvp.InjectViewState;
import com.carpediemsolution.dailynotes.base.BasePresenter;
import com.carpediemsolution.dailynotes.dao.HelperFactory;
import com.carpediemsolution.dailynotes.model.Task;
import com.carpediemsolution.dailynotes.utils.Log;

import java.sql.SQLException;
import java.util.Date;

@InjectViewState
public class TaskPresenter extends BasePresenter<TaskView> {

    public void saveTask() {

        Task task = new Task();
        task.setTask(getViewState().getTaskData());

        if (task.getTask() != null && !("").equals(task.getTask())) {

            task.setTaskDate(new Date(System.currentTimeMillis()));
            task.setDone(false);

            try {
                HelperFactory.getHelper().getTaskDAO().create(task);
                getViewState().showSaveSuccess();

            } catch (SQLException e) {
                Log.v("db error:  ".concat(e.toString()));
                getViewState().showError("Error while saving!");
            }

        } else getViewState().showMessageTaskIsEmpty();
    }
}
