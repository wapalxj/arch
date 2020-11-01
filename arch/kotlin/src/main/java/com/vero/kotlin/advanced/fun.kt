package com.vero.kotlin.advanced


fun main() {
    //对集合进行求和，每运算一次，回调一次
    val list = listOf(1, 2, 3, 4, 5)
    list.sum {
//        println(it)
    }


    //
    val list2=listOf("1","1","1")
    val res=list2.toIntSum()(2)
    println(res)
}


/**
 * 函数作为参数
 */
fun List<Int>.sum(callback: (Int) -> Unit): Int {
    var result = 0
    for (v in this) {
        result += v
        callback(result)
    }

    return result
}

/**
 * 函数作为返回值(scale:Int) -> Float
 */
fun List<String>.toIntSum(): (scale: Int) -> Float {
    println("第一层")
    return fun(scaleParam): Float {
        var result = 0f
        for (v in this) {
            result += v.toInt() * scaleParam
        }
        return result
    }
}


