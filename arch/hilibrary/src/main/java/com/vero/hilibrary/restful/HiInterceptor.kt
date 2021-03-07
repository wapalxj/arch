package com.vero.hilibrary.restful

interface HiInterceptor {
    fun intercept(chain: Chain): Boolean


    /**
     * China对象 会在我们派发拦截器的时候 创建
     */
    interface Chain {

        /**
         * 是否是网络发起之前
         */
        val isRequestPeriod: Boolean get() = false

        fun request(): HiRequest

        /**
         * 网络发起之前，是为空的
         */
        fun response(): HiResponse<*>?
    }
}