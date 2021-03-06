package com.carpediemsolution.dailynotes.itemslist.view;

import com.carpediemsolution.dailynotes.base.baseview.BaseView;
import com.carpediemsolution.dailynotes.model.Task;

import java.util.List;

public interface ItemsView extends BaseView {

    void showItems(List<Task> tasks);

}
