package com.vero.hiui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vero.hilibrary.util.HiDisplayUtil;

/**
 * 下拉刷新Overlay
 */
public abstract class HiOverView extends FrameLayout {

    public enum HiRefreshState {
        /**
         * 初始态
         */
        STATE_INIT,
        /**
         * Header展示状态
         */
        STATE_VISIBLE,
        /**
         * 刷新状态
         */
        STATE_REFRESH,
        /**
         * 超出可刷新距离状态
         */
        STATE_OVER,
        /**
         * 超出可刷新距离，松手后的状态
         */
        STATE_OVER_RELEASE,

    }

    protected HiRefreshState mState = HiRefreshState.STATE_INIT;

    /**
     * 阻尼：用户下拉的距离和实际滚动的系数
     * 最小阻尼
     */
    public float minDamp = 1.6f;

    /**
     * 最大阻尼
     */
    public float maxDamp = 2.2f;

    /**
     * 出发下拉刷新需要的最小高度
     */
    public int mPullRefreshHeight;


    public HiOverView(@NonNull Context context) {
        this(context,null);
    }

    public HiOverView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HiOverView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        preInit();
    }

    protected void preInit(){
        mPullRefreshHeight= HiDisplayUtil.dp2px(88,getResources());
        init();
    }
    /**
     * 初始化
     */
    public abstract void init();

    protected abstract void onScroll(int scrollY, int pullRefreshHeight);

    /**
     * 显示Overlay
     */
    protected abstract void onVisible();

    /**
     * 超过Overlay,释放加载
     */
    protected abstract void onOver();

    /**
     * 开始刷新
     */
    protected abstract void onRefresh();

    /**
     * 加载完成
     */
    protected abstract void onFinish();

    /**
     * state
     */
    protected void setState(HiRefreshState state) {
        this.mState = state;
    }

    public HiRefreshState getState() {
        return mState;
    }
}
