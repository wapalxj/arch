package com.vero.kotlin.advanced

fun main() {
    testClosure(10)(2) {
        println(it)
    }
}

/**
 * 闭包
 * 实现一个方法，接收一个int参数v1
 * 返回一个声明为(v2: Int, (Int) -> Unit) -> Unit的方法，这个方法计算v1+v2
 *
 */
fun testClosure(v1: Int): (v2: Int, (Int) -> Unit) -> Unit {
    return fun(v2: Int, printer: (Int) -> Unit) {
        printer(v1 + v2)
    }
}

//解构声明
data class Result(val message: String, val code: Int)

fun test() {
    var result = Result("success", 0)
    //解构声明
    val (message, code) = result
    println("$message $code")

}

//匿名方法
val fun1 = fun(x: Int): Int {
    return 100 * x
}


//变量temp的类型就是 ((Int) -> Boolean)
fun literal() {
    var temp: ((Int) -> Boolean)? = null
    //{ num -> (num > 10) } 方法字面值
    temp = { num -> (num > 10) }
    temp(11)
}
