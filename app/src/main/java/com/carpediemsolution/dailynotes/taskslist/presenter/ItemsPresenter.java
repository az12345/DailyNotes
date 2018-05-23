package com.carpediemsolution.dailynotes.taskslist.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.carpediemsolution.dailynotes.base.BasePresenter;
import com.carpediemsolution.dailynotes.dao.HelperFactory;
import com.carpediemsolution.dailynotes.model.Task;
import com.carpediemsolution.dailynotes.taskslist.view.ItemsView;
import com.carpediemsolution.dailynotes.utils.Log;

import java.sql.SQLException;

import java.util.List;

@InjectViewState
public class ItemsPresenter extends BasePresenter<ItemsView>{

    public void getItems(){
        //todo
        List<Task> tasks = null;
        try {
            tasks = HelperFactory.getAllTasks();
        } catch (SQLException e) {
            Log.v("sql ex!");
        }
        getViewState().showItems(tasks);
    }
}
