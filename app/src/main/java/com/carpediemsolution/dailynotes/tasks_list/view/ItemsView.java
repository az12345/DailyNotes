package com.carpediemsolution.dailynotes.tasks_list.view;


import com.carpediemsolution.dailynotes.base.base_view.BaseView;
import com.carpediemsolution.dailynotes.model.Task;

import java.util.List;

public interface ItemsView extends BaseView {

    void showItems(List<Task> tasks);
}
