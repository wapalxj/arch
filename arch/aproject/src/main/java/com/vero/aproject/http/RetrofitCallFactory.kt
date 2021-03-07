package com.vero.aproject.http

import com.vero.hilibrary.restful.HiCall
import com.vero.hilibrary.restful.HiCallback
import com.vero.hilibrary.restful.HiRequest
import com.vero.hilibrary.restful.HiResponse
import okhttp3.FormBody
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.*
import java.lang.IllegalStateException

class RetrofitCallFactory (val baseUrl: String):HiCall.Factory{
    private var apiService:ApiService
    private var gsonConvert:GsonConvert

    init {
        val retrofit=Retrofit.Builder()
            .baseUrl(baseUrl)
            .build()
        apiService=retrofit.create(ApiService::class.java)
        gsonConvert=GsonConvert()

    }


    override fun newCall(request: HiRequest): HiCall<Any> {
        return RetrofitCall(request)
    }

    internal inner class RetrofitCall<T>(val request: HiRequest):HiCall<T>{
        override fun execute(): HiResponse<T> {
            val realCall=createRealCall(request)
            val response=realCall.execute()
            return parseResponse(response)
        }

        private fun parseResponse(response: Response<ResponseBody>): HiResponse<T> {
            var rawData:String=""
            if (response.isSuccessful) {
                val body =response.body()
                if (body != null) {
                    rawData=body.string()
                }
            }else{
                val body=response.errorBody()
                if (body != null) {
                    rawData=body.string()
                }
            }

            return  gsonConvert.convert(rawData,request.returnType!!)
        }

        override fun enqueue(callback: HiCallback<T>) {
            val realCall=createRealCall(request)
            realCall.enqueue(object :Callback<ResponseBody>{
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    callback.onFailed(t)

                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    callback.onSuccess(parseResponse(response))
                }

            })
        }

        private fun createRealCall(request: HiRequest):Call<ResponseBody> {
            if (request.httpMethod==HiRequest.METHOD.GET) {
                return apiService.get(request.headers,request.endPointUrl(),request.parameters)
            }else if (request.httpMethod==HiRequest.METHOD.POST) {
                val builder=FormBody.Builder()
                var requestBody :RequestBody?=null
                val jsonObject=JSONObject()

                request.parameters?.let {
                    for ((key,value) in it){
                        if (request.formPost){
                            builder.add(key,value)
                        }else{
                            jsonObject.put(key,value)
                        }
                    }
                }
                requestBody = if (request.formPost) {
                    builder.build()
                }else{
                    RequestBody.create(MediaType.parse("application/json;utf-8"),jsonObject.toString())
                }

                return apiService.post(request.headers,request.endPointUrl(),requestBody)
            }else{
                throw IllegalStateException("hirestful only support GET and POST")
            }
        }

    }

    interface ApiService{
        @GET
        fun get(@HeaderMap headers:MutableMap<String,String>?,
                @Url url:String,
                @QueryMap(encoded = true) param:MutableMap<String,String>?
        ):Call<ResponseBody>

        @POST
        fun post(@HeaderMap headers:MutableMap<String,String>?,
                @Url url:String,
                @Body body:RequestBody?
        ):Call<ResponseBody>
    }

}