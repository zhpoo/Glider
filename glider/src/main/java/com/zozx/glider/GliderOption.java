package com.zozx.glider;

import android.support.annotation.DrawableRes;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;

/**
 * Created by zozx on 16/9/28.
 * option for {@link Glider}
 */
public class GliderOption {

    private final Builder builder;

    private GliderOption(Builder builder) {
        this.builder = builder;
    }

    public ImageView view() {
        return this.builder.view;
    }

    public String uriString() {
        return this.builder.uriString;
    }

    @DrawableRes
    public int uriRes() {
        return this.builder.uriRes;
    }

    public boolean isLoadResource() {
        return this.builder.uriRes != 0;
    }

    public int crossFade() {
        return this.builder.crossFadeDuration;
    }

    @DrawableRes
    public int loading() {
        return this.builder.loading;
    }

    @DrawableRes
    public int error() {
        return this.builder.error;
    }

    public GliderListener listener() {
        return this.builder.listener;
    }

    public TransformType transformType() {
        return this.builder.transformType;
    }

    public int radius() {
        return this.builder.radius;
    }

    public DiskCacheStrategy diskCacheStrategy() {
        return this.builder.diskCacheStrategy;
    }

    @SuppressWarnings("unused")
    public static final class Builder {
        private ImageView view;
        private int uriRes;
        private String uriString;
        private int loading;
        private int error;
        private GliderListener listener;
        private TransformType transformType;
        private int radius;
        private int crossFadeDuration;
        private DiskCacheStrategy diskCacheStrategy;

        /**
         * create a builder for glider option with imageView and file.
         *
         * @param view imageView
         * @param file file
         */
        public Builder(ImageView view, File file) {
            this(view, "file://" + file);
        }

        /**
         * create a builder for glider option with imageView and string uri.
         *
         * @param view imageView
         * @param uri  string uri
         */
        public Builder(ImageView view, String uri) {
            this.view = view;
            this.uriString = uri;
            this.transformType = TransformType.NONE;
            diskCache(true);
        }

        /**
         * create a builder for glider option with imageView and drawable resource.
         *
         * @param view   imageView
         * @param uriRes drawable resource
         */
        public Builder(ImageView view, @DrawableRes int uriRes) {
            this.view = view;
            this.uriRes = uriRes;
            this.transformType = TransformType.NONE;
            diskCache(false);
        }

        /**
         * set save image cache on disk enable.
         */
        public Builder diskCache(boolean cache) {
            this.diskCacheStrategy = cache ? DiskCacheStrategy.ALL : DiskCacheStrategy.NONE;
            return this;
        }

        /**
         * transform the image to a circle image.
         * NOTE: should call only one of the transform* methods,only the latest one will be useful
         *
         * @return this builder
         */
        public Builder transformCircle() {
            checkTransformType();
            this.transformType = TransformType.CIRCLE;
            return this;
        }

        /**
         * transform the image to a rounded image with given radius px.
         * NOTE: should call only one of the transform* methods,only the latest one will be useful
         *
         * @param radius the round corner radius px.
         * @return this builder
         */
        public Builder transformRound(int radius) {
            checkTransformType();
            this.transformType = TransformType.ROUND;
            this.radius = radius;
            return this;
        }

        /**
         * transform the image to a square image.
         * NOTE: should call only one of the transform* methods,only the latest one will be useful
         *
         * @return this builder
         */
        public Builder transformSquare() {
            checkTransformType();
            this.transformType = TransformType.SQUARE;
            return this;
        }

        /**
         * transform the image to a square and rounded image with given round corner radius px.
         * NOTE: should call only one of the transform* methods,only the latest one will be useful
         *
         * @param radiusPx the round corner radius px.
         * @return this builder
         */
        public Builder transformSquareRound(int radiusPx) {
            checkTransformType();
            this.transformType = TransformType.SQUARE_ROUND;
            this.radius = radiusPx;
            return this;
        }

        /**
         * set cross fade duration when image showing, setup with 0 or lower to close.
         *
         * @param duration the duration
         * @return this builder
         */
        public Builder crossFade(int duration) {
            this.crossFadeDuration = duration;
            return this;
        }

        /**
         * set the drawable to be shown when uri error.
         *
         * @param error the error drawable
         * @return this builder
         */
        public Builder error(@DrawableRes int error) {
            this.error = error;
            return this;
        }

        /**
         * set the drawable to be shown when image is loading.
         *
         * @param loading the loading drawable
         * @return this builder
         */
        public Builder loading(@DrawableRes int loading) {
            this.loading = loading;
            return this;
        }

        /**
         * set a glider listener.
         *
         * @param listener glider listener to be set.
         * @return this builder
         */
        public Builder listener(GliderListener listener) {
            this.listener = listener;
            return this;
        }

        private void checkTransformType() {
            if (this.transformType != TransformType.NONE) {
                Log.w(GliderOption.class.getSimpleName(),
                        "should call only one of transform* methods. only the latest one will be useful.");
            }
        }

        /**
         * create a glider option with this builder.
         *
         * @return the created glider option
         */
        public GliderOption create() {
            return new GliderOption(this);
        }
    }
}
