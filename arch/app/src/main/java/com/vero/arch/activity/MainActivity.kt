package com.vero.arch.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.IntentService
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.PersistableBundle
import android.util.Log
import android.widget.TextView
import com.tbruyelle.rxpermissions2.RxPermissions
import com.vero.arch.R
import com.vero.arch.banner.HiBannerDemoActivity
import com.vero.arch.databinding.ActivityMainBinding
import com.vero.arch.refresh.HiRefreshDemoActivity
import com.vero.arch.tab.HiTabBottomDemoActivity
import com.vero.arch.tab.HiTabTopDemoActivity


class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPermissions()
        mBinding.toTab.setOnClickListener {
            startActivity(Intent(this, HiTabBottomDemoActivity::class.java))
        }
        mBinding.toTabTop.setOnClickListener {
            startActivity(Intent(this, HiTabTopDemoActivity::class.java))
        }
        mBinding.toRefresh.setOnClickListener {
            startActivity(Intent(this, HiRefreshDemoActivity::class.java))
        }
        mBinding.toBanner.setOnClickListener {
            startActivity(Intent(this, HiBannerDemoActivity::class.java))
        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }

    @SuppressLint("CheckResult")
    private fun initPermissions() {
        val permissionsGroup: Array<String> = arrayOf(
            Manifest.permission.INTERNET
//            Manifest.permission.WRITE_CONTACTS,
//            Manifest.permission.READ_SMS,
//            Manifest.permission.ACCESS_FINE_LOCATION,
//            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        val rxPermissions = RxPermissions(this)
        rxPermissions.request(*permissionsGroup)
            .subscribe({ granted ->
                if (granted) {
                    // All requested permissions are granted
                } else {
                    // At least one permission is denied
                }
            }, {
                it.printStackTrace()
            })
    }

    override fun getLayout(): Int {
        return R.layout.activity_main

    }
}
