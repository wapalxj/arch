package com.vero.arch.activity

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.lang.ref.WeakReference
import java.util.ArrayList

/**
 * Activity堆栈管理
 * 已经获取栈顶Activity
 */
class ActivityManager private constructor() {

    private val activityRefs = ArrayList<WeakReference<Activity>>()
    private val frontBackCallback = ArrayList<FrontBackCallback>()
    private var activityStartCount = 0//处于前台Activity的数量
    private var front = true//app处于前台


    fun init(app: Application) {
        app.registerActivityLifecycleCallbacks(InnerActivityLifecycleCallbacks())
    }

    fun addFrontBackCallback(callback: FrontBackCallback) {
        frontBackCallback.add(callback)
    }

    fun removeFrontBackCallback(callback: FrontBackCallback) {
        frontBackCallback.remove(callback)
    }

    interface FrontBackCallback {
        fun onChanged(front: Boolean)
    }

    //kotlin单例模式
    companion object {

        val instance: ActivityManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            ActivityManager()
        }
    }

    //回调前后台变化
    private fun onFrontBackChanged(front: Boolean) {
        frontBackCallback.forEach {
            it.onChanged(front)
        }
    }

    //获取栈顶Activity
    val topActivity: Activity?
        get() {
            return if (activityRefs.isNotEmpty()) {
                activityRefs.last().get()
            } else {
                null
            }
        }

    inner class InnerActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            activityRefs.add(WeakReference(activity))
        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStarted(activity: Activity) {
            activityStartCount++
            //!front 表示之前在后台
            if (!front && activityStartCount > 0) {
                //从后台到前台
                onFrontBackChanged(front)
            }
        }


        override fun onActivityStopped(activity: Activity) {
            activityStartCount--
            if (activityStartCount <= 0 && front) {
                //从前台到后台
                front = false
                onFrontBackChanged(front)
            }
        }


        override fun onActivityResumed(activity: Activity) {

        }

        override fun onActivityDestroyed(activity: Activity) {
            activityRefs.find {
                it.get() == activity
            }?.let {
                activityRefs.remove(it)
            }

        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }


    }
}