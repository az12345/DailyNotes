package com.carpediemsolution.dailynotes.newtask.model;

import android.support.annotation.NonNull;
import java.util.Date;

public interface TaskLoaderListener {

    void onFinished();

    void onError();

    void showTask(@NonNull Date date, @NonNull String itemData, String uri);
}
