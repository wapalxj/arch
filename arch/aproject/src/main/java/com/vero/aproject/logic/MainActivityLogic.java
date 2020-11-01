package com.vero.aproject.logic;


import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentManager;

import com.vero.aproject.R;
import com.vero.aproject.databinding.ActivityMainBinding;
import com.vero.aproject.fragment.CategoryFragment;
import com.vero.aproject.fragment.FavoriteFragment;
import com.vero.aproject.fragment.HomePageFragment;
import com.vero.aproject.fragment.ProfileFragment;
import com.vero.aproject.fragment.RecommendFragment;
import com.vero.common.ui.component.HiBaseFragment;
import com.vero.common.ui.tab.HiFragmentTabView;
import com.vero.common.ui.tab.HiTabViewAdapter;
import com.vero.hiui.tab.bottom.HiTabBottomInfo;
import com.vero.hiui.tab.bottom.HiTabBottomLayout;
import com.vero.hiui.tab.common.IHiTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 将MainActivity的一些逻辑内聚在这里
 * 让MainActivity更加清爽
 */
public class MainActivityLogic {
    private ActivityMainBinding mBinding;
    private HiFragmentTabView fragmentTabView;
    private HiTabBottomLayout hiTabBottomLayout;
    private List<HiTabBottomInfo<?>> infoList;
    private ActivityProvider activityProvider;

    private final static String SAVE_CURRENT_ID = "SAVE_CURRENT_ID";
    private int currentItemIndex;//当前选中的fragment

    public MainActivityLogic(ActivityProvider activityProvider,Bundle savedInstanceState, ActivityMainBinding activityMainBinding) {
        mBinding = activityMainBinding;
        this.activityProvider = activityProvider;
        if (savedInstanceState!= null) {
            currentItemIndex=savedInstanceState.getInt(SAVE_CURRENT_ID);
        }
        initTabBottom();
    }

    private void initTabBottom() {
        mBinding.hiTabBottomLayout.setTabAlpha(0.85f);
        infoList = new ArrayList<>();
        int defaultColor = activityProvider.getResources().getColor(R.color.tabBottomDefaultColor);
        int tintColor = activityProvider.getResources().getColor(R.color.tabBottomTintColor);

        infoList = new ArrayList<>();

        HiTabBottomInfo<Integer> homeInfo = new HiTabBottomInfo<>(
                "首页",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_home),
                null,
                defaultColor,
                tintColor
        );
        HiTabBottomInfo<Integer> favoriteInfo = new HiTabBottomInfo<>(
                "收藏",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_favorite),
                null,
                defaultColor,
                tintColor
        );
        HiTabBottomInfo<Integer> categoryInfo = new HiTabBottomInfo<>(
                "分类",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_category),
                null,
                defaultColor,
                tintColor
        );

        HiTabBottomInfo<Integer> recommendInfo = new HiTabBottomInfo<>(
                "推荐",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_recommend),
                null,
                defaultColor,
                tintColor
        );
        HiTabBottomInfo<Integer> profileInfo = new HiTabBottomInfo<>(
                "我的",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_profile),
                null,
                defaultColor,
                tintColor
        );

        homeInfo.fragment = HomePageFragment.class;
        favoriteInfo.fragment = FavoriteFragment.class;
        categoryInfo.fragment = CategoryFragment.class;
        recommendInfo.fragment = RecommendFragment.class;
        profileInfo.fragment = ProfileFragment.class;

        infoList.add(homeInfo);
        infoList.add(favoriteInfo);
        infoList.add(categoryInfo);
        infoList.add(recommendInfo);
        infoList.add(profileInfo);
        mBinding.hiTabBottomLayout.inflateInfo(infoList);
        initFragmentTabView();
        mBinding.hiTabBottomLayout.addTabSelectedChangeListener(new IHiTabLayout.OnTabSelectedListener<HiTabBottomInfo<?>>() {
            @Override
            public void onTabSelectedChange(int index, @Nullable HiTabBottomInfo<?> preInfo, @NonNull HiTabBottomInfo<?> nextInfo) {
                mBinding.fragmentTabView.setCurrentItem(index);
                currentItemIndex=index;
            }
        });
        mBinding.hiTabBottomLayout.defaultSelected(infoList.get(currentItemIndex));
    }

    private void initFragmentTabView() {
        HiTabViewAdapter tabViewAdapter = new HiTabViewAdapter(activityProvider.getSupportFragmentManager(), infoList);
        mBinding.fragmentTabView.setAdapter(tabViewAdapter);
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(SAVE_CURRENT_ID, currentItemIndex);
    }

    /**
     * 这个接口的API其实都是Activity本身已经实现的
     */
    public interface ActivityProvider {
        <T extends View> T findViewById(@IdRes int id);

        Resources getResources();

        FragmentManager getSupportFragmentManager();

        String getString(@StringRes int resId);

    }
}
