package com.carpediemsolution.dailynotes.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.carpediemsolution.dailynotes.MainActivity;
import com.carpediemsolution.dailynotes.OpenImageActivity;
import com.carpediemsolution.dailynotes.R;
import com.carpediemsolution.dailynotes.dao.HelperFactory;
import com.carpediemsolution.dailynotes.model.Task;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Юлия on 30.05.2017.
 */

public class TasksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int EMPTY_VIEW = 1;
    private Task task;
    private Context mContext;

    private List<Task> tasks;

    public  TasksAdapter(Context context){
        this.mContext = context;
    }


    private class EmptyViewHolder extends RecyclerView.ViewHolder {
        private EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cardView;
        TextView dateTextView;
        TextView taskTextView;
        CheckBox doneCheckBox;
        ImageView imageView;


        private TaskHolder(View itemView) {
            super(itemView);

            cardView = (CardView)itemView.findViewById(R.id.card_view);
            dateTextView = (TextView)itemView.findViewById(R.id.date_item_text_view);
            taskTextView = (TextView)itemView.findViewById(R.id.task_item_text_view);
            doneCheckBox = (CheckBox)itemView.findViewById(R.id.task_checked);
            imageView = (ImageView)itemView.findViewById(R.id.loaded_image);

            itemView.setOnClickListener(this);

            doneCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                       int position = getLayoutPosition();
                        task = tasks.get(position);
                        Intent intent = new Intent(mContext, OpenImageActivity.class);
                        intent.putExtra("uri", task.getImageUri());
                        mContext.startActivity(intent);
                    }

            });
        }

        public void onClick(View v) {
            int position = getLayoutPosition();
            task = tasks.get(position);
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

    public void setTasks(List<Task> mtasks) {
        tasks = mtasks;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        if (viewType == EMPTY_VIEW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_view, parent, false);
            EmptyViewHolder evh = new EmptyViewHolder(v);
            return evh;
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
            TaskHolder vh = new TaskHolder(v);
           // v.setBackgroundColor(parent.getContext().getResources().getColor(R.color.color1));
            return vh;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vho, int position) {
        if (vho instanceof TaskHolder) {

            TaskHolder vh = (TaskHolder) vho;
            task = tasks.get(position);

            vh.cardView.setCardBackgroundColor(Color.parseColor(getCardViewColor()));
            vh.dateTextView.setText(DateFormat.format("dd.MM.yyyy, HH:mm",task.getTaskDate()));
            vh.taskTextView.setText(task.getTask());
            vh.doneCheckBox.setChecked(task.isDone());
            if (task.getImageUri()!=null){
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(350,350);
                params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                params.addRule(RelativeLayout.BELOW, R.id.task_item_text_view);
                vh.imageView.setLayoutParams(params);
                Picasso.with(mContext)
                        .load(new File(task.getImageUri()))
                        .into(vh.imageView);
            }
        }
    }

    private String getCardViewColor(){
        List<String> colors = new ArrayList<>();
        colors.add("#f2faf3");
        colors.add("#fffaf0");
        colors.add("#fffafa");
        colors.add("#f0f8ff");
        colors.add("#ededed");
        colors.add("#f8f8ff");
        colors.add("#f5f3f2");
        colors.add("#e6e6e8");

        Collections.shuffle(colors);
        return colors.get(0);
    }
}