package com.carpediemsolution.dailynotes.tasks_list.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.carpediemsolution.dailynotes.base.BasePresenter;
import com.carpediemsolution.dailynotes.model.Task;
import com.carpediemsolution.dailynotes.tasks_list.view.ItemsView;

import java.util.ArrayList;
import java.util.List;

@InjectViewState
public class ItemsPresenter extends BasePresenter<ItemsView>{

    public void getItems(){
        //todo
        List<Task> tasks = new ArrayList<>();
        getViewState().showItems(tasks);
    }
}
