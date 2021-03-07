package com.vero.hilibrary.restful.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)

//formPost  post是否表单提交
annotation class POST(val value: String, val formPost: Boolean = true)