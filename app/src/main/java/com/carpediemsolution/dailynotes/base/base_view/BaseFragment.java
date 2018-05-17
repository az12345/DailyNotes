package com.carpediemsolution.dailynotes.base.base_view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.carpediemsolution.dailynotes.R;


public abstract class BaseFragment extends MvpAppCompatFragment implements BaseView {

    private Activity activity;
    public ProgressDialog progressDialog;


    @Override
    public void showLoading() {
        activity = getActivity();
        progressDialog = ProgressDialog.show(activity, "", getString(R.string.progress_loading), true);
        progressDialog.setCancelable(true);
      //  progressDialog.setCanceledOnTouchOutside(true);
    }

    @Override
    public void showLoading(@NonNull String loadingText) {

        activity = getActivity();
        progressDialog = ProgressDialog.show(activity, "", loadingText, true);
        progressDialog.setCancelable(true);
      //  progressDialog.setCanceledOnTouchOutside(true);
    }

    @Override
    public void showStopLoading() {
        if (progressDialog.isShowing()) progressDialog.dismiss();
    }

    @Override
    public void showError(@NonNull String errorDescription) {
        activity = getActivity();
        Toast.makeText(activity, errorDescription,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUnknownError() {
        activity = getActivity();
        Toast.makeText(activity, R.string.smth_went_wrong,
                Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
