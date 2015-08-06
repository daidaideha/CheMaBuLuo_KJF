package com.cmbl.chemabuluo_kjf.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.cmbl.chemabuluo_kjf.R;

import org.kymjs.kjframe.KJBitmap;

/**
 * Created by Administrator on 2015/8/5 0005.
 */
public class ViewFactory {
    public static ImageView getImageView(Context context, String url) {
        KJBitmap kjb = new KJBitmap();
        ImageView imageView = (ImageView) LayoutInflater.from(context).inflate(
                R.layout.view_banner, null);
        kjb.display(imageView, url);
        return imageView;
    }
}
