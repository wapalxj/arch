package com.vero.aproject.biz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.vero.aproject.R
import com.vero.aproject.route.RouteFlag
import com.vero.arch.activity.BaseActivity

@Route(path = "/profile/vip", extras = RouteFlag.FLAG_VIP)
class VipActivity : BaseActivity<ViewDataBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun getLayout(): Int {
        return R.layout.activity_vip
    }
}