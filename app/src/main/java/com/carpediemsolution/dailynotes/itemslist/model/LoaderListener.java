package com.carpediemsolution.dailynotes.itemslist.model;

import android.support.annotation.NonNull;

import com.carpediemsolution.dailynotes.model.Task;
import java.util.List;


public interface LoaderListener {

    void onFinished(List<Task> tasks);

    void onDeletedSuccessfully();

    void onError();

}
