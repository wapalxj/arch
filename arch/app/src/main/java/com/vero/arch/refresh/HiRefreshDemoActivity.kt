package com.vero.arch.refresh

import android.os.Bundle
import com.vero.arch.R
import com.vero.arch.activity.BaseActivity
import com.vero.arch.databinding.ActivityHiRefreshDemoBinding
import com.vero.hiui.refresh.HiRefresh
import com.vero.hiui.refresh.overview.HiTextOverView

class HiRefreshDemoActivity : BaseActivity<ActivityHiRefreshDemoBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val xOverView = HiTextOverView(this)
        mBinding.refreshLayout.let {
            it.setRefreshOverView(xOverView)
            it.setRefreshListener(object : HiRefresh.HiRefreshListener {
                override fun enableRefresh(): Boolean {
                    return true
                }

                override fun onRefresh() {
                    it.postDelayed({
                        it.refreshFinished()
                    }, 3000)
                }

            })
        }
    }


    override fun getLayout(): Int {
        return R.layout.activity_hi_refresh_demo
    }
}