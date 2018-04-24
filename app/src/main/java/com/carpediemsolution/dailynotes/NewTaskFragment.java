package com.carpediemsolution.dailynotes;

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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.carpediemsolution.dailynotes.dao.HelperFactory;
import com.carpediemsolution.dailynotes.model.Task;
import com.carpediemsolution.dailynotes.utils.OnBackListener;
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


/**
 * Created by Юлия on 24.05.2017.
 */

public class NewTaskFragment extends Fragment implements OnBackListener {

    private Task task = new Task();
    private static final int REQUEST_TAKE_PHOTO = 1;
    private final String LOG_TAG = "NewTaskFragment";
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

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
            initTaskListFragment();

        } else {
            //todo  in error
            Toast toast = Toast.makeText(getActivity(), getString(R.string.insert_task), Toast.LENGTH_SHORT);
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


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.new_task_fragment, container, false);
        ButterKnife.bind(this, view);

        dateTextView.setText(DateFormat.format("dd.MM.yyyy, HH:mm", new Date(System.currentTimeMillis())));

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == getActivity().RESULT_OK && null != data) {
            try {
               //todo wtf?
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                //
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                task.setImageUri(imgDecodableString);

                Picasso.with(getActivity())
                        .load(new File(imgDecodableString))
                        .into(imageView);

            } catch (Exception e) {
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG)
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
                result = ContextCompat.checkSelfPermission(getActivity(), p);
                if (result != PackageManager.PERMISSION_GRANTED) {
                    listPermissionsNeeded.add(p);
                }
            }
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(getActivity(),
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

    @Override
    public void onBackPressed() {
        initTaskListFragment();
    }

    private void initTaskListFragment() {
        TasksListFragment tasksListFragment = new TasksListFragment();
        fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fromCont, tasksListFragment);
        fragmentTransaction.commit();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}


