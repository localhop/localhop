package com.localhop.swipe.viewpagersupport;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * This code is open sourced from the link below.
 *
 * This class provides the "Zoom Out" page transformation feature (if we decide to use it)
 * To use this, all we need to do is call:
 * pager.setPageTransformer(true, new ZoomOutPageTransformer());
 * where "pager" is our ViewPager instance.
 *
 * Please visit http://developer.android.com/training/animation/screen-slide.html#zoom-out
 * for more details on this customization option.
 */
public class ZoomOutPageTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.65f;  // Change this value to change the "fade" effect

    private static final float MIN_ALPHA = 0.5f;

    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);

        } else if (position <= 1) { // [-1,1]
            // Modify the default slide transition to shrink the page as well
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
            if (position < 0) {
                view.setTranslationX(horzMargin - vertMargin / 2);
            } else {
                view.setTranslationX(-horzMargin + vertMargin / 2);
            }

            // Scale the page down (between MIN_SCALE and 1)
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

            // Fade the page relative to its size.
            view.setAlpha(MIN_ALPHA +
                    (scaleFactor - MIN_SCALE) /
                            (1 - MIN_SCALE) * (1 - MIN_ALPHA));

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
        }
    }
}