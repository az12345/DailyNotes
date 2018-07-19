package com.carpediemsolution.dailynotes.itemslist.model;

import android.support.annotation.NonNull;


public interface ItemsInteractorImpl {

    void loadAllItems(@NonNull LoaderListener loaderListener);
}
