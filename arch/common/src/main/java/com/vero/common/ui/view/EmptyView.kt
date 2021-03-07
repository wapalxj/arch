package com.vero.common.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.StringRes
import com.vero.common.R

/**
 * 空页面
 */
class EmptyView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private var icon: TextView? = null
    private var title: TextView? = null
    private var desc: TextView? = null
    private var empty_action: TextView? = null
    private var empty_tips: TextView? = null


    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER
        LayoutInflater.from(context).inflate(R.layout.layout_empty_view, this, true)

        icon = findViewById(R.id.empty_icon)
        desc = findViewById(R.id.empty_text)
        title = findViewById(R.id.empty_title)
        empty_action = findViewById(R.id.empty_action)
        empty_tips = findViewById(R.id.empty_tips)
    }

    fun setIcon(@StringRes iconRes: Int) {
        icon?.setText(iconRes)
    }

    fun setTitle(text: String) {
        title?.text = text
        title?.visibility = if (text.isNullOrBlank()) View.GONE else View.VISIBLE
    }

    fun setDesc(text: String) {
        desc?.text = text
        desc?.visibility = if (text.isNullOrBlank()) View.GONE else View.VISIBLE
    }

    fun setHelpAction(actonId: Int = R.string.if_detail, listener: OnClickListener) {
        empty_tips?.setText(actonId)
        empty_tips?.setOnClickListener(listener)
        empty_tips?.visibility = View.VISIBLE
        if (actonId == -1) {
            empty_tips?.visibility = View.GONE
        }
    }


    fun setButton(text: String, listener: OnClickListener) {
        if (text.isNullOrBlank()) {
            empty_action?.visibility = View.GONE
        } else {
            empty_action?.visibility = View.VISIBLE
            empty_action?.text = text
            empty_action?.setOnClickListener { listener }

        }

    }
}


