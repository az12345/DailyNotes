package com.carpediemsolution.dailynotes.itemslist.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.carpediemsolution.dailynotes.R;
import com.carpediemsolution.dailynotes.app.App;
import com.carpediemsolution.dailynotes.base.BasePresenter;
import com.carpediemsolution.dailynotes.model.Task;
import com.carpediemsolution.dailynotes.itemslist.model.ItemsInteractor;
import com.carpediemsolution.dailynotes.itemslist.model.LoaderListener;
import com.carpediemsolution.dailynotes.itemslist.view.ItemsView;
import java.util.List;

@InjectViewState
public class ItemsPresenter extends BasePresenter<ItemsView> implements LoaderListener {

    private ItemsInteractor itemsIteractor;

    public void init() {
        itemsIteractor = new ItemsInteractor();
    }

    public void getItems() {
        itemsIteractor.loadAllItems(this);
    }

    @Override
    public void onFinished(List<Task> tasks) {
        getViewState().showItems(tasks);
    }

    @Override
    public void onDeletedSuccessfully() {
        getItems();
    }

    @Override
    public void onError() {
        getViewState().showError(App.getAppContext().getString(R.string.error_deleting));
    }


    public void deleteItem(int idTask) {
        itemsIteractor.deleteItem(this, idTask);
    }

}
