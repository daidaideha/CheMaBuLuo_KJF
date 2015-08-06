package com.cmbl.chemabuluo_kjf;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cmbl.chemabuluo_kjf.base.BaseActivity;
import com.cmbl.chemabuluo_kjf.service.MyService;
import com.cmbl.chemabuluo_kjf.widget.share.DialogShare;
import com.cmbl.chemabuluo_kjf.widget.share.ShareUnit;
import com.cmbl.chemabuluo_kjf.widget.ViewFactory;
import com.witalk.widget.PullToRefreshView;
import com.witalk.widget.cycleviewpager.ADInfo;
import com.witalk.widget.cycleviewpager.CycleViewPager;

import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.ui.BindView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements View.OnFocusChangeListener {
    @BindView(id = R.id.tv_test, click = true)
    private TextView textView;
    @BindView(id = R.id.image)
    private ImageView imageView;
    @BindView(id = R.id.pulltorefresh)
    private PullToRefreshView mPullToRefreshView;
    @BindView(id = R.id.listview)
    private ListView mListView;
    private CycleViewPager cycleViewPager;
    @BindView(id = R.id.btn, click = true)
    private Button mButton;
    @BindView(id = R.id.btn2, click = true)
    private Button mButton2;
    @BindView(id = R.id.btn3, click = true)
    private Button mButton3;
    @BindView(id = R.id.context)
    private TextView mTextView;

    private String[] imageUrls = {
            "http://d.hiphotos.baidu.com/image/w%3D400/sign=8d6ddf17bb389b5038ffe152b534e5f1/6d81800a19d8bc3e0a89c3c3808ba61ea8d34532.jpg",
            "http://h.hiphotos.baidu.com/image/w%3D400/sign=91e3c526fffaaf5184e380bfbc5594ed/314e251f95cad1c8f87e91277d3e6709c83d51ec.jpg",
            "http://b.hiphotos.baidu.com/image/w%3D400/sign=3cb9e5f779899e51788e3b1472a7d990/f9198618367adab443ae2d4989d4b31c8701e4b9.jpg",
            "http://a.hiphotos.baidu.com/image/w%3D400/sign=b55764e9e51190ef01fb93dffe1a9df7/838ba61ea8d3fd1f7156163e324e251f95ca5f52.jpg",
            "http://d.hiphotos.baidu.com/image/w%3D400/sign=2887480cfadcd100cd9cf921428b47be/d833c895d143ad4bc253a4c180025aafa40f06b5.jpg",
            "http://h.hiphotos.baidu.com/image/w%3D400/sign=16b3bfd575094b36db921aed93cc7c00/5d6034a85edf8db1ec0d3d6e0a23dd54564e749c.jpg"};

    private List<ImageView> views = new ArrayList<ImageView>();
    private List<ADInfo> infos = new ArrayList<ADInfo>();
    private Intent intent;
    DialogShare dialog;

    @Override
    public void setRootView() {
        super.setRootView();
        setMyContextView(R.layout.activity_main);
        dialog = new DialogShare(this, R.style.dialog, new ShareUnit());
        dialog.windowDeploy();
        cycleViewPager = (CycleViewPager) getFragmentManager()
                .findFragmentById(R.id.cycleviewapger);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        initHeader();
        initBodyer();
        intent = new Intent(this, MyService.class);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
//        ViewInject.toast("click " + v.getId());
//        startService(intent);
        dialog.show();
//        v.requestFocus();
        mTextView.setText(((TextView)v).getText().toString() + v.getId());
    }


    @Override
    protected void initHeader() {
        setHeaderTitle("Test");
    }

    @Override
    protected void initBodyer() {
        KJBitmap kjb = new KJBitmap();
//        List<String> list = new ArrayList<>();
//        list.add("http://d.hiphotos.baidu.com/image/w%3D400/sign=8d6ddf17bb389b5038ffe152b534e5f1/6d81800a19d8bc3e0a89c3c3808ba61ea8d34532.jpg");
//        list.add("http://h.hiphotos.baidu.com/image/w%3D400/sign=91e3c526fffaaf5184e380bfbc5594ed/314e251f95cad1c8f87e91277d3e6709c83d51ec.jpg");
//        list.add("http://b.hiphotos.baidu.com/image/w%3D400/sign=3cb9e5f779899e51788e3b1472a7d990/f9198618367adab443ae2d4989d4b31c8701e4b9.jpg");
//        list.add("http://a.hiphotos.baidu.com/image/w%3D400/sign=b55764e9e51190ef01fb93dffe1a9df7/838ba61ea8d3fd1f7156163e324e251f95ca5f52.jpg");
//        list.add("http://d.hiphotos.baidu.com/image/w%3D400/sign=2887480cfadcd100cd9cf921428b47be/d833c895d143ad4bc253a4c180025aafa40f06b5.jpg");
//        list.add("http://h.hiphotos.baidu.com/image/w%3D400/sign=16b3bfd575094b36db921aed93cc7c00/5d6034a85edf8db1ec0d3d6e0a23dd54564e749c.jpg");
//        list.add("http://d.hiphotos.baidu.com/image/w%3D400/sign=8d6ddf17bb389b5038ffe152b534e5f1/6d81800a19d8bc3e0a89c3c3808ba61ea8d34532.jpg");
//        list.add("http://h.hiphotos.baidu.com/image/w%3D400/sign=91e3c526fffaaf5184e380bfbc5594ed/314e251f95cad1c8f87e91277d3e6709c83d51ec.jpg");
//        list.add("http://b.hiphotos.baidu.com/image/w%3D400/sign=3cb9e5f779899e51788e3b1472a7d990/f9198618367adab443ae2d4989d4b31c8701e4b9.jpg");
//        list.add("http://a.hiphotos.baidu.com/image/w%3D400/sign=b55764e9e51190ef01fb93dffe1a9df7/838ba61ea8d3fd1f7156163e324e251f95ca5f52.jpg");
//        list.add("http://d.hiphotos.baidu.com/image/w%3D400/sign=2887480cfadcd100cd9cf921428b47be/d833c895d143ad4bc253a4c180025aafa40f06b5.jpg");
//        list.add("http://h.hiphotos.baidu.com/image/w%3D400/sign=16b3bfd575094b36db921aed93cc7c00/5d6034a85edf8db1ec0d3d6e0a23dd54564e749c.jpg");
//        ImageAdatper adatper = new ImageAdatper(mListView, list, kjb);
//        mListView.setAdapter(adatper);
//        mPullToRefreshView.setEnablePullTorefresh(false);
//        mPullToRefreshView.setEnablePullLoadMoreDataStatus(false);

//        mButton.performClick();
        mButton.setOnFocusChangeListener(this);
        mButton2.setOnFocusChangeListener(this);
        mButton3.setOnFocusChangeListener(this);

        for(int i = 0; i < imageUrls.length; i ++){
            ADInfo info = new ADInfo();
            info.setUrl(imageUrls[i]);
            info.setContent("图片-->" + i );
            infos.add(info);
        }

        // 将最后一个ImageView添加进来
        views.add(ViewFactory.getImageView(this, infos.get(infos.size() - 1).getUrl()));
        for (int i = 0; i < infos.size(); i++) {
            views.add(ViewFactory.getImageView(this, infos.get(i).getUrl()));
        }
        // 将第一个ImageView添加进来
        views.add(ViewFactory.getImageView(this, infos.get(0).getUrl()));

        // 设置循环，在调用setData方法前调用
        cycleViewPager.setCycle(true);

        // 在加载数据前设置是否循环
        cycleViewPager.setData(views, infos, null);
        //设置轮播
        cycleViewPager.setWheel(true);

        // 设置轮播时间，默认5000ms
        cycleViewPager.setTime(5000);
        //设置圆点指示图标组居中显示，默认靠右
        cycleViewPager.setIndicatorCenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(intent);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus)
            v.performClick();
    }
}
