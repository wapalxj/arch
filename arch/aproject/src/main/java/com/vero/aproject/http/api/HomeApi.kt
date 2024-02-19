package com.vero.aproject.http.api

import com.google.gson.JsonObject
import com.vero.aproject.model.CourseNotice
import com.vero.aproject.model.HomeModel
import com.vero.aproject.model.TabCategory
import com.vero.aproject.model.UserProfile
import com.vero.hilibrary.restful.HiCall
import com.vero.hilibrary.restful.annotation.Field
import com.vero.hilibrary.restful.annotation.GET
import com.vero.hilibrary.restful.annotation.POST
import com.vero.hilibrary.restful.annotation.Path

interface HomeApi {

    @GET("category/categories")
    fun queryTabList(): HiCall<List<TabCategory>>


    @GET("home/{categoryId}")
    fun queryTabCategoryList(
            @Path("categoryId") categoryId: String,
            pageIndex: Int,
            pageSize: Int
    ): HiCall<HomeModel>

}