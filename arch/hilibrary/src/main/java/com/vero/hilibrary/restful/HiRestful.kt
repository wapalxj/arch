package com.vero.hilibrary.restful

import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.concurrent.ConcurrentHashMap

class HiRestful constructor(val baseUrl: String, val callFactory: HiCall.Factory) {

    private var interceptors: MutableList<HiInterceptor> = mutableListOf()

    //缓存解析的方法MethodParser
    private var methodService = ConcurrentHashMap<Method, MethodParser>()

    private var scheduler: Scheduler

    fun addHiInterceptor(interceptor: HiInterceptor) {
        interceptors.add(interceptor)
    }

    init {
        scheduler = Scheduler(callFactory, interceptors)
    }


    //根据接口的Class对象，返回该接口的代理对象
    fun <T> create(service: Class<T>): T {
        return Proxy.newProxyInstance(
            service.classLoader,
            arrayOf<Class<*>>(service)
        ) { proxy, method, args ->

            var methodParser = methodService[method]
            if (methodParser == null) {
                //解析method
                methodParser = MethodParser.parse(baseUrl, method, args)
                methodService[method] = methodParser
            }
            //创建HiCall
            val request = methodParser.newRequest()
//            callFactory.newCall(request)
            //使用代理创建Call，因为需要派发拦截器
            scheduler.newCall(request)
        } as T
    }

}