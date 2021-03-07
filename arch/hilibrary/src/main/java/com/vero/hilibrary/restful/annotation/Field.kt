package com.vero.hilibrary.restful.annotation


//fun test(@Field("city") city: String)


@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Field(val value: String)

