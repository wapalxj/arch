package com.vero.kotlin


/**
 * 数组
 */

fun main() {
//    arrayType()
    arrayTravel()
}

fun arrayType() {
    //arrayOf
    val array = arrayOf(100, 200, 300)

    //arrayOfNulls
    val array1 = arrayOfNulls<Int>(3)
    array1[0] = 1
    println(array1.contentToString())

    //Array()的构造函数+lambda
    val array2 = Array(5) { i ->
        i * i
    }
    println(array2.contentToString())

    //intArrayOf()
    var x = intArrayOf(1, 2, 3)

    x = IntArray(5)
    x = IntArray(5) { 100 }//lambda,所有元素都是100
}

/**
 * 遍历数组
 */
fun arrayTravel() {
    val array = arrayOf(1, 2, 3)
    //1.数组遍历
    for (item in array) {
//        println(item)
    }

    //2.带索引数组遍历
    for (i in array.indices) {
//        println("$i -> ${array[i]}")
    }

    //3.带索引数组遍历
    for ((index, item) in array.withIndex()) {
        println("$index -> $item")
    }

    //4.foreach
    array.forEach {
        println(it)
    }

    //5.foreach index
    array.forEachIndexed { index, item ->

    }

}


