package com.vero.aproject.http

import com.alibaba.android.arouter.launcher.ARouter
import com.vero.common.utils.SPUtils
import com.vero.hilibrary.log.HiLog
import com.vero.hilibrary.restful.HiInterceptor
import com.vero.hilibrary.restful.HiResponse

/**
 * 根据http response code 自动路由到相关页面
 */
class HttpStatusInterceptor :HiInterceptor{

    override fun intercept(chain: HiInterceptor.Chain): Boolean {
        val response=chain.response()
        if (!chain.isRequestPeriod && response!=null) {
            val code=response.code
            when(code){
                HiResponse.RC_NEED_LOGIN->{
                    ARouter.getInstance().build("/account/login").navigation()
                }
                HiResponse.RC_AUTH_TOKEN_EXPIRED,
                HiResponse.RC_AUTH_TOKEN_INVALID,
                HiResponse.RC_USER_FORBID ->{
                    //token过期
                    var helpeUrl:String?=null
                    if (response.errorData!=null) {
                        helpeUrl= response.errorData?.get("helpUrl")
                    }
                    ARouter.getInstance().build("/degrade/global/activity")
                            .withString("degrade_title","非法访问")
                            .withString("degrade_desc",response.msg)
                            .withString("degrade_action",helpeUrl)
                            .navigation()
                }
                HiResponse.RC_NEED_LOGIN->{

                }
            }

        }
        return false

    }

}