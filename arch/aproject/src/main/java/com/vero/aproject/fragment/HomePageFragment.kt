package com.vero.aproject.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.launcher.ARouter
import com.vero.aproject.R
import com.vero.common.ui.component.HiBaseFragment
import coroutines.CoroutinesScene3
import kotlinx.coroutines.launch

class HomePageFragment : HiBaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        layoutView.findViewById<View>(R.id.btn_login)
            .setOnClickListener {
                // navigation("/profile/detail");
//                CoroutinesScene.INSTANCE.startScene2();
//                CoroutinesScene3.INSTANCE.startScene3();

                lifecycleScope.launch {
                    val res=CoroutinesScene3.parseAssetsFile(activity!!.assets,"config.txt")
                    Log.e("CoroutinesScene3", " res==$res")
                }


                Log.e("CoroutinesScene3","after-click")
            }


        layoutView.findViewById<View>(R.id.btn_vip)
            .setOnClickListener { navigation("/profile/vip") }
        layoutView.findViewById<View>(R.id.btn_auth)
            .setOnClickListener { navigation("/profile/authentication") }
        layoutView.findViewById<View>(R.id.btn_unknow)
            .setOnClickListener { navigation("/profile/xxxxxxxxxxx") }
    }

    fun navigation(path: String?) {
        ARouter.getInstance().build(path).navigation(context)
    }
}