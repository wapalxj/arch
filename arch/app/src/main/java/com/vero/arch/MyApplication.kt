package com.vero.arch

import android.app.Application
import com.google.gson.Gson
import com.vero.hilibrary.log.HiConsolePrinter
import com.vero.hilibrary.log.HiLogConfig
import com.vero.hilibrary.log.HiLogManager
import com.vero.hilibrary.log.HiLogPrinter

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        HiLogManager.init(object : HiLogConfig() {
            override fun injectJsonParse(): JsonParser {
                return JsonParser { src ->
                    Gson().toJson(src)
                }
            }

            override fun getGlobalTag(): String {
                return "myappppppp"
            }

            override fun enable(): Boolean {
                return true
            }
        }, HiConsolePrinter())
    }
}