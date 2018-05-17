package com.carpediemsolution.dailynotes.tasks_list.presenter;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.EditText;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.carpediemsolution.dailynotes.app.App;
import com.carpediemsolution.dailynotes.dao.HelperFactory;
import com.carpediemsolution.dailynotes.model.Task;
import com.carpediemsolution.dailynotes.utils.Constants;
import com.carpediemsolution.dailynotes.tasks_list.view.TaskSearchView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.sql.SQLException;
import java.util.List;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by Юлия on 23.08.2017.
 */
@InjectViewState
public class TaskSearchPresenter extends MvpPresenter<TaskSearchView> {

    private static final String LOG_TAG = "TaskSearchPresenter";
    private Disposable disposable;
    private List<Task>taskList;

    public void init(EditText editText){

        DisposableObserver observer = new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onComplete() {
                Log.e(LOG_TAG, "---onComplete");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(LOG_TAG, "--- on error!");
            }

            @Override
            public void onNext(TextViewTextChangeEvent onTextChangeEvent) {
                 taskList = getSearchedTask(onTextChangeEvent.text().toString());
                 getViewState().updateItems(taskList);
            }
        };
       //
        disposable  = //Disposable
                RxTextView.textChangeEvents(editText) //реагирует на изменение в эдиттекст EditText
                        .debounce(400, TimeUnit.MILLISECONDS) // default Scheduler is Computation
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(observer);
    }

    public void disposeObservable(){
        disposable.dispose();
    }

    private List<Task> getSearchedTask(String taskSearched) {
        return getSharedPreferencesSettings(taskSearched);
    }

    private List<Task> getSharedPreferencesSettings(String s) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(App.getAppContext());
        boolean pref = sharedPrefs.getBoolean(Constants.SORT, false);

        if (pref) {
            try {
                taskList = HelperFactory.getHelper().getTaskDAO().getAllTasksBySearchStringOrderedByChecked(s);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                taskList = HelperFactory.getHelper().getTaskDAO().getAllTasksBySearchStringOrderedByDate(s);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return taskList;
    }
}
