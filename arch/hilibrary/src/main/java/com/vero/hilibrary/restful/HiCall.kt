package com.vero.hilibrary.restful

import java.io.IOException

interface HiCall<T> {

    @Throws(IOException::class)
    fun execute(): HiResponse<T>

    fun enqueue(callback: HiCallback<T>)

    //抽象工厂
    interface Factory {
        fun newCall(request: HiRequest): HiCall<*>
    }


}