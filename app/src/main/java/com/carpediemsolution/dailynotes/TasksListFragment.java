package com.carpediemsolution.dailynotes;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.carpediemsolution.dailynotes.adapter.TasksAdapter;
import com.carpediemsolution.dailynotes.dao.HelperFactory;
import com.carpediemsolution.dailynotes.model.Task;
import com.carpediemsolution.dailynotes.utils.OnBackListener;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;
import com.squareup.leakcanary.RefWatcher;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by Юлия on 24.05.2017.
 */

public class TasksListFragment extends Fragment implements OnBackListener {

    private List<Task> taskList;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private TasksAdapter adapter;
     EditTaskFragment editTaskFragment;
     FragmentManager fragmentManager;
     FragmentTransaction fragmentTransaction;

    @BindView(R.id.tool_bar)
     Toolbar mToolbar;

    @BindView(R.id.search)
    EditText searchEditText;

    private Unbinder unbinder;
    private Disposable _disposable;

    private final String LOG_TAG = "TasksListFragment";


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tasks_list_fragment, container, false);

        unbinder = ButterKnife.bind(this, view);

        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        this.setHasOptionsMenu(true);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_add);
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryDark)));

        taskList = new ArrayList<>();

       // recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TasksAdapter(getActivity());

        searchEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    searchEditText.setHint("");
                else
                    searchEditText.setHint("Daily Notes");
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _disposable = //Disposable
                RxTextView.textChangeEvents(searchEditText) //реагирует на изменение в эдиттекст EditText
                        .debounce(400, TimeUnit.MILLISECONDS) // default Scheduler is Computation
                       // .filter(changes -> isNotNullOrEmpty(changes.text().toString()))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(_getSearchObserver());
    }

    private DisposableObserver<TextViewTextChangeEvent> _getSearchObserver() {

        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onComplete() {Log.e(LOG_TAG,"--------- onComplete");
             //   searchEditText.clearFocus();
            }

            @Override
            public void onError(Throwable e) {
                Log.e(LOG_TAG, "--------- on error!");
            }

            @Override
            public void onNext(TextViewTextChangeEvent onTextChangeEvent) {

                taskList = getSearchedTask(onTextChangeEvent.text().toString());
                recyclerView.setAdapter(adapter);
                adapter.setTasks(taskList);
                adapter.notifyDataSetChanged();
                Log.e(LOG_TAG, "--------- on Search " + onTextChangeEvent.text().toString());
                Log.e(LOG_TAG, "--------- on Searched List " + taskList.toString());
            }
        };
    }


    private List<Task> getSearchedTask(String taskSearched) {
        List<Task> tasksSearched = new ArrayList<>();
            tasksSearched = getSharedPreferencesSettings(taskSearched);
        return tasksSearched;
    }



    @OnClick(R.id.fab_add)
    public void onNewTaskClick() {
        NewTaskFragment addTask = new NewTaskFragment();
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fromCont, addTask);
        fragmentTransaction.commit();
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition(); //get position which is swipe
            adapter.notifyItemRemoved(position);//item removed from recylcerview
            Task task = taskList.get(position);
            if (direction == ItemTouchHelper.LEFT) {    //if swipe left

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.MyTheme_Dark_Dialog); //alert for confirm to delete

                builder.setMessage(getString(R.string.sure_to_delete));    //set message
                builder.setPositiveButton(getString(R.string.remove), new DialogInterface.OnClickListener() { //when click on DELETE
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        try {
                            HelperFactory.getHelper().getTaskDAO().delete(task);
                            taskList.remove(position);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        return;
                    }
                }).setNeutralButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {  //not removing items if cancel is done
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.notifyItemRemoved(position + 1);    //notifies the RecyclerView Adapter that data in adapter has been removed at a particular position.
                        adapter.notifyItemRangeChanged(position, adapter.getItemCount());   //notifies the RecyclerView Adapter that positions of element in adapter has been changed from position(removed element index to end of list), please update it.
                        return;
                    }
                }).setNegativeButton(getString(R.string.edit), new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Bundle b=new Bundle();
                        b.putInt("task_id",task.getId());
                        editTaskFragment = new EditTaskFragment();
                        editTaskFragment.setArguments(b);
                        fragmentManager = getFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fromCont, editTaskFragment);
                        fragmentTransaction.commit();

                    }
                })
                        .setCancelable(false);
                AlertDialog dialog = builder.create();
                        dialog.show();
                //for negative side button
                dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorWhite));
               //for positive side button
                dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorWhite));
            }
        }
    };

    private List<Task> getSharedPreferencesSettings(String s){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        boolean pref = sharedPrefs.getBoolean("sort",false);

        if(pref == true){
            try {
                taskList = HelperFactory.getHelper().getTaskDAO().getAllTasksBySearchStringOrderedByChecked(s);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                taskList = HelperFactory.getHelper().getTaskDAO().getAllTasksBySearchStringOrderedByDate(s);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return taskList;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent remindIntent = new Intent(getActivity(), UserSettingActivity.class);
            startActivity(remindIntent);
            return true;}
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        getActivity().finishAffinity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        _disposable.dispose();
        unbinder.unbind();
        RefWatcher refWatcher = TaskApplication.getRefWatcher(getActivity());
       refWatcher.watch(this);
    }
}