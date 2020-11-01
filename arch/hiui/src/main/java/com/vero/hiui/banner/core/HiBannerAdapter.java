package com.vero.hiui.banner.core;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class HiBannerAdapter extends PagerAdapter implements IBindAdapter {
    private Context mContext;
    //缓存
    private SparseArray<HiBannerViewHolder> mCachedViews = new SparseArray<>();
    private IHiBanner.OnBannerClickListener mBannerClickListener;
    private IBindAdapter mBindAdapter;//业务bind
    private List<? extends HiBannerMo> models;

    /**
     * 是否开启自动轮播
     */
    private boolean mAutoPlay;

    /**
     * 非自动轮播状态下是否可以循环切换
     */
    private boolean mLoop = false;

    private int mLayoutResId = -1;

    public HiBannerAdapter(Context context) {
        mContext = context;
    }

    public void setBindAdapter(IBindAdapter mBindAdapter) {
        this.mBindAdapter = mBindAdapter;
    }

    public void setAutoPlay(boolean mAutoPlay) {
        this.mAutoPlay = mAutoPlay;
    }

    public void setLoop(boolean mLoop) {
        this.mLoop = mLoop;
    }

    public void setLayoutResId(int mLayoutResId) {
        this.mLayoutResId = mLayoutResId;
    }

    public void setBannerData(@NonNull List<? extends HiBannerMo> models) {
        this.models = models;
        //初始化数据
        initCachedView();
        notifyDataSetChanged();
    }

    public void setOnBannerClickListener(IHiBanner.OnBannerClickListener mBannerClickListener) {
        this.mBannerClickListener = mBannerClickListener;
    }


    @Override
    public int getCount() {
        //无限轮播
        return mAutoPlay ? Integer.MAX_VALUE : (mLoop ? Integer.MAX_VALUE : getRealCount());
    }

    public int getRealCount() {
        return models == null ? 0 : models.size();
    }

    /**
     * 获取初次展示的item位置
     */
    public int getFirstItem() {
        return Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2) % getRealCount();
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        //让Item每次都刷新
        return POSITION_NONE;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int realPosition = position;
        if (getRealCount() > 0) {
            realPosition = position % getRealCount();
        }
        //取缓存
        HiBannerViewHolder viewHolder = mCachedViews.get(realPosition);
        if (container.equals(viewHolder.rootView.getParent())) {
            //已经添加，则移除
            container.removeView(viewHolder.rootView);
        }

        if (viewHolder.rootView.getParent() != null) {
            //异常处理，如果有parent,则先移除
            ((ViewGroup) viewHolder.rootView.getParent()).removeView(viewHolder.rootView);
        }
        container.addView(viewHolder.rootView);

        //数据绑定
        onBind(viewHolder, models.get(realPosition), realPosition);
        return viewHolder.rootView;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {


    }

    @Override
    public void onBind(final HiBannerViewHolder viewHolder, final HiBannerMo bannerMo, final int position) {
        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBannerClickListener != null) {
                    mBannerClickListener.onBannerClick(viewHolder, bannerMo, position);
                }
            }
        });
        if (mBindAdapter != null) {
            mBindAdapter.onBind(viewHolder, bannerMo, position);
        }
    }


    public void initCachedView() {
        mCachedViews = new SparseArray<>();
        for (int i = 0; i < models.size(); i++) {
            HiBannerViewHolder viewHolder = new HiBannerViewHolder(createView(LayoutInflater.from(mContext), null));
            mCachedViews.put(i, viewHolder);
        }

    }

    private View createView(LayoutInflater layoutInflater, ViewGroup parent) {
        if (mLayoutResId == -1) {
            throw new IllegalArgumentException("you must be set layoutId first");
        }
        return layoutInflater.inflate(mLayoutResId, parent, false);
    }


    public static class HiBannerViewHolder {
        private SparseArray<View> viewSparseArray;
        View rootView;

        public HiBannerViewHolder(View rootView) {
            this.rootView = rootView;
        }

        public View getRootView() {
            return rootView;
        }

        public <T extends View> T findViewById(@IdRes int id) {
            if (!(rootView instanceof ViewGroup)) {
                return (T) rootView;
            }
            if (this.viewSparseArray == null) {
                this.viewSparseArray = new SparseArray<>(1);
            }
            T childView = (T) viewSparseArray.get(id);
            if (childView == null) {
                childView = rootView.findViewById(id);
                this.viewSparseArray.put(id, childView);
            }

            return childView;
        }

    }
}
