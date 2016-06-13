package com.vidumanszky.vidimageview.vid_image_view;

import android.content.Context;

/**
 * Created by VBalazs on 2016-06-13.
 */
public class PixelCalculator {

    public static float dpFromPx(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }


}
