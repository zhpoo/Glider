package com.zozx.glider;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;

import com.zozx.glider.GliderOption.Builder;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.target.ViewTarget;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.CropSquareTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by zozx on 16/8/8.
 * image loader wrap {@link Glide}.
 */
@SuppressWarnings("unused")
public class Glider {

    private static final int DEFAULT_CROSS_FADE_DURATION = 300;
    //    private static final int DEFAULT_LOADING_DRAWABLE = R.drawable.loading;

    private static boolean initialized;

    static {
        initialize();
    }

    private Glider() {
    }

    private static synchronized void initialize() {
        if (initialized) {
            return;
        }
        initialized = true;
        ViewTarget.setTagId(R.id.glide_tag);
    }

    /**
     * clear the cache image on a view.
     *
     * @param view the view you want clear.
     */
    public static void clear(View view) {
        Glide.clear(view);
    }

    /**
     * clear the disk cache ,should call in background.
     *
     * @param context the context
     */
    public static void clearDiskCache(final Context context) {
        Glide.get(context).clearDiskCache();
    }

    /**
     * display a image with given glider option
     *
     * @param option the option
     */
    public static void load(GliderOption option) {
        try {
            if (option == null || option.view() == null || option.view().getContext() == null
                    || (option.view().getContext() instanceof Activity && ((Activity) option.view().getContext()).isFinishing())) {
                return;
            }
            RequestManager manager = Glide.with(option.view().getContext());
            if (option.isLoadResource()) {
                DrawableRequestBuilder<Integer> request = manager.load(option.uriRes()).diskCacheStrategy(option.diskCacheStrategy());
                if (option.listener() != null) {
                    request.listener(new RequestListenerWrapper<Integer>(option.listener()));
                }
                applyRequestOption(request, option);
            } else {
                DrawableRequestBuilder<String> request = manager.load(option.uriString()).diskCacheStrategy(option.diskCacheStrategy());
                if (option.listener() != null) {
                    request.listener(new RequestListenerWrapper<String>(option.listener()));
                }
                applyRequestOption(request, option);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void applyRequestOption(DrawableRequestBuilder request, GliderOption option) {
        if (option.loading() != 0) {
            request.placeholder(option.loading());
        }
        if (option.error() != 0) {
            request.error(option.error());
        }
        if (option.crossFade() > 0) {
            request.crossFade(option.crossFade());
        }
        ImageView view = option.view();
        Context context = view.getContext();
        switch (option.transformType()) {
            case CIRCLE:
                request.bitmapTransform(new CropCircleTransformation(context));
                break;
            case ROUND:
                request.bitmapTransform(new CenterCrop(context), new RoundedCornersTransformation(context, option.radius(), 0));
                break;
            case SQUARE_ROUND:
                request.bitmapTransform(new CropSquareTransformation(context), new RoundedCornersTransformation(context, option.radius(), 0));
                break;
            case SQUARE:
                request.bitmapTransform(new CropSquareTransformation(context));
                break;
        }
        request.into(view);
    }

    /**
     * display a image with given string uri into imageView.
     *
     * @param view the view want to be used to display the image.
     * @param uri  the string uri. can be a url or "file://" + File.
     */
    public static void load(ImageView view, String uri) {
        load(builder(view, uri).create());
    }

    /**
     * display a image with given string uri into imageView.
     *
     * @param view     the view want to be used to display the image.
     * @param uri      the string uri. can be a url or "file://" + File.
     * @param listener the listener with load the uri.
     */
    public static void load(ImageView view, String uri, GliderListener listener) {
        load(builder(view, uri).listener(listener).create());
    }

    /**
     * display a image with given drawable resource into imageView.
     *
     * @param view the view want to be used to display the image.
     * @param res  the drawable resource.
     */
    public static void load(ImageView view, @DrawableRes int res) {
        load(builder(view, res).create());
    }

    /**
     * display a circle image with given string uri into imageView.
     * NOTE:will display default head_men drawable when uri error.
     *
     * @param view the view want to be used to display the image.
     * @param uri  the string uri. can be a url or "file://" + File.
     */
    public static void loadCircle(ImageView view, String uri) {
        load(builder(view, uri)
                .transformCircle()
                .create());
    }

    /**
     * display a circle image with given drawable resource into imageView
     *
     * @param view the view want to be used to display the image.
     * @param res  the drawable resource.
     */
    public static void loadCircle(ImageView view, @DrawableRes int res) {
        load(builder(view, res)
                .transformCircle()
                .create());
    }

    /**
     * display a circle image with given drawable resource into imageView,
     * allow setting own error drawable when uri error.
     *
     * @param view  the view want to be used to display the image.
     * @param uri   the string uri. can be a url or "file://" + File.
     * @param error the drawable when error happened
     */
    public static void loadCircle(ImageView view, String uri, @DrawableRes int error) {
        load(builder(view, uri)
                .transformCircle()
                .error(error)
                .create());
    }

    /**
     * display a square round image with given string uri into imageView,
     * this method will set the corner with 5 dp.
     *
     * @param view the view want to be used to display the image.
     * @param uri  the string uri. can be a url or "file://" + File.
     */
    public static void loadSquareRound(ImageView view, String uri) {
        loadSquareRound(view, uri, 5);
    }

    /**
     * display a square round image with given drawable resource into imageView,
     * this method will set the corner with 5 dp.
     *
     * @param view the view want to be used to display the image.
     * @param res  the drawable resource.
     */
    public static void loadSquareRound(ImageView view, @DrawableRes int res) {
        loadSquareRound(view, res, 5);
    }

    /**
     * display a square round image with given drawable resource into imageView,
     * allow setting the corner with dp value.
     *
     * @param view     the view want to be used to display the image.
     * @param res      the drawable resource.
     * @param radiusDp the corner dp value to be set.
     */
    public static void loadSquareRound(ImageView view, @DrawableRes int res, float radiusDp) {
        if (view == null) return;
        load(builder(view, res)
                .transformSquareRound(dpToPx(view.getContext(), radiusDp))
                .create());
    }

    /**
     * display a square round image with given string uri into imageView,
     * allow setting the corner with dp value.
     *
     * @param view     the view want to be used to display the image.
     * @param uri      the string uri. can be a url or "file://" + File.
     * @param radiusDp the corner dp value to be set.
     */
    public static void loadSquareRound(ImageView view, String uri, float radiusDp) {
        if (view == null) return;
        load(builder(view, uri)
                .transformSquareRound(dpToPx(view.getContext(), radiusDp))
                .create());
    }

    /**
     * display a rounded corners image with given drawable resource into imageView,
     * this method will set the corner with 5 dp.
     *
     * @param view the imageView to display the image.
     * @param res  the drawable resource
     */
    public static void loadRound(ImageView view, @DrawableRes int res) {
        loadRound(view, res, 5);
    }

    /**
     * display a rounded corners image with given string uri into imageView,
     * this method will set the corner with 5 dp.
     *
     * @param view the imageView to display the image.
     * @param uri  the string uri. can be a url or "file://" + File.
     */
    public static void loadRound(ImageView view, String uri) {
        loadRound(view, uri, 5);
    }

    /**
     * display a rounded corners image with given string uri into imageView,
     * allow setting the corner with dp value.
     *
     * @param view     the imageView to display the image.
     * @param uri      the string uri. can be a url or "file://" + File.
     * @param radiusDp the corner dp value to be set.
     */
    public static void loadRound(ImageView view, String uri, float radiusDp) {
        if (view == null) return;
        load(builder(view, uri)
                .transformRound(dpToPx(view.getContext(), radiusDp))
                .create());
    }

    /**
     * display a rounded corners image with given drawable resource into imageView,
     * allow setting the corner with dp value.
     *
     * @param view     the imageView to display the image.
     * @param res      the image resource
     * @param radiusDp the corner dp value to be set.
     */
    public static void loadRound(ImageView view, @DrawableRes int res, float radiusDp) {
        if (view == null) return;
        load(builder(view, res)
                .transformRound(dpToPx(view.getContext(), radiusDp))
                .create());
    }

    /**
     * display a square image with given string uri into imageView
     *
     * @param view the imageView to display the image.
     * @param uri  the string uri. can be a url or "file://" + File.
     */
    public static void loadSquare(ImageView view, String uri) {
        load(builder(view, uri)
                .transformSquare()
                .create());
    }

    // ——————————————————————————————————————  builders  ———————————————————————————————————————————

    /**
     * create a new builder with string uri
     *
     * @param view the view to create builder
     * @param uri  the string uri to create builder
     * @return a new builder created
     */
    private static GliderOption.Builder builder(ImageView view, String uri) {
        return builderDefault(new Builder(view, uri));
    }

    /**
     * create a new builder with string uri
     *
     * @param view the view to create builder
     * @param res  the drawable resource to create builder
     * @return a new builder created
     */
    private static Builder builder(ImageView view, @DrawableRes int res) {
        return builderDefault(new Builder(view, res));
    }

    /**
     * use default config into a builder
     *
     * @param builder the builder
     * @return the given builder which used default config.
     */
    public static Builder builderDefault(Builder builder) {
        return builder.crossFade(DEFAULT_CROSS_FADE_DURATION)/*.error(DEFAULT_LOADING_DRAWABLE)*/;
    }

    // ——————————————————————————————————————  util  ———————————————————————————————————————————————

    /**
     * trans dp value to px value.
     */
    private static int dpToPx(Context context, float dp) {
        if (context == null) {
            return 0;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
