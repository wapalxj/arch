package com.vero.kotlin

fun main() {
    listType()
//    listSort()
//    setMapType()
}

fun listType() {

    //不可变
    val stringList = listOf("one", "two", "one")
    val stringSet = setOf("one", "two", "one")
//    println(stringSet)

    //可变
    val numbers = mutableListOf(1, 2, 3, 4)
    numbers.add(5)
    numbers.removeAt(0)
    numbers[0] = 200


    //
    val list1 = listOf("one", "two")
    val list2 = listOf("one", "two")
    val list3 = listOf("two", "one")

    //true
    println("list1 == list2 ${list1 == list2}")
    //false
    println("list1 === list2 ${list1 === list2}")
    //false
    println("list1 == list3 ${list1 == list3}")

}

fun listSort() {
    val numbers = mutableListOf(1, 2, 3, 4)
    //随机
    numbers.shuffle()

    //小到大
    numbers.sort()

    //大到小
    numbers.sortDescending()


    //list条件排序
    data class Language(var name: String, var score: Int)

    val languageList = mutableListOf<Language>()
    languageList.add(Language("Java", 80))
    languageList.add(Language("Kotlin", 90))
    languageList.add(Language("Dart", 99))
    languageList.add(Language("C", 80))

    //单条件排序sortBy
    languageList.sortBy {
        it.score
    }
    println(languageList)

    //多条件排序sortWith
    languageList.sortWith(compareBy({ it.score }, { it.name }))

    println(languageList)
}


fun setMapType() {
    //set
    val hello = mutableSetOf("H", "E", "L", "L", "O")

    //集合的加减,前面必须是可变集合
    hello += setOf("1", "2")

    //map
    val map = mapOf("key1" to 1, "key2" to 2, "key3" to 3)
    map.keys
    map.values
    map.entries

//    if (map.containsKey("key1")) {
    if ("key1" in map) {
        //key在不在Map里面
//        println(map["key1"])
    }
//    if (map.containsValue(1)) {
    if (1 in map.values) {
        //value在不在Map里面
//        println("1 in the map")
    }

    //2个Map，相同键值对，顺序不同
    val map2 = mapOf("key1" to 1, "key2" to 2)
    val map3 = mapOf("key2" to 2, "key1" to 1)

    //true
    println("map2 == map3 ${map2 == map3}")
    //false
    println("map2 === map3 ${map2 === map3}")


}


