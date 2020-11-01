package com.vero.arch.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.vero.arch.R
import com.vero.arch.databinding.ActivityMainBinding
import com.vero.hilibrary.log.HiLog
import com.vero.hilibrary.log.HiLogConfig
import com.vero.hilibrary.log.HiLogManager
import com.vero.hilibrary.log.HiLogType.E
import com.vero.hilibrary.log.HiViewPrinter
import com.vero.hiui.tab.bottom.HiTabBottomInfo

open abstract class BaseActivity<Binding : ViewDataBinding> : AppCompatActivity() {

    lateinit var mBinding: Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,getLayout())
    }

    abstract fun getLayout(): Int
}
