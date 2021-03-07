package com.vero.aproject.http

import com.vero.hilibrary.restful.HiRestful

object ApiFactory {
    private val baseUrl = "https://api.devio.org/as/"
    private val hiRestful = HiRestful(baseUrl, RetrofitCallFactory(baseUrl))

    init {
        hiRestful.addHiInterceptor(BizInterceptor())
        hiRestful.addHiInterceptor(HttpStatusInterceptor())

    }

    fun <T> create(service: Class<T>): T {
        return hiRestful.create(service)
    }
}