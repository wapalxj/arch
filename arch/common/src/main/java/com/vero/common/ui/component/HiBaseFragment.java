package com.vero.common.ui.component;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class HiBaseFragment extends Fragment {
    protected View layoutView;

    public abstract int getLayoutId();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutView = inflater.inflate(getLayoutId(), container, false);

        Log.e("fragment", getClass().getSimpleName() + "-----onCreateView()=");
        return layoutView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("fragment", getClass().getSimpleName() + "-----onStart()=");
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.e("fragment", getClass().getSimpleName() + "-----onResume()=");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("fragment", getClass().getSimpleName() + "-----onPause()=");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("fragment", getClass().getSimpleName() + "-----onStop()=");
    }
}
