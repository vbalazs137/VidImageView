package com.vidumanszky.vidimageview.vid_image_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.vidumanszky.vidimageview.R;

import java.lang.ref.WeakReference;

/**
 * Created by VBalazs on 2016-06-13.
 */
public class VidImageView extends LinearLayout {

    private Context context;

    private RelativeLayout rlMain;
    private ImageView imageView;

    private int width = 100;
    private int height = 100;
    private final int steps = 2;

    public VidImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_vid_image_view, this, true);

        initUi();
    }

    private void initUi() {
        rlMain = (RelativeLayout) getChildAt(0);
        imageView = (ImageView) rlMain.getChildAt(0);
    }

    public void setImageResources(int resId) {
        new BitmapWorkerTask(imageView, rlMain).execute(resId);
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    private class BitmapWorkerTask extends AsyncTask<Integer, Bitmap, Bitmap> {

        private RelativeLayout rlMain;
        private ProgressBar progressBar;
        private final WeakReference<ImageView> imageViewWeakReference;
        private int data = 0;

        public BitmapWorkerTask(ImageView imageView, RelativeLayout rlMain) {
            imageViewWeakReference = new WeakReference<ImageView>(imageView);
            this.rlMain = rlMain;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressBar = new ProgressBar(context);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);

            rlMain.addView(progressBar, params);

        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            data = params[0];

            int dpWidth = (int) PixelCalculator.pxFromDp(context, width);
            int dpHeight = (int) PixelCalculator.pxFromDp(context, height);

            for (int i = 1; i<=steps; i++) {
                publishProgress(
                        LoadPicture.decodeSampledBitmap(context, data,
                                dpWidth/steps*i,
                                dpHeight/steps*i)
                );

                if (i == steps) {
                    return LoadPicture.decodeSampledBitmap(context, data,
                            dpWidth,
                            dpHeight);
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Bitmap... values) {
            if (imageViewWeakReference != null && values[0] != null) {
                final ImageView imageView = imageViewWeakReference.get();
                if (imageView != null) {
                    imageView.setBackgroundDrawable(new BitmapDrawable(values[0]));
                }
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (imageViewWeakReference != null && bitmap != null) {
                final ImageView imageView = imageViewWeakReference.get();
                if (imageView != null) {
                    imageView.setBackgroundDrawable(new BitmapDrawable(bitmap));
                }
            }

            if (progressBar != null) {
                rlMain.removeView(progressBar);
                progressBar = null;
            }
        }
    }
}
