package com.carpediemsolution.dailynotes.newtask;

import android.support.annotation.NonNull;

import com.carpediemsolution.dailynotes.base.baseview.BaseView;
import com.carpediemsolution.dailynotes.model.Task;

import java.util.Date;

public interface TaskView extends BaseView {

    void showSaveSuccess();

    void showMessageTaskIsEmpty();

    void showItem(@NonNull Date date, @NonNull String itemData, String uri);
}
