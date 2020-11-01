package com.vero.kotlin.advanced

import java.util.*

fun main() {
    Dog(12)
}


class Student {
    //伴生对象
    companion object {

    }
}


//数据类主构造至少要有一个参数
//数据类型不能被继承,不能被open修饰
data class Address(val name: String, val number: Int) {
    var city: String = ""
    fun print() {
        println(city)
    }
}

class C : A, B {
    override fun foo() {
//        super.foo()
        super<A>.foo()
        super<B>.foo()
    }
}

interface A {
    fun foo() {

    }
}

interface B {
    fun foo() {

    }
}


interface Study {
    fun discuss()
    fun learn() {
        //可以有方法的实现
    }
}


//延迟初始化
class Late {
    lateinit var shop: Shop
    fun setup() {
        shop = Shop()
        shop.address = "sssss"
    }

    fun test() {
        if (::shop.isInitialized) {
            //检测有没有被初始化
            println(shop.address)
        }
    }

}


//get set
class Shop {
    val name = "android"
    var address: String? = null
    val isClose: Boolean
        get() = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) > 11

    var score: Float = 0.0f
        get() = if (field < 0.2f) 0.2f else field * 1.5f
        set(value) {
            println(value)
        }

}

open class Animal(age: Int) {
    init {
        println(age)
    }

    open val foot: Int = 0
    open fun eat() {
        println("eat===Animal")
    }

    val simple: Int?
        get() {
            return 1
        }
}

class Dog : Animal {
    constructor(age: Int) : super(age)

    override val foot: Int = 0

    override fun eat() {
        println("eat===Dog")
    }

}


class MyClass constructor(name: String) { //主构造

    //次构造,必须调主构造
    constructor(v: Int, name: String) : this(name) {

    }

}