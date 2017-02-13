package com.zozx.glider;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

/**
 * Created by zozx on 16/9/2.
 * the glide request listener wrapper.
 */
class RequestListenerWrapper<T> implements RequestListener<T, GlideDrawable> {

    private final GliderListener listener;

    public RequestListenerWrapper(GliderListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onException(Exception e, T model, Target<GlideDrawable> target, boolean isFirstResource) {
        if (listener != null) {
            listener.onFailed();
        }
        // return false only, glide will handle the image.
        return false;
    }

    @Override
    public boolean onResourceReady(GlideDrawable resource, T model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
        if (listener != null) {
            listener.onReady();
        }
        // return false only, glide will handle the image.
        return false;
    }
}
