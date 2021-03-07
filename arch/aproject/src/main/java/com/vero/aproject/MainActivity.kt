package com.vero.aproject

import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.JsonObject
import com.vero.aproject.biz.LoginActivity
import com.vero.aproject.databinding.ActivityMainBinding
import com.vero.aproject.http.ApiFactory
import com.vero.aproject.http.api.AccountApi
import com.vero.aproject.logic.MainActivityLogic
import com.vero.arch.activity.BaseActivity
import com.vero.hilibrary.log.HiLog
import com.vero.hilibrary.restful.HiCallback
import com.vero.hilibrary.restful.HiResponse

class MainActivity : BaseActivity<ActivityMainBinding>(), MainActivityLogic.ActivityProvider {
    private var activityLogic: MainActivityLogic? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLogic = MainActivityLogic(this,savedInstanceState,mBinding)

        ARouter.getInstance().build("/account/login")
                .navigation()
    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        activityLogic?.onSaveInstanceState(outState)



    }
}
