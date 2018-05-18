package com.carpediemsolution.dailynotes.tasks_list;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.carpediemsolution.dailynotes.base.base_view.BaseFragment;
import com.carpediemsolution.dailynotes.new_task.AddTaskActivity;
import com.carpediemsolution.dailynotes.R;
import com.carpediemsolution.dailynotes.utils.view.UserSettingActivity;
import com.carpediemsolution.dailynotes.adapter.ItemsAdapter;
import com.carpediemsolution.dailynotes.model.Task;
import com.carpediemsolution.dailynotes.tasks_list.presenter.ItemsPresenter;
import com.carpediemsolution.dailynotes.tasks_list.presenter.TaskSearchPresenter;
import com.carpediemsolution.dailynotes.tasks_list.view.ItemsView;
import com.carpediemsolution.dailynotes.utils.OnBackListener;
import com.carpediemsolution.dailynotes.tasks_list.view.TaskSearchView;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by Юлия on 24.05.2017.
 */

public class ItemsFragment extends BaseFragment implements
        OnBackListener, TaskSearchView, ItemsView {

   // private final String TAG = ItemsFragment.class.getSimpleName();

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.tool_bar)
    Toolbar mToolbar;
    @BindView(R.id.search)
    EditText searchEditText;
    @BindView(R.id.fab_add)
    FloatingActionButton fab;

    @InjectPresenter
    TaskSearchPresenter presenter;
    @InjectPresenter
    ItemsPresenter itemsPresenter;

    private Unbinder unbinder;
    private ItemsAdapter adapter;

    @OnClick(R.id.fab_add)
    public void onNewTaskClick() {
        addNewTask();
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(mToolbar);
        this.setHasOptionsMenu(true);

        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryDark)));
        setSearchEditTextListener();

        itemsPresenter.getItems();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.init(searchEditText);
    }

    private void addNewTask() {
        Intent intent = new Intent(getActivity(), AddTaskActivity.class);
        startActivity(intent);
    }

    @Override
    public void showItems(List<Task> tasks) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ItemsAdapter(tasks);
    }


    @Override
    public void updateItems(List<Task> taskList) {
        recyclerView.setAdapter(adapter);
        adapter.setTasks(taskList);
        adapter.notifyDataSetChanged();
    }

    private void setSearchEditTextListener() {
        searchEditText.setOnFocusChangeListener((View v, boolean hasFocus) -> {
            if (hasFocus)
                searchEditText.setHint("");
            else
                searchEditText.setHint(getActivity().getResources().getString(R.string.app_name));
        });
    }

    /* base */
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

    private Activity activity = getActivity();

    @Override
    public void onBackPressed() {
        if (activity != null)
            activity.finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.disposeObservable();
        unbinder.unbind();
    }

}