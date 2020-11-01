package com.vero.kotlin.advanced2

fun main() {
//    val list = mutableListOf(1, 2, 3, 4, 5, 6)
//    list.swap(0, 1)
//    println(list)

//    val list = mutableListOf("1", "2", "3", "4", "5", "6")
//    list.swap2(0, 1)
//    println(list)
//
//    Jump.print("cccccc")

    //run

    val s="sss"
    s.apply {

    }

}

fun MutableList<Int>.swap(index1: Int, index2: Int) {
    val temp = this[index1]
    this[index1] = this[index2]
    this[index2] = temp
}

//泛型扩展
fun <T> MutableList<T>.swap2(index1: Int, index2: Int) {
    val temp = this[index1]
    this[index1] = this[index2]
    this[index2] = temp
}

/**
 * 扩展属性
 */
val String.lastChar: Char get() = this[this.length - 1]

/**
 * 伴生对象扩展方法
 */
class Jump {
    companion object {}
}

fun Jump.Companion.print(str: String) {
    println(str)
}

