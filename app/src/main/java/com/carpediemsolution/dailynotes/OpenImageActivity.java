package com.carpediemsolution.dailynotes;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
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

        requestWindowFeature (Window.FEATURE_NO_TITLE);

        setContentView(R.layout.open_image_for_view);

        ButterKnife.bind(this);

        String photoUri = getIntent().getStringExtra("uri");

        Uri uri = Uri.parse(photoUri);

        File photoFile = new File(uri.getPath());

        Picasso.with(this)
                .load(photoFile)
                .into(showImage);
    }
}
