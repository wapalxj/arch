package com.vero.kotlin

import java.util.*

class MyClass {
}

fun main() {
    val num1 = 1.5
    val num2 = 1.5f
    val num3 = 1
    printType(num1)
    printType(num2)
    printType(num3)
    printType("ssssss")
}

fun printType(param: Any) {
    println("$param is ${param::class.simpleName}")
}
