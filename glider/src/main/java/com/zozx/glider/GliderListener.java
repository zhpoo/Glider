package com.zozx.glider;

/**
 * Created by zozx on 16/9/2.
 * glider listener ,used to warp the glide requestListener
 */
public interface GliderListener {

    /**
     * invoked when glide is ready to display a image.
     * Note: the glide will handle the image finally,
     * although this method do everything.
     */
    void onReady();

    /**
     * invoked when glide load image on exception.
     * Note: the glide will handle the image finally,
     * although this method do everything.
     */
    void onFailed();
}
