package com.carpediemsolution.dailynotes.itemslist.view;


import com.arellomobile.mvp.MvpView;
import com.carpediemsolution.dailynotes.model.Task;

import java.util.List;

/**
 * Created by Юлия on 23.08.2017.
 */

public interface TaskSearchView extends MvpView{

    void updateItems(List<Task> taskList);
}
