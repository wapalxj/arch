package com.vero.hilibrary.restful.annotation


//@BaseUrl("https://api.develop.com")


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class BaseUrl(val value: String)