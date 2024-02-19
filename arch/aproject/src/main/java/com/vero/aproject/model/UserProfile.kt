package com.vero.aproject.model


/**
 * {
        "isLogin": false,
        "favoriteCount": 0,
        "browseCount": 0,
        "bannerNoticeList": [
             {
                    "id": "2",
                    "sticky": 1,
                    "type": "recommend",
                    "title": "好课推荐",
                    "subtitle": "Flutter从入门到进阶-实战携程网App",
                    "url": "https://coding.imooc.com/class/321.html",
                    "cover": "https://o.devio.org/images/o_as/other/flutter_321.jpg",
                    "createTime": "2020-06-11 23:48:38"
            }
        ]
    }
 */
data class UserProfile(val isLogin :Boolean,
                       val favoriteCount :Int,
                       val browseCount :Int,
                       val learnMinutes :Int,
                       val userName :String,
                       val avatar :String?,
                       val bannerNoticeList :List<Notice>?
                       )