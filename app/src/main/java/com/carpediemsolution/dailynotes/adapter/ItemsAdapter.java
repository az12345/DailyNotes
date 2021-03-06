package com.carpediemsolution.dailynotes.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
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

import com.carpediemsolution.dailynotes.R;
import com.carpediemsolution.dailynotes.model.Task;
import com.carpediemsolution.dailynotes.utils.Constant;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.carpediemsolution.dailynotes.utils.Constant.EMPTY_VIEW;

/**
 * Created by Юлия on 30.05.2017.
 */

public class ItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Task> tasks;

    public ItemsAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void setData(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    private OnDeleteItemListener onDeleteItemListener;
    private OnEditItemListener onEditItemListener;

    public void setOnDeleteItemListener(OnDeleteItemListener onDeleteItemListener) {
        this.onDeleteItemListener = onDeleteItemListener;
    }

    public void setOnEditItemListener(OnEditItemListener onEditItemListener) {
        this.onEditItemListener = onEditItemListener;
    }

    private class EmptyViewHolder extends RecyclerView.ViewHolder {
        private EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class TaskHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView dateTextView, taskTextView;
        private CheckBox doneCheckBox;
        private ImageView imageView, editBtn, deleteBtn;

        private TaskHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();

            cardView = itemView.findViewById(R.id.card_view);
            dateTextView = itemView.findViewById(R.id.date_item_text_view);
            taskTextView = itemView.findViewById(R.id.task_item_text_view);
            doneCheckBox = itemView.findViewById(R.id.task_checked);
            imageView = itemView.findViewById(R.id.loaded_image);
            editBtn = itemView.findViewById(R.id.edit_item);
            deleteBtn = itemView.findViewById(R.id.delete_item);

            doneCheckBox.setOnClickListener(v -> todo());
            imageView.setOnClickListener(v -> todo());

        }
    }

    @Override
    public int getItemViewType(int position) {
        return tasks.size() == 0 ? EMPTY_VIEW : super.getItemViewType(position);
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

        return viewType == EMPTY_VIEW ?
                new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_view, parent, false)) :
                new TaskHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false));

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder vho, int position) {
        if (vho instanceof TaskHolder) {

            TaskHolder vh = (TaskHolder) vho;
            Task task = tasks.get(position);

            vh.cardView.setCardBackgroundColor(Color.parseColor(generateItemColor()));

            //todo
            vh.dateTextView.setText(DateFormat.format("dd.MM.yyyy, HH:mm", task.getDate()));
            vh.taskTextView.setText(task.getData());
            vh.doneCheckBox.setChecked(task.isDone());

            vh.deleteBtn.setOnClickListener(v -> deleteItem(task.getId()));
            vh.editBtn.setOnClickListener(v -> editItem(task.getId()));

            if (task.getImageUri() != null) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(350, 350);
                params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                params.addRule(RelativeLayout.BELOW, R.id.task_item_text_view);
                vh.imageView.setLayoutParams(params);

                Picasso.with(context)
                        .load(new File(task.getImageUri()))
                        .error(R.drawable.image_not_found)
                        .into(vh.imageView);
            }
        }
    }


    private String generateItemColor() {
        List<String> colors = Arrays.asList(Constant.colorOne,
                Constant.colorTwo,
                Constant.colorThree,
                Constant.colorFour,
                Constant.colorFive,
                Constant.colorSix,
                Constant.colorSeven,
                Constant.colorEight);

        Collections.shuffle(colors);
        return colors.get(0);
    }

    private void todo() {

    }

    private void deleteItem(int idItem) {
        onDeleteItemListener.onItemDeleted(idItem);
    }

    private void editItem(int idItem) { onEditItemListener.onItemEdited(idItem); }

    public interface OnDeleteItemListener {
        void onItemDeleted(int idItem);
    }

    public interface OnEditItemListener {
        void onItemEdited(int idItem);
    }

   /* private void onClickCheckBox() {
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
        Intent intent = new Intent(context, OpenImageActivity.class);
        intent.putExtra("uri", task.getImageUri());
        context.startActivity(intent);
    }*/

 /*   private void onClickEditView() {
        int position = getLayoutPosition();
        Task task = tasks.get(position);
        Bundle b = new Bundle();
        b.putInt(Constants.TASK_ID, task.getId());
        //todo
    }

    private void onClickDeleteView() {
        int position = getLayoutPosition();

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyTheme_Dark_Dialog); //alert for confirm to delete

        builder.setMessage(mActivity.getString(R.string.sure_to_delete));    //set message
        builder.setNegativeButton(context.getString(R.string.remove), (DialogInterface dialog, int which) -> {
            try {
                HelperFactory.getHelper().getTaskDAO().delete(task);
                tasks.remove(position);
                notifyItemRemoved(position);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).setPositiveButton(context.getString(R.string.cancel), (DialogInterface dialog, int which) -> {
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();

        //ссылка на контекст приложения
        dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(App.getAppContext().getResources().getColor(R.color.colorBlack));
        dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(App.getAppContext().getResources().getColor(R.color.colorBlack));
    }*/
}

