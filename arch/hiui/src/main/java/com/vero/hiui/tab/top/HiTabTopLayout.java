package com.vero.hiui.tab.top;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.vero.hilibrary.util.HiDisplayUtil;
import com.vero.hiui.tab.common.IHiTabLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HiTabTopLayout extends HorizontalScrollView implements IHiTabLayout<HiTabTop, HiTabTopInfo<?>> {
    private List<OnTabSelectedListener<HiTabTopInfo<?>>> tabSelectedListeners = new ArrayList<>();
    private HiTabTopInfo<?> selectedInfo;
    private List<HiTabTopInfo<?>> infoList;


    public HiTabTopLayout(Context context) {
        this(context, null);
    }

    public HiTabTopLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HiTabTopLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public HiTabTop findTab(@NonNull HiTabTopInfo<?> data) {
        //通过model找到对应的tab这个view
        ViewGroup ll = getRootLayout(false);

        for (int i = 0; i < ll.getChildCount(); i++) {
            View child = ll.getChildAt(i);
            if (child instanceof HiTabTop) {
                HiTabTop tab = (HiTabTop) child;
                if (tab.getHiTabInfo() == data) {
                    return tab;
                }
            }
        }
        return null;
    }

    @Override
    public void addTabSelectedChangeListener(OnTabSelectedListener<HiTabTopInfo<?>> listener) {
        tabSelectedListeners.add(listener);
    }

    @Override
    public void defaultSelected(@NonNull HiTabTopInfo<?> defaultInfo) {
        onSelected(defaultInfo);
    }

    @Override
    public void inflateInfo(@NonNull List<HiTabTopInfo<?>> infoList) {
        if (infoList.isEmpty()) {
            return;
        }

        this.infoList = infoList;
        LinearLayout root = getRootLayout(true);
        selectedInfo = null;
        //清除之前添加的HiTabTop类型的listener
        Iterator iterator = tabSelectedListeners.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() instanceof HiTabTop) {
                iterator.remove();
            }
        }
        //添加tab list
        for (int i = 0; i < infoList.size(); i++) {
            final HiTabTopInfo<?> info = infoList.get(i);
            HiTabTop tab = new HiTabTop(getContext());
            tabSelectedListeners.add(tab);
            tab.setHiTabInfo(info);
            root.addView(tab);
            tab.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelected(info);
                }
            });
        }


    }

    private void onSelected(@NonNull HiTabTopInfo<?> nextInfo) {
        for (OnTabSelectedListener<HiTabTopInfo<?>> listener : tabSelectedListeners) {
            listener.onTabSelectedChange(infoList.indexOf(nextInfo), selectedInfo, nextInfo);
        }
        this.selectedInfo = nextInfo;

        //自动滚动
        autoScroll(nextInfo);
    }

    int tabWidth;

    private void autoScroll(HiTabTopInfo<?> nextInfo) {
        HiTabTop tab = findTab(nextInfo);
        if (tab == null) {
            return;
        }
        int index = infoList.indexOf(nextInfo);
        int[] loc = new int[2];
        //获取点击的控件在屏幕的位置
        tab.getLocationInWindow(loc);
        int scrollWidth;
        if (tabWidth == 0) {
            tabWidth = tab.getWidth();
        }
        //判断点击了屏幕的左侧还是右侧
        if ((loc[0] + tabWidth / 2) > HiDisplayUtil.getDisplayWidthInPx(getContext()) / 2) {
            //右侧
            scrollWidth = rangeScrollWidth(index, 2);
        } else {
            //左侧
            scrollWidth = rangeScrollWidth(index, -2);
        }
        smoothScrollTo(getScrollX() + scrollWidth, 0);
    }

    /**
     * 获取可滚动的范围
     *
     * @param index 从第几个开始
     * @param range 向前，向后的范围
     * @return
     */
    private int rangeScrollWidth(int index, int range) {
        int scrollWidth = 0;
        for (int i = 0; i <= Math.abs(range); i++) {
            int next;
            if (range < 0) {
                ///点击左侧，左滑
                next = range + i + index;
            } else {
                //右滑
                next = range - i + index;
            }
            if (next >= 0 && next < infoList.size()) {
                //再遍历范围内的每个tab的滑动距离，加起来就是总距离
                if (range < 0) {
                    //点击左侧，左滑
                    scrollWidth -= scrollWidth(next, false);
                } else {
                    scrollWidth += scrollWidth(next, true);
                }
            }
        }
        return scrollWidth;
    }

    /**
     * 单个指定位置的控件要完全展示的滚动距离
     *
     * @param index   指定位置的控件
     * @param toRight 是否点击了右侧
     * @return
     */
    private int scrollWidth(int index, boolean toRight) {
        HiTabTop target = findTab(infoList.get(index));
        if (target == null) {
            return 0;
        }
        Rect rect = new Rect();
        target.getLocalVisibleRect(rect);
        if (toRight) {
            if (rect.right > tabWidth) {
                //right坐标大于控件宽度时，说明完全没有展示
                return tabWidth;

            } else {
                //显示部分，减去已显示的宽度
                return tabWidth - rect.right;
            }
        } else {
            if (rect.left <= -tabWidth) {
                //left坐标小于等于-控件的宽度，说明完全没有展示
                return tabWidth;
            } else if (rect.left > 0) {
                return rect.left;
            }
            return 0;
        }
    }

    private LinearLayout getRootLayout(boolean clearAll) {
        LinearLayout rootView = (LinearLayout) getChildAt(0);
        if (rootView == null) {
            rootView = new LinearLayout(getContext());
            rootView.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            addView(rootView, params);
        } else if (clearAll) {
            rootView.removeAllViews();
        }
        return rootView;
    }


}
