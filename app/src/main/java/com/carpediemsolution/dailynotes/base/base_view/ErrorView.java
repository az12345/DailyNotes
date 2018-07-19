package com.carpediemsolution.dailynotes.base.base_view;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;

public interface ErrorView extends MvpView {

    void showError(@NonNull String errorDescription);

    void showUnknownError();

}
