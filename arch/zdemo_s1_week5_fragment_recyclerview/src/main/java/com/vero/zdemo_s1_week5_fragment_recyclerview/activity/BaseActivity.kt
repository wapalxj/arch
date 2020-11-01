package com.vero.arch.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.appbar.AppBarLayout
import com.vero.common.ui.component.HiBaseActivity
import com.vero.hilibrary.log.HiLog
import com.vero.hilibrary.log.HiLogConfig
import com.vero.hilibrary.log.HiLogManager
import com.vero.hilibrary.log.HiLogType.E
import com.vero.hilibrary.log.HiViewPrinter
import com.vero.hiui.tab.bottom.HiTabBottomInfo

open abstract class BaseActivity<Binding : ViewDataBinding> : HiBaseActivity() {

    lateinit var mBinding: Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, getLayout())
    }

    abstract fun getLayout(): Int


}
