package com.carpediemsolution.dailynotes.newtask.model;

import android.support.annotation.NonNull;


public interface TaskInteractorImpl {

    void loadAllItems(@NonNull String taskData, @NonNull TaskLoaderListener taskLoaderListener);
}
