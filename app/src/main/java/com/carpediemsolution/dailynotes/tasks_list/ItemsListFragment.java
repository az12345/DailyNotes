package com.carpediemsolution.dailynotes.tasks_list;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.carpediemsolution.dailynotes.new_task.AddTaskActivity;
import com.carpediemsolution.dailynotes.R;
import com.carpediemsolution.dailynotes.UserSettingActivity;
import com.carpediemsolution.dailynotes.adapter.ItemsAdapter;
import com.carpediemsolution.dailynotes.model.Task;
import com.carpediemsolution.dailynotes.presenters.TaskSearchPresenter;
import com.carpediemsolution.dailynotes.utils.OnBackListener;
import com.carpediemsolution.dailynotes.views.TaskSearchView;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by Юлия on 24.05.2017.
 */

public class ItemsListFragment extends MvpAppCompatFragment implements
        OnBackListener, TaskSearchView {

    @InjectPresenter
    TaskSearchPresenter presenter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.tool_bar)
    Toolbar mToolbar;
    @BindView(R.id.search)
    EditText searchEditText;

    private Unbinder unbinder;
    private ItemsAdapter adapter;

    // private final String LOG_TAG = "TasksListFragment";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tasks_list_fragment, container, false);

        unbinder = ButterKnife.bind(this, view);
        //todo
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(mToolbar);
        this.setHasOptionsMenu(true);

        FloatingActionButton fab = view.findViewById(R.id.fab_add);
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryDark)));

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ItemsAdapter(getActivity());

        searchEditText.setOnFocusChangeListener((View v, boolean hasFocus) -> {
            if (hasFocus)
                searchEditText.setHint("");
            else
                searchEditText.setHint(getActivity().getResources().getString(R.string.app_name));
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.init(searchEditText);
    }


    @OnClick(R.id.fab_add)
    public void onNewTaskClick() {
        addNewTask();
    }

    private void addNewTask() {
        Intent intent = new Intent(getActivity(), AddTaskActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.disposeObservable();
        unbinder.unbind();
    }

    @Override
    public void changeDataInRecyclerView(List<Task> taskList) {
        recyclerView.setAdapter(adapter);
        adapter.setTasks(taskList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent remindIntent = new Intent(getActivity(), UserSettingActivity.class);
            startActivity(remindIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //todo
    @Override
    public void onBackPressed() {
        getActivity().finishAffinity();
    }
}