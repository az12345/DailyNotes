package com.carpediemsolution.dailynotes.newtask;

import com.carpediemsolution.dailynotes.base.base_view.BaseView;

public interface TaskView extends BaseView {

    String getTaskData();

    void showSaveSuccess();

    void showMessageTaskIsEmpty();
}
