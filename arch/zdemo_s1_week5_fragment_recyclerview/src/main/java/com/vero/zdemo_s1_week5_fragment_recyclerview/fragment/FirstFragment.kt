package com.vero.zdemo_s1_week5_fragment_recyclerview.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import com.vero.common.ui.component.HiBaseFragment
import com.vero.zdemo_s1_week5_fragment_recyclerview.R

class FirstFragment : HiBaseFragment<ViewDataBinding>() {

    var loaded: Boolean = false
    var root: View? = null
    override fun getLayoutId(): Int {
        return R.layout.fragment_category
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        if (loaded && root != null) {
            return root
        }
        root = super.onCreateView(inflater, container, savedInstanceState)
        loaded = true
        val textView = root?.findViewById<TextView>(R.id.tv)
        textView?.text = System.currentTimeMillis().toString()
        Log.e("FirstFragment000", textView?.text?.toString())
        return root
    }
}