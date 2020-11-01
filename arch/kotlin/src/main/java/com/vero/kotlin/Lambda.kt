package com.vero.kotlin

fun main() {
//    test()
//    println(test222(1, 1))
    test5()
}

val test = {
    println("无参数")
}

fun test2(a: Int, b: Int): Int {
    return a + b
}

val test22: (Int, Int) -> Int = { a, b ->
    a + b
}

//22的类型推断
val test222 = { a: Int, b: Int ->
    a + b
}

fun test5() {
    val arr = arrayOf(100,4, 2, 3, 4, 5, 6, 7, 8)

    val ret = arr.filter {
        it < 5
    }.component1()//获取集合的第一个元素

    println(ret)
}
