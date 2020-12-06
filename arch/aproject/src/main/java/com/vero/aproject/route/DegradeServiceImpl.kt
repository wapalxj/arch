package com.vero.aproject.route

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.DegradeService
import com.alibaba.android.arouter.launcher.ARouter

/**
 *  全局降级服务，找不到路由目标页时
 */

@Route(path = "/degrade/global/service")
class DegradeServiceImpl : DegradeService {
    var context: Context? = null
    override fun init(context: Context?) {
        this.context = context
    }

    /**
     * 目标页不存在的回调
     */
    override fun onLost(context: Context?, postcard: Postcard?) {
        ARouter.getInstance()
            .build("/degrade/global/activity")
            .greenChannel()
            .navigation()
    }

}