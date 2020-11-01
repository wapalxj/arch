package com.vero.hiui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class HiRefreshLayout extends FrameLayout implements HiRefresh {

    private HiOverView.HiRefreshState mState;
    private GestureDetector mGestureDetector;
    private HiRefresh.HiRefreshListener mHiRefreshListener;
    private HiOverView mHiOverView;
    private AutoScroller mAutoScroller;

    private int mLastY;
    //刷新时是否禁止滚动
    private boolean disableRefreshScroll=true;

    public HiRefreshLayout(@NonNull Context context) {
        this(context, null);
    }

    public HiRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HiRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mGestureDetector = new GestureDetector(getContext(), gestureDetector);
        mAutoScroller = new AutoScroller();
    }

    @Override
    public void setDisableRefreshScroll(boolean disableRefreshScroll) {
        this.disableRefreshScroll = disableRefreshScroll;
    }

    @Override
    public void refreshFinished() {
        View head = getChildAt(0);
        mHiOverView.onFinish();
        mHiOverView.setState(HiOverView.HiRefreshState.STATE_INIT);
        int bottom = head.getBottom();
        if (bottom > 0) {
            recover(bottom);
        }
        mState=HiOverView.HiRefreshState.STATE_INIT;

    }

    @Override
    public void setRefreshOverView(HiOverView hiOverView) {
        if (this.mHiOverView != null) {
            removeView(this.mHiOverView);
        }
        this.mHiOverView = hiOverView;
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mHiOverView, 0, params);
    }


    @Override
    public void setRefreshListener(HiRefreshListener hiRefreshListener) {
        this.mHiRefreshListener = hiRefreshListener;
    }

    HiGestureDetector gestureDetector = new HiGestureDetector() {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            super.onScroll(e1,e2,distanceX,distanceY);
            if (Math.abs(distanceX) > Math.abs(distanceY) || (mHiRefreshListener != null && !mHiRefreshListener.enableRefresh())) {
                //横向滑动，或者刷新被禁止

                return false;
            }
            if (disableRefreshScroll && mState == HiOverView.HiRefreshState.STATE_REFRESH) {
                //刷新时是否禁止滚动

                return true;
            }

            View header = getChildAt(0);
            View child = HiScrollUtil.findScrollableChild(HiRefreshLayout.this);
//            View child = getChildAt(1);
            if (HiScrollUtil.childScrolled(child)) {
                //列表发生了滚动则不处理
                return false;
            }
            //没有刷新或者没有达到可刷新的距离，且头部已经划出或下拉
            if ((mState != HiOverView.HiRefreshState.STATE_REFRESH || header.getBottom() <= mHiOverView.mPullRefreshHeight) && (header.getBottom() > 0 || distanceY <= 0)) {
                //还在滑动中
                if (mState != HiOverView.HiRefreshState.STATE_OVER_RELEASE) {
                    int seed;
                    //阻尼计算速度
                    if (child.getTop() < mHiOverView.mPullRefreshHeight) {
                        //没划出刷新高度，用小阻尼
                        seed = (int) (mLastY / mHiOverView.minDamp);
                    } else {
                        //划出刷新高度，用大阻尼
                        seed = (int) (mLastY / mHiOverView.maxDamp);
                    }
                    //如果是正在刷新状态，不允许在刷新的时候改变状态
                    boolean bool = moveDown(seed, true);
                    mLastY = (int) -distanceY;
                    return bool;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    };

    /**
     * 根据偏于量移动header与child
     *
     * @param offsetY 偏移量
     * @param nonAuto 是否非自动滚动出发
     * @return
     */
    private boolean moveDown(int offsetY, boolean nonAuto) {
        View head = getChildAt(0);
        View child = getChildAt(1);
        int childTop = child.getTop() + offsetY;
        if (childTop <= 0) {//异常情况的补充
            offsetY = -child.getTop();
            //移动head与child的位置到原始位置
            head.offsetTopAndBottom(offsetY);
            child.offsetTopAndBottom(offsetY);
            if (mState != HiOverView.HiRefreshState.STATE_REFRESH) {
                mState = HiOverView.HiRefreshState.STATE_INIT;
            }
        } else if (mState == HiOverView.HiRefreshState.STATE_REFRESH && childTop > mHiOverView.mPullRefreshHeight) {
            //正在下拉刷新中，禁止继续下拉

            return false;
        } else if (childTop <= mHiOverView.mPullRefreshHeight) {
            //还没超出设定的刷新距离，继续下拉
            if (mHiOverView.getState() != HiOverView.HiRefreshState.STATE_VISIBLE && nonAuto) {
                //头部开始显示
                mHiOverView.onVisible();
                mHiOverView.setState(HiOverView.HiRefreshState.STATE_VISIBLE);
                mState = HiOverView.HiRefreshState.STATE_VISIBLE;
            }

            head.offsetTopAndBottom(offsetY);
            child.offsetTopAndBottom(offsetY);
            if (childTop == mHiOverView.mPullRefreshHeight && mState == HiOverView.HiRefreshState.STATE_OVER_RELEASE) {
                //松手后的下拉刷新
                refresh();
            }
        } else {

            if (mHiOverView.getState() != HiOverView.HiRefreshState.STATE_OVER && nonAuto) {
                //超出刷新位置
                mHiOverView.onOver();
                mHiOverView.setState(HiOverView.HiRefreshState.STATE_OVER);
                mState = HiOverView.HiRefreshState.STATE_OVER;
            }
            head.offsetTopAndBottom(offsetY);
            child.offsetTopAndBottom(offsetY);
        }
        if (mHiOverView != null) {
            mHiOverView.onScroll(head.getBottom(), mHiOverView.mPullRefreshHeight);
        }

        return true;
    }

    /**
     * 刷新
     */
    private void refresh() {
        if (mHiRefreshListener != null) {
            mState = HiOverView.HiRefreshState.STATE_REFRESH;
            mHiOverView.onRefresh();
            mHiOverView.setState(HiOverView.HiRefreshState.STATE_REFRESH);
            mHiRefreshListener.onRefresh();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!mAutoScroller.isFinished()) {
            return false;
        }
        View header = getChildAt(0);
        if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL || ev.getAction() == MotionEvent.ACTION_POINTER_INDEX_MASK) {
            //松开手
            if (header.getBottom() > 0) {
                if (mState != HiOverView.HiRefreshState.STATE_REFRESH) {
                    //恢复或者开始刷新
                    recover(header.getBottom());
                    return false;
                }
            }
            mLastY = 0;
        }
        boolean consumed = mGestureDetector.onTouchEvent(ev);
        if ((consumed || (mState != HiOverView.HiRefreshState.STATE_INIT && mState != HiOverView.HiRefreshState.STATE_REFRESH)) && header.getBottom() != 0) {
            ev.setAction(MotionEvent.ACTION_CANCEL);//让父类接受不到真实的事件
            return super.dispatchTouchEvent(ev);
        }
        if (consumed) {
            return true;
        } else {
            return super.dispatchTouchEvent(ev);
        }
    }

    private void recover(int dis) {
        if (mHiRefreshListener != null && dis > mHiOverView.mPullRefreshHeight) {
            //下拉超过了刷新设定的距离，松手开始刷新
            //滚动指定距离
            //dis-mHiOverView.mPullRefreshHeight
            mAutoScroller.recover(dis - mHiOverView.mPullRefreshHeight);
            //松开手
            mState = HiOverView.HiRefreshState.STATE_OVER_RELEASE;
        } else {
            //恢复
            mAutoScroller.recover(dis);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        //定义header和child的排列位置
        View header = getChildAt(0);
        View child = getChildAt(1);
        if (header != null && child != null) {
            int childTop = child.getTop();
            if (mState == HiOverView.HiRefreshState.STATE_REFRESH) {
                //正在刷新
                header.layout(0, mHiOverView.mPullRefreshHeight - header.getMeasuredHeight(), right, mHiOverView.mPullRefreshHeight);
                child.layout(0, mHiOverView.mPullRefreshHeight, right, mHiOverView.mPullRefreshHeight + child.getMeasuredHeight());
            } else {
                //header的真实高度
                header.layout(0, childTop - header.getMeasuredHeight(), right, childTop);
                child.layout(0, childTop, right, childTop + child.getMeasuredHeight());
            }

            //其他view的处理
            View other;
            for (int i = 2; i < getChildCount(); i++) {
                other = getChildAt(i);
                other.layout(left, top, right, bottom);
            }
        }
    }

    private class AutoScroller implements Runnable {
        private Scroller mAutoScroller;
        private int mLastY;
        private boolean mIsFinished;

        public AutoScroller() {
            Context context;
            this.mAutoScroller = new Scroller(getContext(), new LinearInterpolator());
            this.mIsFinished = true;
        }

        @Override
        public void run() {
            if (mAutoScroller.computeScrollOffset()) {
//                mLastY-mAutoScroller.getCurrY()
                moveDown(mLastY - mAutoScroller.getCurrY(), false);
                mLastY=mAutoScroller.getCurrY();
                post(this);

            } else {
                removeCallbacks(this);
                mIsFinished = true;
            }
        }

        void recover(int dis) {
            if (dis <= 0) {
                return;
            }
            removeCallbacks(this);
            mLastY = 0;
            mIsFinished = false;
            mAutoScroller.startScroll(0, 0, 0, dis, 300);
            post(this);
        }

        boolean isFinished() {
            return mIsFinished;
        }
    }
}
