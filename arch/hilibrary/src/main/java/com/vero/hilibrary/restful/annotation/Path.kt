package com.vero.hilibrary.restful.annotation


//@GET("cities/{province}")
//fun test(@Path("province") city: Int)


@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Path(val value: String)