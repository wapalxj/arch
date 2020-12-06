package com.vero.aproject.route

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import java.lang.RuntimeException

@Interceptor(name = "biz_interceptor",priority = 9)
class BizInterceptor : IInterceptor {
    var context: Context? = null

    override fun init(context: Context?) {
        this.context = context
    }


    //运行在子线程
    override fun process(postcard: Postcard?, callback: InterceptorCallback?) {
        postcard?.let {
            val flag = it.extra
            when {
                flag and (RouteFlag.FLAG_LOGIN) != 0 -> {
                    //表明指定了FLAG_LOGIN
                    callback?.onInterrupt(RuntimeException("need login"))
                    showToast("请先登录")
                }
                flag and (RouteFlag.FLAG_AUTH) != 0 -> {
                    //表明指定了FLAG_AUTH
                    callback?.onInterrupt(RuntimeException("need authentication"))
                    showToast("请先认证")
                }
                flag and (RouteFlag.FLAG_VIP) != 0 -> {
                    //表明指定了FLAG_VIP
                    callback?.onInterrupt(RuntimeException("need become vip"))
                    showToast("请先加入会员")
                }
                else -> {
                    callback?.onContinue(postcard)
                }
            }
        }
    }

    private fun showToast(msg: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
        }
    }


}