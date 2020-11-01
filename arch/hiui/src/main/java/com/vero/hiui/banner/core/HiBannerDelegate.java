package com.vero.hiui.banner.core;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;

import com.vero.hiui.R;
import com.vero.hiui.banner.HiBanner;
import com.vero.hiui.banner.indicator.HiCircleIndicator_java;
import com.vero.hiui.banner.indicator.IHiIndicator;

import java.util.List;

/**
 * HiBanner的控制器
 * 辅助HiBanner完成各种功能的控制
 * 将HiBanner 的一些逻辑内聚在这里,保证暴露给使用者的HiBanner干净整洁
 */
public class HiBannerDelegate implements IHiBanner, OnPageChangeListener {
    private Context mContext;
    private HiBanner mHiBanner;
    private HiBannerAdapter mAdapter;
    private IHiIndicator<?> mIHiIndicator;
    private boolean mAutoPlay;
    private boolean mLoop;
    private List<? extends HiBannerMo> mHiBannerMos;
    private OnPageChangeListener mOnPageChangeListener;
    private int mIntervalTime = 5000;//滚动间隔时间
    private OnBannerClickListener mOnBannerClickListener;
    private HiViewPager mHiViewPager;
    private int mDuration = -1;//每一页的滚动时间

    public HiBannerDelegate(Context context, HiBanner hiBanner) {
        mContext = context;
        mHiBanner = hiBanner;
    }

    @Override
    public void setBannerData(int layoutReId, @NonNull List<? extends HiBannerMo> models) {
        //指定Item时
        mHiBannerMos = models;
        init(layoutReId);
    }

    private void init(int layoutReId) {
        if (mAdapter == null) {
            mAdapter = new HiBannerAdapter(mContext);
        }
        if (mIHiIndicator == null) {
            mIHiIndicator = new HiCircleIndicator_java(mContext);
        }
        mIHiIndicator.onInflate(mHiBannerMos.size());
        mAdapter.setLayoutResId(layoutReId);
        mAdapter.setBannerData(mHiBannerMos);
        mAdapter.setAutoPlay(mAutoPlay);
        mAdapter.setLoop(mLoop);
        mAdapter.setOnBannerClickListener(mOnBannerClickListener);


        mHiViewPager = new HiViewPager(mContext);
        mHiViewPager.setIntervalTime(mIntervalTime);
        mHiViewPager.addOnPageChangeListener(this);
        mHiViewPager.setAdapter(mAdapter);

        if (mHiViewPager != null && mDuration > 0) {
            mHiViewPager.setScrollerDuration(mDuration);
        }

        if ((mLoop || mAutoPlay) && mAdapter.getRealCount() != 1) {
            //无限轮播关键点：是第一张能反向滑动到最后一张
            int firstItem = mAdapter.getFirstItem();
            mHiViewPager.setCurrentItem(firstItem, false);
        }

        //清除所有的view
        mHiBanner.removeAllViews();

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mHiBanner.addView(mHiViewPager, params);
        mHiBanner.addView(mIHiIndicator.get(), params);


    }

    @Override
    public void setBannerData(@NonNull List<? extends HiBannerMo> models) {
        //不指定Item时,使用默认的layout
        setBannerData(R.layout.hi_banner_item_image, models);
    }

    @Override
    public void setHiIndicator(IHiIndicator<?> hiIndicator) {
        this.mIHiIndicator = hiIndicator;
    }

    @Override
    public void setAutoPlay(boolean autoPlay) {
        this.mAutoPlay = autoPlay;
        if (mAdapter != null) {
            mAdapter.setAutoPlay(mAutoPlay);
        }
        if (mHiViewPager != null) {
            mHiViewPager.setAutoPlay(mAutoPlay);
        }
    }

    @Override
    public void setLoop(boolean loop) {
        this.mLoop = loop;
    }

    @Override
    public void setIntervalTime(int intervalTime) {
        if (intervalTime > 0) {
            this.mIntervalTime = intervalTime;
        }
    }

    @Override
    public void setBindAdapter(IBindAdapter bindAdapter) {
        mAdapter.setBindAdapter(bindAdapter);
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.mOnPageChangeListener = onPageChangeListener;
    }

    @Override
    public void setOnBannerClickListener(OnBannerClickListener onBannerClickListener) {
        this.mOnBannerClickListener = onBannerClickListener;
    }

    @Override
    public void setScrollerDuration(int duration) {
        this.mDuration = duration;
        if (mHiViewPager != null && mDuration > 0) {
            mHiViewPager.setScrollerDuration(mDuration);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mOnPageChangeListener != null && mAdapter.getRealCount() != 0) {
            mOnPageChangeListener.onPageScrolled(position % mAdapter.getRealCount(), positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (mAdapter.getRealCount() == 0) {
            return;
        }
        position = position % mAdapter.getRealCount();
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageSelected(position);
        }

        if (mIHiIndicator != null) {
            mIHiIndicator.onPointChange(position, mAdapter.getRealCount());
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrollStateChanged(state);
        }
    }
}
