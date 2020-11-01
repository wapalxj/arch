package com.vero.aproject;

import com.alibaba.android.arouter.launcher.ARouter;
import com.vero.aproject.activity.ActivityManager;
import com.vero.common.ui.component.HiBaseApplication;

public class HiApplication extends HiBaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
        ActivityManager.Companion.getInstance().init(this);
    }
}
