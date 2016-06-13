package com.vidumanszky.vidimageview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.vidumanszky.vidimageview.vid_image_view.VidImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VidImageView vidImageView = (VidImageView) findViewById(R.id.imgVid);
        vidImageView.setHeight(300);
        vidImageView.setWidth(300);

        vidImageView.setImageResources(R.drawable.zenit_12xp_no_objective_drawing_black4_37);
    }
}
