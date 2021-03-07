package com.vero.hilibrary.restful

import com.vero.hilibrary.restful.annotation.*
import java.lang.Exception
import java.lang.IllegalStateException
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class MethodParser(var baseUrl: String, method: Method, args: Array<Any>) {
    //方法的泛型返回类型
    var returnType: Type? = null
    var relativeUrl: String? = null
    var httpMethod: Int? = null

    //post是否表单提交
    var formPost: Boolean = false

    var headers: MutableMap<String, String> = mutableMapOf()
    var parameters: MutableMap<String, String> = mutableMapOf()
    var domainUrl: String? = null

    init {
        //parse Method Annotations,get/headers/post/baseurl
        //解析方法上标记的注解
        parseMethodAnnotations(method)

        //parse path,filed
        //解析入参以及其注解
        parseMethodParameters(method, args)

        //genric return type
        //解析泛型返回类型
        parseMethodReturnType(method)
    }

    /**
     * 解析方法泛型返回值
     *
     * interface ApoService{
     *
     * @Headers("auth-token:token","accountId:123456")
     * @BaseUrl("http:api.devio.org/as/")
     * @POST(/cities/{province})
     * @GET(/cities)
     * fun listCities(@Path("province") province :Int):HiCall<JsonObject>
     * }
     */
    private fun parseMethodReturnType(method: Method) {
        if (method.returnType != HiCall::class.java) {
            throw IllegalStateException(
                String.format("method %s must be type od HiCall.class", method.name)
            )
        }

        //获取泛型参数
        val genericReturnType = method.genericReturnType

        if (genericReturnType is ParameterizedType) {
            //泛型参数只能有一个
            val actualTypeArguments = genericReturnType.actualTypeArguments
            require(actualTypeArguments.size == 1) {
                String.format("method %s can only has one generic return type", method.name)
            }
            returnType = actualTypeArguments[0]
        } else {
            throw IllegalStateException(
                String.format("method %s must has one generic return type", method.name)
            )
        }
    }

    private fun parseMethodParameters(method: Method, args: Array<Any>) {
        //@Path("province") province :Int
        //异常情况，注解数和参数数不一致

        val parameterAnnotations = method.parameterAnnotations
        val equals = parameterAnnotations.size == args.size
        require(equals) {
            throw IllegalStateException(
                "arguments annotaions count ${method.parameterAnnotations.size} dont match expect count ${args.size} "
            )
        }

        for (index in args.indices) {
            val annotations = parameterAnnotations[index]
            //从语法上一个参数可以有多个注解
            //所以parameterAnnotations是一个二维数组

            //一个参数只能有一个注解
            require(annotations.size <= 1) {
                throw IllegalStateException(
                    "field can only has one annotation :index=${index} "
                )
            }

            val value = args[index]
            //只能是8种基本类型(包括包装类型)+String
            require(isPrimitive(value)) {
                throw IllegalStateException(
                    "String and 8 basic types are supported for now,index=${index} "
                )
            }

            val annotation = annotations[0]
            if (annotation is Field) {
                val key = annotation.value
                val value = args[index]
                parameters[key] = value.toString()

            } else if (annotation is Path) {
                val replaceName = annotation.value
                val replacement = value.toString()
                if (replaceName != null && replacement != null) {
                    val newRelativeUrl = relativeUrl?.replace("{$replaceName}", replacement)
                    relativeUrl = newRelativeUrl
                }
            } else {
                throw IllegalStateException("cannnot handle method annotation: ${annotation.javaClass.toString()}")

            }

        }

    }

    //只能是8种基本类型(包括包装类型)+String
    private fun isPrimitive(value: Any): Boolean {
        if (value.javaClass == String::class.java) {
            return true
        }
        try {
            val field = value.javaClass.getField("TYPE")
            val clazz = field.get(null) as Class<*>
            if (clazz.isPrimitive) {
                return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }


    //解析方法上标记的注解
    private fun parseMethodAnnotations(method: Method) {
        val annotations = method.annotations
        annotations.forEach {
            when (it) {
                is GET -> {
                    relativeUrl = it.value
                    httpMethod = HiRequest.METHOD.GET
                }
                is POST -> {
                    relativeUrl = it.value
                    httpMethod = HiRequest.METHOD.POST
                    formPost = it.formPost
                }
                is Headers -> {
                    // @Headers("key:value","key2:value2")
                    it.value.forEach {
                        //冒号检查
                        val colon = it.indexOf(":")
                        check(!(colon == 0 || colon == -1)) {
                            throw IllegalStateException(
                                String.format(
                                    "@Headers value must be in the form [name:value],but find [%s]",
                                    it
                                )
                            )
                        }

                        val name = it.substring(0, colon)
                        val value = it.substring(colon + 1).trim()
                        headers[name] = value

                    }
                }
                is BaseUrl -> {
                    domainUrl = it.value
                }
                else -> {
                    throw IllegalStateException("cannnot handle method annotation: ${it.javaClass.toString()}")
                }
            }

            if (domainUrl == null) {
                domainUrl = baseUrl
            }
        }

        require(httpMethod == HiRequest.METHOD.GET || httpMethod == HiRequest.METHOD.POST) {
            throw IllegalStateException("method ${method.name} must be one of GET,POST")
        }
    }

    fun newRequest(): HiRequest {
        var request = HiRequest()
        request.let {
            it.domainUrl = domainUrl
            it.headers = headers
            it.httpMethod = httpMethod
            it.parameters = parameters
            it.relativeUrl = relativeUrl
            it.returnType = returnType
            it.formPost = formPost
        }

        return request

    }

    companion object {
        fun parse(baseUrl: String, method: Method, args: Array<Any>): MethodParser {
            return MethodParser(baseUrl, method, args)
        }
    }
}