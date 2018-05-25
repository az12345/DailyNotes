package com.carpediemsolution.dailynotes.newtask.presenter;


import com.arellomobile.mvp.InjectViewState;
import com.carpediemsolution.dailynotes.base.BasePresenter;
import com.carpediemsolution.dailynotes.newtask.TaskView;
import com.carpediemsolution.dailynotes.newtask.model.TaskInteractor;
import com.carpediemsolution.dailynotes.newtask.model.TaskLoaderListener;


@InjectViewState
public class TaskPresenter extends BasePresenter<TaskView> implements TaskLoaderListener {

    private TaskInteractor taskInteractor;

    public void init() {
        taskInteractor = new TaskInteractor();
    }

    public void saveTask(String taskData) {

        if (taskData != null && !("").equals(taskData)) {
            taskInteractor.loadAllItems(taskData, this);


        } else getViewState().showMessageTaskIsEmpty();
    }

    @Override
    public void onFinished() {
        getViewState().showSaveSuccess();
    }

    @Override
    public void onError() {
        getViewState().showUnknownError();
    }
}
