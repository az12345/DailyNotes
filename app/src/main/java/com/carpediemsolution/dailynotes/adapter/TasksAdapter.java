package com.carpediemsolution.dailynotes.adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.carpediemsolution.dailynotes.EditTaskFragment;
import com.carpediemsolution.dailynotes.OpenImageActivity;
import com.carpediemsolution.dailynotes.R;
import com.carpediemsolution.dailynotes.app.App;
import com.carpediemsolution.dailynotes.dao.HelperFactory;
import com.carpediemsolution.dailynotes.model.Task;
import com.carpediemsolution.dailynotes.utils.Constants;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Юлия on 30.05.2017.
 */

public class TasksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int EMPTY_VIEW = 1;
    private Task task;
    private FragmentActivity mActivity;
    private List<Task> tasks;

    public TasksAdapter(FragmentActivity activity) {
        this.mActivity = activity;
    }

    private class EmptyViewHolder extends RecyclerView.ViewHolder {
        private EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class TaskHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView dateTextView;
        private TextView taskTextView;
        private CheckBox doneCheckBox;
        private ImageView imageView;
        private ImageView editImageView;
        private ImageView deleteImageView;

        private TaskHolder(View itemView) {
            super(itemView);

            cardView =  itemView.findViewById(R.id.card_view);
            dateTextView = itemView.findViewById(R.id.date_item_text_view);
            taskTextView =  itemView.findViewById(R.id.task_item_text_view);
            doneCheckBox = itemView.findViewById(R.id.task_checked);
            imageView =  itemView.findViewById(R.id.loaded_image);
            editImageView =  itemView.findViewById(R.id.edit_item);
            deleteImageView = itemView.findViewById(R.id.delete_item);

            doneCheckBox.setOnClickListener(v -> onClickCheckBox());
            imageView.setOnClickListener(v -> onClickImageView());
            editImageView.setOnClickListener(v -> onClickEditView());
            deleteImageView.setOnClickListener(v -> onClickDeleteView());
        }

        private void onClickCheckBox() {
            int position = getLayoutPosition();
            task = tasks.get(position);

            if (task.isDone())
                task.setDone(false);
            else task.setDone(true);

            try {
                HelperFactory.getHelper().getTaskDAO().update(task);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private void onClickImageView() {
            int position = getLayoutPosition();
            task = tasks.get(position);
            Intent intent = new Intent(mActivity, OpenImageActivity.class);
            intent.putExtra("uri", task.getImageUri());
            mActivity.startActivity(intent);
        }

        private void onClickEditView() {
            int position = getLayoutPosition();
            Task task = tasks.get(position);
            Bundle b = new Bundle();
            b.putInt(Constants.TASK_ID, task.getId());
            EditTaskFragment editTaskFragment = new EditTaskFragment();
            FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction;
            editTaskFragment.setArguments(b);

            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fromCont, editTaskFragment);
            fragmentTransaction.commit();
        }

        private void onClickDeleteView() {
            int position = getLayoutPosition();

            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity, R.style.MyTheme_Dark_Dialog); //alert for confirm to delete

            builder.setMessage(mActivity.getString(R.string.sure_to_delete));    //set message
            builder.setNegativeButton(mActivity.getString(R.string.remove), new DialogInterface.OnClickListener() { //when click on DELETE
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        HelperFactory.getHelper().getTaskDAO().delete(task);
                        tasks.remove(position);
                        notifyItemRemoved(position);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }).setPositiveButton(mActivity.getString(R.string.cancel), (DialogInterface dialog, int which)-> {
                    dialog.dismiss();
            });

            AlertDialog dialog = builder.create();
            dialog.show();

            //ссылка на контекст приложения
            dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(App.getAppContext().getResources().getColor(R.color.colorBlack));
            dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(App.getAppContext().getResources().getColor(R.color.colorBlack));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (tasks.size() == 0) {
            return EMPTY_VIEW;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size() > 0 ? tasks.size() : 1;
    }

    public void setTasks(List<Task> taskList) {
        tasks = taskList;
    }
 @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;

        if (viewType == EMPTY_VIEW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_view, parent, false);
            return new EmptyViewHolder(v);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
            return new TaskHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder vho, int position) {
        if (vho instanceof TaskHolder) {

            TaskHolder vh = (TaskHolder) vho;
            task = tasks.get(position);

            vh.cardView.setCardBackgroundColor(Color.parseColor(getCardViewColor()));
            vh.dateTextView.setText(DateFormat.format("dd.MM.yyyy, HH:mm", task.getTaskDate()));
            vh.taskTextView.setText(task.getTask());
            vh.doneCheckBox.setChecked(task.isDone());
            if (task.getImageUri() != null) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(350, 350);
                params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                params.addRule(RelativeLayout.BELOW, R.id.task_item_text_view);
                vh.imageView.setLayoutParams(params);
                Picasso.with(mActivity)
                        .load(new File(task.getImageUri()))
                        .into(vh.imageView);
            }
        }
    }


    private String getCardViewColor() {
        List<String> colors = new ArrayList<>();
        colors.add(Constants.colorOne);
        colors.add(Constants.colorTwo);
        colors.add(Constants.colorThree);
        colors.add(Constants.colorFour);
        colors.add(Constants.colorFive);
        colors.add(Constants.colorSix);
        colors.add(Constants.colorSeven);
        colors.add(Constants.colorEight);

        Collections.shuffle(colors);
        return colors.get(0);
    }
}