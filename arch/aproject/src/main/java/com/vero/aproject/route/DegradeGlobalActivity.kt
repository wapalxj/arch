package com.vero.aproject.route

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.vero.aproject.R
import com.vero.arch.activity.BaseActivity


/**
 * 全局错误页
 */
@Route(path = "/profile/vip", extras = RouteFlag.FLAG_VIP)
class DegradeGlobalActivity : BaseActivity<ViewDataBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun getLayout(): Int {
        return R.layout.activity_degrade_global
    }
}