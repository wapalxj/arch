package com.vero.aproject.route

import android.content.Intent
import android.net.Uri
import com.vero.hilibrary.util.AppGlobals

object HiRoute {
    fun startActivityForBrowser(url:String){
        var intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        //防止某些机型拉不起浏览器
        intent.addCategory(Intent.CATEGORY_APP_BROWSER)
        //app context
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        AppGlobals.get()?.startActivity(intent)
    }
}