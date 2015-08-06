package com.cmbl.chemabuluo_kjf.activity;

import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cmbl.chemabuluo_kjf.R;
import com.cmbl.chemabuluo_kjf.base.BaseActivity;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.KJActivityStack;
import org.kymjs.kjframe.ui.ViewInject;

/**
 * Created by Administrator on 2015/8/6 0006.
 */
public class HomePageActivity extends BaseActivity implements View.OnFocusChangeListener {
    @BindView(id = R.id.bottom_first, click = true)
    private TextView bottomFirst;
    @BindView(id = R.id.bottom_second, click = true)
    private TextView bottomSecond;
    @BindView(id = R.id.bottom_third, click = true)
    private TextView bottomThrid;
    @BindView(id = R.id.layout_bodyer)
    private RelativeLayout layoutBodyer;

    private View oldClickView;
    /***
     * 第二次按退出的时间
     */
    private long time = 0;

    @Override
    public void setRootView() {
        super.setRootView();
        setMyContextView(R.layout.activity_homepage);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        initHeader();
        initBodyer();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (!isOldView(v)) {
            setHeaderTitle(((TextView) v).getText().toString());
        }
        oldClickView = v;
    }

    private boolean isOldView(View view) {
        if (oldClickView != null && view.getId() == oldClickView.getId())
            return true;
        return false;
    }

    @Override
    protected void initHeader() {
        setLeftRelHide(true);
    }

    @Override
    protected void initBodyer() {
        bottomFirst.setOnFocusChangeListener(this);
        bottomSecond.setOnFocusChangeListener(this);
        bottomThrid.setOnFocusChangeListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus)
            v.performClick();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            int LENG_EXIT = 1500;
            if (System.currentTimeMillis() - time > LENG_EXIT) {
                Toast.makeText(this, getResources().getString(R.string.toast_double_exit),
                        Toast.LENGTH_LONG).show();
                time = System.currentTimeMillis();
            } else {
                KJActivityStack.create().AppExit(this);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
