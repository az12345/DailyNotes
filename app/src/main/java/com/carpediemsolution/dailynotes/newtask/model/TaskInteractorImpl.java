package com.carpediemsolution.dailynotes.newtask.model;

import android.support.annotation.NonNull;



public interface TaskInteractorImpl {

    void saveItem(@NonNull String taskData, @NonNull TaskLoaderListener taskLoaderListener);

    void editItem(@NonNull TaskLoaderListener loaderListener, int idItem);

    void getItemById(@NonNull TaskLoaderListener loaderListener, int idItem);
}
