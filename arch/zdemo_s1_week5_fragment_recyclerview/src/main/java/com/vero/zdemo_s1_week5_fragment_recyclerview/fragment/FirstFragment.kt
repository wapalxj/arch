package com.vero.zdemo_s1_week5_fragment_recyclerview.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.vero.common.ui.component.HiBaseFragment
import com.vero.zdemo_s1_week5_fragment_recyclerview.R

class FirstFragment : HiBaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_category
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = super.onCreateView(inflater, container, savedInstanceState)

        val textView = root?.findViewById<TextView>(R.id.tv)
        textView?.text = System.currentTimeMillis().toString()
        return root
    }
}