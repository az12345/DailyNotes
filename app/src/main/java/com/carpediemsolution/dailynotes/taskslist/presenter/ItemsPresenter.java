package com.carpediemsolution.dailynotes.taskslist.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.carpediemsolution.dailynotes.base.BasePresenter;
import com.carpediemsolution.dailynotes.model.Task;
import com.carpediemsolution.dailynotes.taskslist.model.ItemsInteractor;
import com.carpediemsolution.dailynotes.taskslist.model.LoaderListener;
import com.carpediemsolution.dailynotes.taskslist.view.ItemsView;

import java.util.List;

@InjectViewState
public class ItemsPresenter extends BasePresenter<ItemsView> implements LoaderListener {

    private ItemsInteractor itemsIteractor;

    public void init(){
        itemsIteractor = new ItemsInteractor();
    }

    public void getItems(){
        itemsIteractor.loadAllItems(this);
    }

    @Override
    public void onFinished(List<Task> tasks) {
        getViewState().showItems(tasks);
    }
}
