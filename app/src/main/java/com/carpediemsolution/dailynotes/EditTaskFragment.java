package com.carpediemsolution.dailynotes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.carpediemsolution.dailynotes.dao.HelperFactory;
import com.carpediemsolution.dailynotes.model.Task;
import com.carpediemsolution.dailynotes.utils.Constants;
import com.carpediemsolution.dailynotes.utils.OnBackListener;

import java.sql.SQLException;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by Юлия on 13.06.2017.
 */

public class EditTaskFragment extends Fragment implements OnBackListener {

    private static final String LOG_TAG = "EditTaskFragment";
    private Task task;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @BindView(R.id.date_textview)
    TextView dateTextView;
    @BindView(R.id.new_task)
    EditText editTask;

    @OnTextChanged(R.id.new_task)
    public void textChanged(CharSequence s) {
        task.setTask(s.toString());
        Log.d(LOG_TAG, s.toString());
    }

    @OnClick(R.id.fab_write)
    public void onClick() {
        try {
            if(task.getTask()!=null){

                HelperFactory.getHelper().getTaskDAO().update(task);
                TasksListFragment tasksListFragment = new TasksListFragment();
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fromCont, tasksListFragment);
                fragmentTransaction.commit();}
            else {
                Toast toast = Toast.makeText(getActivity(), getString(R.string.insert_task), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.new_task_fragment, container, false);
        ButterKnife.bind(this, view);

        int id = getArguments().getInt(Constants.TASK_ID);
        try {
            task = HelperFactory.getHelper().getTaskDAO().getTaskById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.d(LOG_TAG, "task id " + id);
        editTask.setText(task.getTask());

        dateTextView.setText(DateFormat.format("dd.MM.yyyy, HH:mm",new Date(System.currentTimeMillis())));

        return view;
    }

    @Override
    public void onBackPressed() {
        TasksListFragment tasksListFragment = new TasksListFragment();
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fromCont, tasksListFragment);
        fragmentTransaction.commit();
    }

    @Override public void onDestroy() {
        super.onDestroy();
    }
}
