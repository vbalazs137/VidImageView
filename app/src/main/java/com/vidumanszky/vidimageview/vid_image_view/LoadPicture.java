package com.vidumanszky.vidimageview.vid_image_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by VBalazs on 2016-06-13.
 */
public class LoadPicture {

    public static Bitmap decodeSampledBitmap(Context context, int res, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = getOptions();
        BitmapFactory.decodeResource(context.getResources(), res, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(context.getResources(), res, options);
    }

    private static BitmapFactory.Options getOptions() {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        return options;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

}
