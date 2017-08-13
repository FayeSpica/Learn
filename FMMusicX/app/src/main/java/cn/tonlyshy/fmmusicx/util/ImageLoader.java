package cn.tonlyshy.fmmusicx.util;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by liaowm5 on 17/4/28.
 */

public class ImageLoader {
    public static void load(Context context, Uri uri, ImageView imageView) {
        Glide.with(context).load(uri).asBitmap().into(imageView);
    }
}
