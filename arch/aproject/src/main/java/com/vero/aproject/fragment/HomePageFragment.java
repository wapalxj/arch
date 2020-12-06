package com.vero.aproject.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.vero.aproject.R;
import com.vero.aproject.route.RouteFlag;
import com.vero.common.ui.component.HiBaseFragment;

import coroutines.CoroutinesScene;

public class HomePageFragment extends HiBaseFragment {


    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layoutView.findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                navigation("/profile/detail");
                CoroutinesScene.INSTANCE.startScene1();
            }
        });

        layoutView.findViewById(R.id.btn_vip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigation("/profile/vip");

            }
        });
        layoutView.findViewById(R.id.btn_auth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigation("/profile/authentication");

            }
        });
        layoutView.findViewById(R.id.btn_unknow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigation("/profile/xxxxxxxxxxx");

            }
        });

    }
    void navigation(String path) {
        ARouter.getInstance().build(path).navigation(getContext());
    }
}
