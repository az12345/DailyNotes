package com.carpediemsolution.dailynotes.itemslist;

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
import com.carpediemsolution.dailynotes.app.App;
import com.carpediemsolution.dailynotes.base.baseview.BaseFragment;
import com.carpediemsolution.dailynotes.R;
import com.carpediemsolution.dailynotes.newtask.AddTaskActivity;
import com.carpediemsolution.dailynotes.utils.Log;
import com.carpediemsolution.dailynotes.utils.view.UserSettingActivity;
import com.carpediemsolution.dailynotes.adapter.ItemsAdapter;
import com.carpediemsolution.dailynotes.model.Task;
import com.carpediemsolution.dailynotes.itemslist.presenter.ItemsPresenter;
import com.carpediemsolution.dailynotes.itemslist.presenter.TaskSearchPresenter;
import com.carpediemsolution.dailynotes.itemslist.view.ItemsView;

import com.carpediemsolution.dailynotes.itemslist.view.TaskSearchView;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.carpediemsolution.dailynotes.itemslist.MainActivity.REQUEST_CODE_ADD;
import static com.carpediemsolution.dailynotes.itemslist.MainActivity.REQUEST_CODE_EDIT;

/**
 * Created by Юлия on 24.05.2017.
 */

public class ItemsFragment extends BaseFragment implements TaskSearchView,
        ItemsView, MainActivity.OnAddItemListener, ItemsAdapter.OnDeleteItemListener, ItemsAdapter.OnEditItemListener {

    private final String TAG = ItemsFragment.class.getSimpleName();

    private MainActivity activity;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tool_bar)
    Toolbar mToolbar;
    @BindView(R.id.search)
    EditText searchEditText;
    @BindView(R.id.fab_add)
    FloatingActionButton fab;

    @InjectPresenter
    TaskSearchPresenter taskSearchPresenter;
    @InjectPresenter
    ItemsPresenter itemsPresenter;

    private ItemsAdapter adapter;
    private Unbinder unbinder;

    @OnClick(R.id.fab_add)
    public void onNewTaskClick() {
        addNewTask();
    }

    public static ItemsFragment newInstance() {
        return new ItemsFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        activity = (MainActivity) getActivity();

        if (activity != null)
            activity.setOnAddItemListener(this);

        initViews();

        itemsPresenter.init();
        itemsPresenter.getItems();

        return view;
    }

    private void initViews() {
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(mToolbar);
        this.setHasOptionsMenu(true);

        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryDark)));
        setSearchEditTextListener();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        taskSearchPresenter.init(searchEditText);
    }


    @Override
    public void showItems(List<Task> tasks) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (adapter == null) {
            adapter = new ItemsAdapter(tasks);
            adapter.setOnDeleteItemListener(this);
            adapter.setOnEditItemListener(this);
        } else adapter.setData(tasks);
    }


    @Override
    public void updateItems(List<Task> taskList) {
        recyclerView.setAdapter(adapter);
        adapter.setTasks(taskList);
        adapter.notifyDataSetChanged();
    }

    private void setSearchEditTextListener() {
        searchEditText.setOnFocusChangeListener((View v, boolean hasFocus) -> {
            if (hasFocus) {
                searchEditText.setHint("");
            } else {
                searchEditText.setHint(App.getAppContext().getResources().getString(R.string.app_name));
            }
        });
    }

    private void addNewTask() {
        if (getActivity() != null)
            getActivity().startActivityForResult(AddTaskActivity.newInstance(getActivity()), REQUEST_CODE_ADD);
    }

    @Override
    public void onItemAdded() {
        Log.v("Item added successfully!");
        itemsPresenter.getItems();
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        taskSearchPresenter.disposeObservable();
        unbinder.unbind();
    }

    @Override
    public void onItemDeleted(int idTask) {
        itemsPresenter.deleteItem(idTask);
    }

    @Override
    public void onItemEdited(int idItem) {
        startActivityForResult(AddTaskActivity.newInstance(getActivity(), idItem), REQUEST_CODE_EDIT);
    }
}