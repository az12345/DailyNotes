package com.carpediemsolution.dailynotes.itemslist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.carpediemsolution.dailynotes.R;
import com.carpediemsolution.dailynotes.utils.Log;
import com.carpediemsolution.dailynotes.utils.OnBackListener;


public class MainActivity extends AppCompatActivity {

    private static final String ITEMS_FRAGMENT = "items_fragment";
    public static final int REQUEST_CODE = 1;

    private OnAddItemListener listener;

    public void setOnAddItemListener(OnAddItemListener listener) {
        this.listener = listener;
    }

    public static Intent newInstance(Activity activity) {
        return new Intent(activity, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTaskListFragment();

    }

    private void initTaskListFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(ITEMS_FRAGMENT);
        if (fragment == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fromCont, ItemsFragment.newInstance(), ITEMS_FRAGMENT)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .attach(fragment)
                    .commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.v(" item added! " + requestCode);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                listener.onItemAdded();
            }
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        OnBackListener backPressedListener = null;
        for (Fragment fragment : fm.getFragments()) {
            if (fragment instanceof OnBackListener) {
                backPressedListener = (OnBackListener) fragment;
                break;
            }
        }

        if (backPressedListener != null) {
            backPressedListener.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    //todo
                    assert imm != null;
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    public interface OnAddItemListener {
        void onItemAdded();
    }


    //todo
    /***
     * pin enter
     * task tree
     *
     */
}