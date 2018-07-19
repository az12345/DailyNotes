package com.carpediemsolution.dailynotes.itemslist.model;

import android.support.annotation.NonNull;

import com.carpediemsolution.dailynotes.dao.HelperFactory;
import com.carpediemsolution.dailynotes.model.Task;
import com.carpediemsolution.dailynotes.utils.Log;

import java.sql.SQLException;
import java.util.List;

public class ItemsInteractor implements ItemsInteractorImpl {

    @Override
    public void loadAllItems(@NonNull LoaderListener loaderListener) {
        //todo
        List<Task> tasks = null;
        try {
            tasks = HelperFactory.getAllTasks();
        } catch (SQLException e) {
            Log.v("sql ex!" + e.toString());
        }
        if(tasks !=null)
        loaderListener.onFinished(tasks);
    }
}
