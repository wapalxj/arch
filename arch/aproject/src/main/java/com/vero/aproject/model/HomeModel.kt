package com.vero.aproject.model


data class HomeModel(
        val bannerList: List<HomeBanner>?,
        val subcategoryList: List<Subcategory>?,
        val goodsList: List<GoodsModel>?
)


/*{
    "categoryId": "1",
    "categoryName": "热门",
    "goodsCount": "1"
}*/

data class TabCategory(val categoryId: String,
                       val categoryName: String,
                       val goodsCount: String
)

/**
 *   {
"id": "10",
"sticky": 1,
"type": "recommend",
"title": "好课推荐",
"subtitle": "Flutter高级进阶实战 仿哔哩哔哩APP",
"url": "https://coding.imooc.com/class/487.html",
"cover": "https://img4.mukewang.com/604ec1a30001d21b17920764.jpg",
"createTime": "2021-03-15 11:51:12"
}
 */

data class HomeBanner(
        val cover: String,
        val createTime: String,
        val id: String,
        val sticky: Int,
        val subtitle: String,
        val title: String,
        val type: String,
        val url: String
)

/**
 * {
"subcategoryId": "1",
"groupName": null,
"categoryId": "1",
"subcategoryName": "限时秒杀",
"subcategoryIcon": "https://o.devio.org/images/as/images/2018-05-16/26c916947489c6b2ddd188ecdb54fd8d.png",
"showType": "1"

 */
data class Subcategory(
        val categoryId: String,
        val groupName: Any,
        val showType: String,
        val subcategoryIcon: String,
        val subcategoryId: String,
        val subcategoryName: String
)


/**
 *  {
"goodsId": "1580373950624",
"categoryId": "14",
"hot": true,
"sliderImages": [
{
"url": "https://o.devio.org/images/as/goods/images/2019-10-17/8a60a5d5-5d8f-4746-8ddd-3411d84d7979.jpg",
"type": 1
},
{
"url": "https://o.devio.org/images/as/goods/images/2020-01-09/003f52a1-dc0d-4368-a5ca-742be926a38a.jpg",
"type": 1
},
{
"url": "https://o.devio.org/images/as/goods/images/2019-10-17/4a3d0c13-a592-4d95-81dd-324d4b0d5504.jpg",
"type": 1
}
],
"marketPrice": "",
"groupPrice": "367",
"completedNumText": "已拼2.2万件",
"goodsName": "看尚液晶电视机55英寸32高清平板50网络智能wifi家用led彩电60 43",
"tags": "送货入户 全场包邮 7天无理由退货 假一赔十",
"joinedAvatars": null,
"createTime": "2020-01-30 16:45:50",
"sliderImage": "https://o.devio.org/images/as/goods/images/2019-10-17/8a60a5d5-5d8f-4746-8ddd-3411d84d7979.jpg"
}
 */


data class GoodsModel(
        val categoryId: String,
        val completedNumText: String,
        val createTime: String,
        val goodsId: String,
        val goodsName: String,
        val groupPrice: String,
        val hot: Boolean,
        val joinedAvatars: Any,
        val marketPrice: String,
        val sliderImage: String,
        val sliderImages: List<SliderImage>,
        val tags: String
)

data class SliderImage(
        val type: Int,
        val url: String
)


