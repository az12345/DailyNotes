package com.carpediemsolution.dailynotes.taskslist.model;

import android.support.annotation.NonNull;


public interface ItemsInteractorImpl {

    void loadAllItems(@NonNull LoaderListener loaderListener);
}
