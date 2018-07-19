package com.carpediemsolution.dailynotes.utils.view;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.ImageView;

import com.carpediemsolution.dailynotes.R;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Юлия on 01.06.2017.
 */

public class OpenImageActivity extends Activity {

    @BindView(R.id.image_big_size)
    ImageView showImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_show_image);

        ButterKnife.bind(this);

        String photoUri = getIntent().getStringExtra("uri");

        if (photoUri != null) {

            Picasso.with(this)
                    .load(createPhotoFile(photoUri))
                    .into(showImage);
        }
    }

    private File createPhotoFile(@NonNull String photoUri){
        Uri uri = Uri.parse(photoUri);
        return new File(uri.getPath());
    }
}
