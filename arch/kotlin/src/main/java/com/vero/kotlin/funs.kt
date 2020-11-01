package com.vero.kotlin

import java.lang.StringBuilder

fun main() {

}




/**
 * 局部方法
 */
fun magic(): Int {
    fun foo(v: Int): Int {
        return v * v
    }

    val v = (0..100).random()
    return foo(v)
}

/**
 * 可变数量参数
 */
fun append(vararg str: Char): String {
    val result = StringBuilder()
    for (char in str) {
        result.append(char)
    }

    return result.toString()
}

//默认参数
fun read(b: Array<Byte>, off: Int = 0, len: Int = b.size) {

}


class Person {
    fun test() {

    }

    companion object {
        fun testCompanion() {

        }
    }

}

/**
 * 整个静态类
 */
object NumUtils {
    fun double(num: Int): Int {
        return num * 2
    }
}