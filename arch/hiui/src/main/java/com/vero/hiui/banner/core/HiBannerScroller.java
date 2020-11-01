package com.vero.hiui.banner.core;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * 可改变速度的scroller
 */
public class HiBannerScroller extends Scroller {

    /**
     * 值越大，滚动越慢
     */
    private int mDuration = 1000;

    public HiBannerScroller(Context context, int duration) {
        super(context);
        this.mDuration=duration;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }
}
