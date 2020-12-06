package com.vero.aproject.route

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.vero.aproject.R
import com.vero.aproject.databinding.ActivityDegradeGlobalBinding
import com.vero.arch.activity.BaseActivity


/**
 * 全局错误页
 */
@Route(path = "/degrade/global/activity")
class DegradeGlobalActivity : BaseActivity<ActivityDegradeGlobalBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding.emptyView.setIcon(R.string.if_unexpected1)
        mBinding.emptyView.setTitle(getString(R.string.degrade_tips))

    }


    override fun getLayout(): Int {
        return R.layout.activity_degrade_global
    }
}