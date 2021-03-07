package com.vero.aproject.route

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.vero.aproject.R
import com.vero.aproject.databinding.ActivityDegradeGlobalBinding
import com.vero.arch.activity.BaseActivity


/**
 * 全局错误页
 */
@Route(path = "/degrade/global/activity")
class DegradeGlobalActivity : BaseActivity<ActivityDegradeGlobalBinding>() {

    @JvmField
    @Autowired
    var degrade_title: String? = null

    @JvmField
    @Autowired
    var degrade_desc: String? = null

    @JvmField
    @Autowired
    var degrade_action: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        mBinding.emptyView.setIcon(R.string.if_unexpected1)

        mBinding.emptyView.setIcon(R.string.if_unexpected1)

        if (degrade_title != null) {
            mBinding.emptyView.setTitle(degrade_title!!)
        }
        if (degrade_desc != null) {
            mBinding.emptyView.setDesc(degrade_desc!!)
        }

        if (degrade_action != null) {
            mBinding.emptyView.setHelpAction(listener = View.OnClickListener {
                var intent=Intent(Intent.ACTION_VIEW, Uri.parse(degrade_action))
                startActivity(intent)
            })
        }

        mBinding.actionBack.setOnClickListener {
            onBackPressed()
        }

    }


    override fun getLayout(): Int {
        return R.layout.activity_degrade_global
    }
}