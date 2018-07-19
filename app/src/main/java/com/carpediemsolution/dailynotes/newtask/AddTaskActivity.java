package com.carpediemsolution.dailynotes.newtask;


import android.app.Activity;
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
import com.carpediemsolution.dailynotes.base.baseview.BaseActivity;
import com.carpediemsolution.dailynotes.newtask.presenter.TaskPresenter;
import com.carpediemsolution.dailynotes.utils.BundleUtils;
import com.carpediemsolution.dailynotes.utils.PermissionsUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.carpediemsolution.dailynotes.utils.Constant.ITEM_ID;
import static com.carpediemsolution.dailynotes.utils.PermissionsUtils.REQUEST_CODE_STORAGE;


/**
 * Created by Юлия on 24.05.2017.
 */

public class AddTaskActivity extends BaseActivity implements TaskView {

    private final String LOG_TAG = AddTaskActivity.class.getSimpleName();
    private static final int REQUEST_TAKE_PHOTO = 1;

    private static final String INTENT_TYPE = "intent_type";
    private static final String INTENT_ADD_ITEM = "intent_add_item";
    private static final String INTENT_EDIT_ITEM = "intent_edit_item";


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
        taskPresenter.saveTask(taskDataTextView.getText().toString());
    }


    @OnClick(R.id.add_photo)
    public void addImage() {
        PermissionsUtils permissionsUtils = new PermissionsUtils(this);
        if (permissionsUtils.isExternalStoragePermission()) {
            takeImage();
        }
    }

    public static Intent newInstance(Activity activity) {
        Intent intent = new Intent(activity, AddTaskActivity.class);
        intent.putExtra(INTENT_TYPE, INTENT_ADD_ITEM);
        return intent;
    }

    public static Intent newInstance(Activity activity, int idItem) {
        Intent intent = new Intent(activity, AddTaskActivity.class);
        intent.putExtra(ITEM_ID, idItem);
        intent.putExtra(INTENT_TYPE, INTENT_EDIT_ITEM);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_new_task);
        ButterKnife.bind(this);

        initActivityByIntentType();

    }

    private void initActivityByIntentType() {
        taskPresenter.init();
        String intentType = BundleUtils.getBundleString(this, INTENT_TYPE);
        if (INTENT_ADD_ITEM.equals(intentType)) {
            initAddItemView();
        } else taskPresenter.getItemById(BundleUtils.getBundleInt(this, ITEM_ID));
    }

    private void initAddItemView() {
        dateTextView.setText(DateFormat.format("dd.MM.yyyy, HH:mm",
                new Date(System.currentTimeMillis())));
    }


    @Override
    public void showSaveSuccess() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void showMessageTaskIsEmpty() {
        Toast toast = Toast.makeText(this, getString(R.string.insert_task), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    //todo refactor this!
    @Override
    public void showItem(@NonNull Date date, @NonNull String itemData, String uri) {
        dateTextView.setText(DateFormat.format("dd.MM.yyyy, HH:mm", date));
        taskDataTextView.setText(itemData);
    }

    private void takeImage() {
        startActivityForResult(new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI), REQUEST_TAKE_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK && null != data) {
            try {

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);

                if (cursor != null) {
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();

                    //todo task.setImageUri(imgDecodableString);
                    Picasso.with(this)
                            .load(new File(imgDecodableString))
                            .error(R.drawable.image_not_found)
                            .into(imageView);
                }
            } catch (Exception e) {
                Toast.makeText(this, getString(R.string.smth_went_wrong), Toast.LENGTH_LONG)
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
        } else {
            //todo
        }
    }
}


