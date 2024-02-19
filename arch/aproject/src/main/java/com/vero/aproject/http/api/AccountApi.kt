package com.vero.aproject.http.api

import com.google.gson.JsonObject
import com.vero.aproject.model.CourseNotice
import com.vero.aproject.model.UserProfile
import com.vero.hilibrary.restful.HiCall
import com.vero.hilibrary.restful.annotation.Field
import com.vero.hilibrary.restful.annotation.GET
import com.vero.hilibrary.restful.annotation.POST

interface AccountApi {

    @POST("user/login")
    fun login(@Field("userName") userName: String,
              @Field("password") password: String
    ): HiCall<String>

    @POST("user/registration")
    fun register(@Field("userName") userName: String,
                 @Field("password") password: String,
                 @Field("imoocId") imoocId: String,
                 @Field("orderId") orderId: String
    ): HiCall<String>

    @GET("user/profile")
    fun profile(): HiCall<UserProfile>


    @GET("user/notice")
    fun notice(): HiCall<CourseNotice>




}