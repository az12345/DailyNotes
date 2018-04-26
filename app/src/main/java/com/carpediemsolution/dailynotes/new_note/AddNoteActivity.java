package com.carpediemsolution.dailynotes.new_note;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.carpediemsolution.dailynotes.R;
import com.carpediemsolution.dailynotes.base.base_view.BaseActivity;
import com.carpediemsolution.dailynotes.dao.HelperFactory;
import com.carpediemsolution.dailynotes.model.Task;
import com.carpediemsolution.dailynotes.new_task.AddTaskActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class AddNoteActivity extends BaseActivity {

    private Task task = new Task();

    private static final int REQUEST_TAKE_PHOTO = 1;
    private final String LOG_TAG = AddTaskActivity.class.getSimpleName();


    @BindView(R.id.image)
    ImageView imageView;
    @BindView(R.id.date_textview)
    TextView dateTextView;

    @OnTextChanged(R.id.new_task)
    public void textChanged(CharSequence s) {
        task.setTask(s.toString());
        Log.d(LOG_TAG, s.toString());
    }

    @OnClick(R.id.fab_write)
    public void onClick() {

        if (task.getTask() != null) {
            //todo
            //in presenter
            saveTask();
            //interface

        } else {
            //todo  in error
            Toast toast = Toast.makeText(this, getString(R.string.insert_task), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }


    @OnClick(R.id.add_photo)
    public void addImage() {
        if (Build.VERSION.SDK_INT >= 23) {
            checkPermissions();
        }
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, REQUEST_TAKE_PHOTO);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_task_fragment);
        ButterKnife.bind(this);

        dateTextView.setText(DateFormat.format("dd.MM.yyyy, HH:mm", new Date(System.currentTimeMillis())));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK && null != data) {
            try {
                //todo wtf?
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                //
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                task.setImageUri(imgDecodableString);

                Picasso.with(this)
                        .load(new File(imgDecodableString))
                        .into(imageView);

            } catch (Exception e) {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                        .show();
            }
        }
    }

    private String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,};

    private boolean checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            int result;
            List<String> listPermissionsNeeded = new ArrayList<>();
            for (String p : permissions) {
                result = ContextCompat.checkSelfPermission(this, p);
                if (result != PackageManager.PERMISSION_GRANTED) {
                    listPermissionsNeeded.add(p);
                }
            }
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this,
                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                        100);
                return false;
            }
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
            return;
        }
    }


    private void saveTask() {
        task.setTaskDate(new Date(System.currentTimeMillis()));
        task.setDone(false);
        try {
            HelperFactory.getHelper().getTaskDAO().create(task);
        } catch (SQLException e) {
            //todo toast

        }
        Log.d(LOG_TAG, "added " + task);
    }
}
