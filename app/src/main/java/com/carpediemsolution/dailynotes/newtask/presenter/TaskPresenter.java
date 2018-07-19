package com.carpediemsolution.dailynotes.newtask.presenter;


import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.carpediemsolution.dailynotes.base.BasePresenter;
import com.carpediemsolution.dailynotes.newtask.TaskView;
import com.carpediemsolution.dailynotes.newtask.model.TaskInteractor;
import com.carpediemsolution.dailynotes.newtask.model.TaskLoaderListener;

import java.util.Date;


@InjectViewState
public class TaskPresenter extends BasePresenter<TaskView> implements TaskLoaderListener {

    private TaskInteractor taskInteractor;

    public void init() {
        taskInteractor = new TaskInteractor();
    }

    public void saveTask(String taskData) {

        if (taskData != null && !"".equals(taskData)) {
            taskInteractor.saveItem(taskData, this);


        } else getViewState().showMessageTaskIsEmpty();
    }

    public void getItemById(int idItem){
        taskInteractor.getItemById(this, idItem);
    }

    @Override
    public void onFinished() {
        getViewState().showSaveSuccess();
    }

    @Override
    public void onError() {
        getViewState().showUnknownError();
    }

    @Override
    public void showTask(@NonNull Date date, @NonNull String itemData, String uri) {
        getViewState().showItem(date, itemData, uri);
    }
}
