package com.carpediemsolution.dailynotes.newtask;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.carpediemsolution.dailynotes.R;
import com.carpediemsolution.dailynotes.base.base_view.BaseActivity;
import com.carpediemsolution.dailynotes.utils.PermissionsUtils;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.util.Date;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.carpediemsolution.dailynotes.utils.PermissionsUtils.REQUEST_CODE_STORAGE;


/**
 * Created by Юлия on 24.05.2017.
 */

public class AddTaskActivity extends BaseActivity implements TaskView {

    private final String LOG_TAG = AddTaskActivity.class.getSimpleName();

    private static final int REQUEST_TAKE_PHOTO = 1;

   // private Task task;

    @InjectPresenter
    TaskPresenter taskPresenter;

    @BindView(R.id.image)
    ImageView imageView;
    @BindView(R.id.new_task)
    TextView taskDataTextView;
    @BindView(R.id.date_textview)
    TextView dateTextView;

    @OnClick(R.id.fab_write)
    public void onClick() {
        taskPresenter.saveTask();
    }


    @OnClick(R.id.add_photo)
    public void addImage() {
        PermissionsUtils permissionsUtils = new PermissionsUtils(this);
        if (permissionsUtils.isExternalStoragePermission()) {
            takeImage();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_new_task);
        ButterKnife.bind(this);

        //todo date java 8
        dateTextView.setText(DateFormat.format("dd.MM.yyyy, HH:mm",
                new Date(System.currentTimeMillis())));
    }

    @Override
    public String getTaskData() {
     return taskDataTextView.getText().toString();
    }

    @Override
    public void showSaveSuccess() {
        finish();
    }

    @Override
    public void showMessageTaskIsEmpty() {
        Toast toast = Toast.makeText(this, getString(R.string.insert_task), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void takeImage() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, REQUEST_TAKE_PHOTO);
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
               //todo task.setImageUri(imgDecodableString);

                Picasso.with(this)
                        .load(new File(imgDecodableString))
                        .into(imageView);

            } catch (Exception e) {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                        .show();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_STORAGE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takeImage();
            }
        }

        else {
            //todo
        }
    }
}


