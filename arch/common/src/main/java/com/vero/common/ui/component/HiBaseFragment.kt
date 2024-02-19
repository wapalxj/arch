package com.vero.common.ui.component

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class HiBaseFragment<Binding : ViewDataBinding> : Fragment() {
    protected var layoutView: View? = null
    lateinit var mBinding: Binding

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.e("fragment", javaClass.simpleName + "-----onCreateView()=")
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        layoutView = mBinding.root
        return layoutView
    }

    open fun isAlive(): Boolean {
        if (isRemoving || isDetached || activity == null || activity!!.isFinishing || activity!!.isDestroyed) {
            return false
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("fragment", javaClass.simpleName + "-----onCreate()=" + this)
    }

    override fun onStart() {
        super.onStart()
        Log.e("fragment", javaClass.simpleName + "-----onStart()=" + this)
    }

    override fun onResume() {
        super.onResume()
        Log.e("fragment", javaClass.simpleName + "-----onResume()=" + this)
    }

    override fun onPause() {
        super.onPause()
        Log.e("fragment", javaClass.simpleName + "-----onPause()=" + this)
    }

    override fun onStop() {
        super.onStop()
        Log.e("fragment", javaClass.simpleName + "-----onStop()=" + this)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("fragment", javaClass.simpleName + "-----onDestroy()=" + this)
    }
}