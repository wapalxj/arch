package com.vero.aproject.http

import com.vero.common.utils.SPUtils
import com.vero.hilibrary.log.HiLog
import com.vero.hilibrary.restful.HiInterceptor

/**
 * token 拦截器
 */
class BizInterceptor :HiInterceptor{


    override fun intercept(chain: HiInterceptor.Chain): Boolean {
        if (chain.isRequestPeriod) {
            val request=chain.request()
            val boardingPass=SPUtils.getString("boarding-pass")?:""
            request.addHeader("boarding-pass",boardingPass)
            request.addHeader("auth-token","MTU5Mjg1MDg3NDcwNw11.26==")

            //测试错误的token跳转错误页面DegradeGlobalActivity
//            request.addHeader("auth-token","MTU5Mjg1MDg3NDcwNw11.11==")
            HiLog.et("BizInterceptor","0000000000")
        }else if (chain.response()!=null) {
            HiLog.et("BizInterceptorxx11111",chain.request().endPointUrl())
            HiLog.et("BizInterceptorxx22222",chain.request().headers)
            HiLog.et("BizInterceptorxx33333",chain.response()?.rawData)
        }
        return false

    }

}