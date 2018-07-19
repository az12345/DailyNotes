package com.carpediemsolution.dailynotes.base.baseview;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.carpediemsolution.dailynotes.R;
import com.carpediemsolution.dailynotes.utils.UiUtils;


// базовая активити для обработки общих случаев загрузки + диалоговое окно с Error Message =) *для mvp
public abstract class BaseActivity extends MvpAppCompatActivity implements BaseView {

    private static final String TAG = BaseActivity.class.getSimpleName();

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UiUtils.darkenStatusBar(this, R.color.colorPrimary);
    }

    @Override
    public void showLoading() {
        progressDialog = ProgressDialog.show(this, "", getString(R.string.progress_loading), true);
        setCancelableProgressDialog();
    }

    @Override
    public void showLoading(@NonNull String loadingText) {
        progressDialog = ProgressDialog.show(this, "", loadingText, true);
        setCancelableProgressDialog();
    }


    @Override
    public void showStopLoading() {
        if (progressDialog != null && progressDialog.isShowing()) progressDialog.dismiss();
    }


    @Override
    public void showError(@NonNull String errorDescription) {
        Toast.makeText(this, errorDescription,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUnknownError() {
        Toast.makeText(this, getString(R.string.smth_went_wrong),
                Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void setCancelableProgressDialog() {
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(true);
    }
}
