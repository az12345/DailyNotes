package com.carpediemsolution.dailynotes.itemslist.model;

import com.carpediemsolution.dailynotes.model.Task;
import java.util.List;


public interface LoaderListener {

    void onFinished(List<Task> tasks);

    void onDeletedSuccessfully();

    void onError();
}
