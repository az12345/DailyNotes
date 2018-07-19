package com.carpediemsolution.dailynotes.itemslist.presenter;

import android.util.Log;
import android.widget.EditText;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.carpediemsolution.dailynotes.dao.HelperFactory;
import com.carpediemsolution.dailynotes.model.Task;
import com.carpediemsolution.dailynotes.itemslist.view.TaskSearchView;
import com.carpediemsolution.dailynotes.utils.PreferencesUtils;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.sql.SQLException;
import java.util.List;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static com.carpediemsolution.dailynotes.utils.PreferencesUtils.SORT;

/**
 * Created by Юлия on 23.08.2017.
 */
//todo interactor

@InjectViewState
public class TaskSearchPresenter extends MvpPresenter<TaskSearchView> {

    private static final String LOG_TAG =TaskSearchPresenter.class.getSimpleName();

    private Disposable disposable;
    private List<Task>taskList;

    public void init(EditText editText){

        DisposableObserver<TextViewTextChangeEvent> observer = new DisposableObserver<TextViewTextChangeEvent>() {
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
        return getItemBySearchString(taskSearched);
    }

    private boolean isItemsSorted(){
        return PreferencesUtils.getBoolData(SORT);
    }

    private List<Task> getItemBySearchString(String s) {

        if (isItemsSorted()) {
            try {
                taskList = HelperFactory.getHelper().getTaskDAO()
                        .getAllTasksBySearchStringOrderedByChecked(s);
            } catch (SQLException e) {
              com.carpediemsolution.dailynotes.utils.Log.v(" error".concat(e.toString()));
            }
        } else {
            try {
                //todo sorted items
                taskList = HelperFactory.getHelper().getTaskDAO()
                        .getAllTasksBySearchStringOrderedByChecked(s);
            } catch (SQLException e) {
                com.carpediemsolution.dailynotes.utils.Log.v(" error".concat(e.toString()));
            }
        }
        return taskList;
    }
}
