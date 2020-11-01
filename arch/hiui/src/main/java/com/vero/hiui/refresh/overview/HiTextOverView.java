package com.vero.hiui.refresh.overview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vero.hiui.R;
import com.vero.hiui.refresh.HiOverView;

public class HiTextOverView extends HiOverView {
    private TextView mText;
    private View mRotateView;

    public HiTextOverView(@NonNull Context context) {
        this(context,null);
    }

    public HiTextOverView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HiTextOverView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.hi__refresh_overview_text, this, true);
        mText = findViewById(R.id.text);
        mRotateView = findViewById(R.id.iv_rotate);
    }

    @Override
    protected void onScroll(int scrollY, int pullRefreshHeight) {

    }

    @Override
    protected void onVisible() {
        mText.setText("Pull refresh");
    }

    @Override
    protected void onOver() {
        mText.setText("Release refresh");
    }

    @Override
    protected void onRefresh() {
        mText.setText("Refreshing...");
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_anim);
        LinearInterpolator lin = new LinearInterpolator();
        animation.setInterpolator(lin);
        mRotateView.startAnimation(animation);
    }

    @Override
    protected void onFinish() {
        mRotateView.clearAnimation();
    }
}
