package com.vero.hilibrary.restful

import androidx.annotation.IntDef
import java.lang.IllegalStateException
import java.lang.reflect.Type

open class HiRequest {

    companion object {
        val SUCCESS: Int = 0
    }

    @METHOD
    var httpMethod: Int? = METHOD.GET
    var headers: MutableMap<String, String>? = null
    var parameters: MutableMap<String, String>? = null
    var domainUrl: String? = null
    var relativeUrl: String? = null
    var returnType: Type? = null
    var formPost: Boolean = true

    @IntDef(value = [METHOD.GET, METHOD.POST])
    annotation class METHOD {
        companion object {
            const val GET = 0
            const val POST = 1
        }

    }

    //返回的是请求的完整的url
    /**
     * //scheme-host-port:443
    //https://api.devio.org/v1/ ---relativeUrl: user/login===>https://api.devio.org/v1/user/login

    //可能存在别的域名的场景
    //https://api.devio.org/v2/


    //https://api.devio.org/v1/ ---relativeUrl: /v2/user/login===>https://api.devio.org/v2/user/login
     */
    //请求的完整url
    fun endPointUrl(): String {
//        if (relativeUrl == null) {
//            throw IllegalStateException("relativeUrl must not be null")
//        }
//        if (relativeUrl?.startsWith("/") != true) {
//            return domainUrl + relativeUrl
//        }
//        //兼容
//        val indexOf = domainUrl?.indexOf("/") ?: 0
//        return domainUrl?.substring(0, indexOf) + relativeUrl

        if (relativeUrl == null) {
            throw IllegalStateException("relative url must bot be null ")
        }
        if (!relativeUrl!!.startsWith("/")) {
            return domainUrl + relativeUrl
        }

        val indexOf = domainUrl!!.indexOf("/")
        return domainUrl!!.substring(0, indexOf) + relativeUrl
    }


    //请求的完整url
    fun addHeader(key:String,value:String) {
        if (headers == null) {
            headers= mutableMapOf()
        }
        headers?.put(key,value)
    }
}