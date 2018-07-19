package com.carpediemsolution.dailynotes.base.base_view;

import android.support.annotation.NonNull;
import com.arellomobile.mvp.MvpView;

public interface LoadingView extends MvpView {

    void showLoading();

    void showStopLoading();

    void showLoading(@NonNull String loadingText);
}
