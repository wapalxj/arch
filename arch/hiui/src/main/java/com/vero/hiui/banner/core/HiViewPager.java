package com.vero.hiui.banner.core;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;
import java.util.logging.LogRecord;

/**
 * 实现自动翻页的Viewpager
 */
public class HiViewPager extends ViewPager {
    private int mIntervalTime;

    //是否开启自动轮播
    private boolean mAutoPlay = true;

    //是否布局过
    private boolean isLayout;

    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            //切换到下一个
            next();
            mHandler.postDelayed(this, mIntervalTime);
        }
    };

    public HiViewPager(@NonNull Context context) {
        super(context);
    }

    public HiViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAutoPlay(boolean autoPlay) {
        this.mAutoPlay = autoPlay;
        if (!mAutoPlay) {
            mHandler.removeCallbacks(mRunnable);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                start();
                break;
            default:
                //触摸时暂停自动播放
                stop();
                break;

        }
        return super.onTouchEvent(ev);
    }

    private void start() {
        mHandler.removeCallbacksAndMessages(null);
        if (mAutoPlay) {
            mHandler.postDelayed(mRunnable, mIntervalTime);
        }
    }

    public void stop() {
        mHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 设置下一个要显示的item,并返回item的pos
     */
    private int next() {
        int nextPosition = -1;
        if (getAdapter() == null || getAdapter().getCount() <= 1) {
            stop();
            return nextPosition;
        }
        nextPosition = getCurrentItem() + 1;
        //下一个索引大于adapter的view的最大数量时重新开始
        if (nextPosition >= getAdapter().getCount()) {
            //获取第一个item的索引
            nextPosition = ((HiBannerAdapter) getAdapter()).getFirstItem();
        }
        setCurrentItem(nextPosition, true);
        return nextPosition;
    }

    public int getIntervalTime() {
        return mIntervalTime;
    }

    public void setIntervalTime(int mIntervalTime) {
        this.mIntervalTime = mIntervalTime;
    }


    /**
     * 设置Viewpager的滚动速度
     *
     * @param duration
     */

    public void setScrollerDuration(int duration) {
        try {
            Field scrollerFiled = ViewPager.class.getDeclaredField("mScroller");
            scrollerFiled.setAccessible(true);
            scrollerFiled.set(this,new HiBannerScroller(getContext(),duration));
        } catch (Exception e) {

        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        isLayout = true;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isLayout && getAdapter() != null && getAdapter().getCount() > 0) {
            //已经layout过
            //fix RecyclerView的回收导致ViewPager切换到一半的问题
            try {
                Field mScroller = ViewPager.class.getDeclaredField("mFirstLayout");
                mScroller.setAccessible(true);
                mScroller.set(this, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        start();
    }

    @Override
    protected void onDetachedFromWindow() {
        //fix RecyclerView的回收导致ViewPager切换到一半的问题
        if (((Activity) getContext()).isFinishing()) {
            super.onDetachedFromWindow();
        } else {
            stop();
        }
    }
}
