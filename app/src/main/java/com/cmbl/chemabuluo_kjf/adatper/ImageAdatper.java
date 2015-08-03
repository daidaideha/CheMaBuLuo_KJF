package com.cmbl.chemabuluo_kjf.adatper;

import android.widget.AbsListView;

import com.cmbl.chemabuluo_kjf.R;

import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.widget.AdapterHolder;
import org.kymjs.kjframe.widget.KJAdapter;

import java.util.Collection;

/**
 * Created by Administrator on 2015/7/31 0031.
 */
public class ImageAdatper extends KJAdapter {
    private KJBitmap kjBitmap;

    public ImageAdatper(AbsListView view, Collection mDatas, KJBitmap kjBitmap) {
        super(view, mDatas, R.layout.adapter_image);
        this.kjBitmap = kjBitmap;
    }

    @Override
    public void convert(AdapterHolder helper, Object item, boolean isScrolling) {
        helper.setImageByUrl(kjBitmap, R.id.image, item.toString());
        helper.setText(R.id.text, item.toString());
    }
}
