package com.lwj.widget.bannersolution;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lwj.widget.bannerview.BannerInterpolator;
import com.lwj.widget.bannerview.BannerPictureLoader;
import com.lwj.widget.bannerview.BannerView;
import com.lwj.widget.bannerview.OnBannerPageSelectedListener;
import com.lwj.widget.bannerview.OnBannerPictureClickListener;
import com.lwj.widget.viewpagerindicator.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTv_hint;
    private BannerView mBannerView;
    private ViewPagerIndicator mViewPagerIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //一组图片url
        final ArrayList<String> mUrlList = new ArrayList<>();
        mUrlList.add("http://t2.hddhhn.com/uploads/tu/201301/149/2.jpg");
        mUrlList.add("http://t2.hddhhn.com/uploads/tu/201301/149/4.jpg");
        mUrlList.add("http://img1.imgtn.bdimg.com/it/u=963551012,3660149984&fm=214&gp=0.jpg");
        mUrlList.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=6959877,824205428&fm=27&gp=0.jpg");
        mUrlList.add("http://t2.hddhhn.com/uploads/tu/201409/036/1.jpg");

        setupBannerView(mUrlList);

        setupIndicator();


    }


    private void setupBannerView(ArrayList<String> mUrlList) {

        mTv_hint = (TextView) findViewById(R.id.tv_hint);

        mBannerView = (BannerView) findViewById(R.id.BannerView);
        mBannerView.setFragmentManager(getSupportFragmentManager());
        mBannerView.setPictureUrl(mUrlList);
        //无限循环
        mBannerView.setCircle(true);
        mBannerView.setDurationFavor(4.0f);
        mBannerView.setInitItem(0);
        mBannerView.setInterpolatorType(BannerInterpolator.ACCELERATE_DECELERATE);
        //图片加载方式,这里采用Glide
        mBannerView.setPictureLoader(new BannerPictureLoader() {
            @Override
            public void showPicture(Fragment fragment, ImageView pictureView, String pictureUrl) {
                Glide.with(fragment)
                        .load(pictureUrl)
                        .into(pictureView);
            }
        });
        //点击事件
        mBannerView.setPictureClickListener(new OnBannerPictureClickListener() {
            @Override
            public void onPictureClick(Fragment fragment, int position, List<String> pictureUrl) {
                Toast.makeText(MainActivity.this, "position:" + position + "pictureUrl" + pictureUrl.get(position),Toast.LENGTH_SHORT).show();
            }
        });
        mBannerView.setOnBannerPageSelectedListener(new OnBannerPageSelectedListener() {
            @Override
            public void onPageSelected(int position, String url) {
                mTv_hint.setText("position"+position+"\n"+url);
            }
        });
        //配置完成后,调用
        mBannerView.start();
    }


    private void setupIndicator() {

        mViewPagerIndicator = (ViewPagerIndicator) findViewById(R.id.indicator);

        //真无限循环BannerView,配合BannerView,增加以下setViewPager
        //https://github.com/LinweiJ/BannerView
        // if mBannerView.setCircle(true);无限循环
        mViewPagerIndicator.setViewPager(mBannerView.getViewPager(),true);

    }


    @Override
    protected void onStart() {
        super.onStart();
        //开始自动滚动
        mBannerView.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //停止自动滚动
        mBannerView.stopAutoPlay();
    }
}
