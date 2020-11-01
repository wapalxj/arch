package com.vero.kotlin.advanced2

import org.omg.SendingContext.RunTime

fun main() {
    fire(ApiGetArticles())
}


const val HTTP = "http2.0"

enum class Method {
    GET, POST
}

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class HttpMethod(val method: Method)

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class HttpClass(val type: String)

interface Api {
    val name: String
    val version: String
        get() = "1.0"
}

@HttpClass(HTTP)
class ApiGetArticles : Api {
    override val name: String
        get() = "/api.articles"

    @HttpMethod(Method.GET)
    fun get() {
    }

    @HttpMethod(Method.POST)
    fun post() {
    }
}

fun fire(api: Api) {
    //读取类注解
    val annotation = api.javaClass.annotations

    val method = annotation.find {
        it is HttpClass
    } as? HttpClass

    method?.let {
        println("method: ${it.type}")
    }

    //读取方法注解
    api.javaClass.declaredMethods.forEach {
        val method = it.annotations.find {
            it is HttpMethod
        } as? HttpMethod
        method?.let {
            println("fun method: ${it.method}")
        }
    }
}


annotation class ApiDoc(val value: String)

@ApiDoc("修饰类")
class Box {
    @ApiDoc("修饰字段")
    val size = 6


    @ApiDoc("修饰方法")
    fun anno() {

    }
}