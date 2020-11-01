package com.vero.hiui.banner.indicator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vero.hilibrary.util.HiDisplayUtil;
import com.vero.hiui.R;

public class HiCircleIndicator_java extends FrameLayout implements IHiIndicator<FrameLayout> {
    private static final int VWC = ViewGroup.LayoutParams.WRAP_CONTENT;

    //normal
    int mPointNormal = R.drawable.shape_point_normal;
    //选中
    int mPointSelected = R.drawable.shape_point_select;


    /**
     * 指示点左右内间距
     */
    int mPointLeftRightPadding;

    /**
     * 指示点上下内间距
     */
    int mPointTopBottomPadding;


    public HiCircleIndicator_java(@NonNull Context context) {
        this(context, null);
    }

    public HiCircleIndicator_java(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HiCircleIndicator_java(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPointLeftRightPadding = HiDisplayUtil.dp2px(5, getContext().getResources());
        mPointTopBottomPadding = HiDisplayUtil.dp2px(5, getContext().getResources());
    }

    @Override
    public FrameLayout get() {
        return this;
    }

    @Override
    public void onInflate(int count) {
        removeAllViews();
        if (count <= 0) {
            return;
        }
        LinearLayout groupView = new LinearLayout(getContext());
        groupView.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(VWC, VWC);
        imageViewParams.gravity = Gravity.CENTER_VERTICAL;
        imageViewParams.setMargins(mPointLeftRightPadding, mPointTopBottomPadding, mPointLeftRightPadding, mPointTopBottomPadding);
        ImageView imageView;
        for (int i = 0; i < count; i++) {
            imageView = new ImageView(getContext());
            imageView.setLayoutParams(imageViewParams);
            if (i == 0) {
                imageView.setImageResource(mPointSelected);
            } else {
                imageView.setImageResource(mPointNormal);
            }
            groupView.addView(imageView);
        }
        FrameLayout.LayoutParams groupViewParams = new FrameLayout.LayoutParams(VWC, VWC);
        groupViewParams.gravity = Gravity.CENTER | Gravity.BOTTOM;
        addView(groupView, groupViewParams);
    }

    @Override
    public void onPointChange(int current, int count) {
        ViewGroup viewGroup = (ViewGroup) getChildAt(0);
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            ImageView imageView = (ImageView) viewGroup.getChildAt(i);
            if (i == current) {
                imageView.setImageResource(mPointSelected);
            } else {
                imageView.setImageResource(mPointNormal);
            }
            imageView.requestLayout();
        }
    }
}
