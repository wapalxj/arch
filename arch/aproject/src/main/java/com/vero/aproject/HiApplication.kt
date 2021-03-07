package com.vero.aproject

import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.vero.aproject.activity.ActivityManager.Companion.instance
import com.vero.common.ui.component.HiBaseApplication
import com.vero.hilibrary.log.HiConsolePrinter
import com.vero.hilibrary.log.HiLogConfig
import com.vero.hilibrary.log.HiLogManager

class HiApplication : HiBaseApplication() {
    override fun onCreate() {
        super.onCreate()
        ARouter.init(this)
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        instance.init(this)

        HiLogManager.init(object : HiLogConfig() {
            override fun injectJsonParse(): JsonParser {
                return JsonParser { src ->
                    Gson().toJson(src)
                }
            }

            override fun getGlobalTag(): String {
                return "myappppppp"
            }

            override fun enable(): Boolean {
                return true
            }
        }, HiConsolePrinter())
    }
}