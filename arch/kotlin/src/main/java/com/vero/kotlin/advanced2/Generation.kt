package com.vero.kotlin.advanced2

fun main() {

}

/**
 * 泛型 out  in
 */



/**
 * 泛型约束
 */

//上界
fun <T : Comparable<T>?> sort(list: List<T>?) {

}

//多个上界  where
fun <T> test(list: List<T>, threshold: T): List<T>
        where T : CharSequence,
              T : Comparable<T> {

    return list.filter {
        it > threshold
    }

}


/**
 * 泛型方法
 */
fun <T> fromJson(json: String, tClass: Class<T>): T? {
    var t: T? = tClass.newInstance()
    return t
}


/**
 * 泛型类
 */
class BlueColor(t: Blue) : Color<Blue>(t) {
    override fun printColor() {
        println("color:${t.color}")
    }

}

class Blue {
    val color = "Blue"
}

abstract class Color<T>(var t: T) {
    //泛型字段
    abstract fun printColor()
}

/**
 * 泛型接口
 */
class Coke : Drinks<Sweet> {
    override fun taste(): Sweet {
        return Sweet()
    }

    override fun price(t: Sweet) {
        println(t.price)
    }

}


class Sweet {
    val price = 5
}

interface Drinks<T> {
    fun taste(): T
    fun price(t: T)
}

