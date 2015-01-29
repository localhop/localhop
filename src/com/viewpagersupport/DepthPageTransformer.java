package com.viewpagersupport;

import android.support.v4.view.ViewPager;
import android.view.View;


/**
 * This code is open sourced from the link below.
 *
 * This class provides the "Depth" page transformation feature (if we decide to use it)
 * To use this, all we need to do is call:
 * pager.setPageTransformer(true, new DepthPageTransformer());
 * where "pager" is our ViewPager instance.
 *
 * Please visit http://developer.android.com/training/animation/screen-slide.html#zoom-out
 * for more details on this customization option.
 */
public class DepthPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.75f;

    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);

        } else if (position <= 0) { // [-1,0]
            // Use the default slide transition when moving to the left page
            view.setAlpha(1);
            view.setTranslationX(0);
            view.setScaleX(1);
            view.setScaleY(1);

        } else if (position <= 1) { // (0,1]
            // Fade the page out.
            view.setAlpha(1 - position);

            // Counteract the default slide transition
            view.setTranslationX(pageWidth * -position);

            // Scale the page down (between MIN_SCALE and 1)
            float scaleFactor = MIN_SCALE
                    + (1 - MIN_SCALE) * (1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
        }
    }
}
