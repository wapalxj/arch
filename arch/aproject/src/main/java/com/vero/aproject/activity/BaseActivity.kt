package com.vero.arch.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.vero.aproject.activity.ActivityManager
import com.vero.common.ui.component.HiBaseActivity
import com.vero.hilibrary.log.HiLog
import com.vero.hilibrary.log.HiLogConfig
import com.vero.hilibrary.log.HiLogManager
import com.vero.hilibrary.log.HiLogType.E
import com.vero.hilibrary.log.HiViewPrinter
import com.vero.hiui.tab.bottom.HiTabBottomInfo

open abstract class BaseActivity<Binding : ViewDataBinding> : HiBaseActivity(),
    ActivityManager.FrontBackCallback {

    lateinit var mBinding: Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, getLayout())

        ActivityManager.instance.addFrontBackCallback(this)
    }

    override fun onDestroy() {
        super.onDestroy()

        ActivityManager.instance.removeFrontBackCallback(this)
    }

    abstract fun getLayout(): Int


    /**
     * 前后台切换
     */
    override fun onChanged(front: Boolean) {
        Log.e("onChangedonChanged====", "当前处于${if (front) "前台" else "后台"}")

    }
}
