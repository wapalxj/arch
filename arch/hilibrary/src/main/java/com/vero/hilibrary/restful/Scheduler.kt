package com.vero.hilibrary.restful

class Scheduler(val callFactory: HiCall.Factory, val interceptors: MutableList<HiInterceptor>) {
    fun newCall(request: HiRequest): HiCall<*> {
        //创建真实Call
        val realCall = callFactory.newCall(request)

        //使用代理创建Call，因为需要派发拦截器
        return ProxyCall(realCall, request)
//        interceptors.forEach()
    }

    /**
     * 静态代理Call，派发拦截器
     */
    internal inner class ProxyCall<T>(val delegate: HiCall<T>, val request: HiRequest) : HiCall<T> {
        override fun execute(): HiResponse<T> {
            //分发拦截器-request
            dispatchInterceptor(request, null)
            //真正的请求
            val response = delegate.execute()

            //分发拦截器-response
            dispatchInterceptor(request, response)

            return response
        }


        override fun enqueue(callback: HiCallback<T>) {
            //分发拦截器-request
            dispatchInterceptor(request, null)
            //真正的请求
            delegate.enqueue(object : HiCallback<T> {
                override fun onSuccess(response: HiResponse<T>) {
                    //分发拦截器-response
                    dispatchInterceptor(request, response)

                    callback?.onSuccess(response)
                }

                override fun onFailed(throwable: Throwable) {
                    callback?.onFailed(throwable)
                }

            })
        }

        private fun dispatchInterceptor(request: HiRequest, response: HiResponse<T>?) {
            InterceptorChain(request, response).dispatch()
        }


        internal inner class InterceptorChain(
            val request: HiRequest,
            val response: HiResponse<T>?
        ) : HiInterceptor.Chain {

            //分发到第几个拦截器
            var callIndex: Int = 0

            override val isRequestPeriod: Boolean
                get() = response == null

            override fun request(): HiRequest {
                return request
            }

            override fun response(): HiResponse<*>? {
                return response

            }

            /**
             * 分发拦截器
             */
            fun dispatch() {
                val interceptor = interceptors[callIndex]
                val intercept = interceptor.intercept(this)
                callIndex++
                if (!intercept && callIndex < interceptors.size) {
                    dispatch()
                }
            }

        }

    }

}