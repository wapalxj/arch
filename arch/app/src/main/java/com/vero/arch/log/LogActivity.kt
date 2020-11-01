package com.vero.arch.log

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.vero.arch.R
import com.vero.arch.activity.BaseActivity
import com.vero.arch.databinding.ActivityLogBinding
import com.vero.arch.databinding.ActivityMainBinding
import com.vero.hilibrary.log.*

class LogActivity : BaseActivity<ActivityLogBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var viewPrinter: HiViewPrinter? = null
        super.onCreate(savedInstanceState)
        //可视化
        viewPrinter = HiViewPrinter(this)
        viewPrinter.viewProvider?.showFloatingView()
        HiLogManager.getInstance().addPrinter(viewPrinter)

        mBinding.btn.setOnClickListener {
            printLog()
        }

    }

    fun printLog() {
        //自定义配置
        HiLog.log(object : HiLogConfig() {
            override fun includeThread(): Boolean {
                return true
            }

            override fun stackTraceDepth(): Int {
                return 0
            }
        }, HiLogType.E, "======", "55566666")

        HiLog.a("11111111111")

    }

    override fun getLayout(): Int {
        return R.layout.activity_log
    }
}
