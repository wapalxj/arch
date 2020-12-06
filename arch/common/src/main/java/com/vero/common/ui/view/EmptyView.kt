package com.vero.common.ui.view

import android.content.Context
import android.graphics.Typeface
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TimeUtils
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
    private var button: TextView? = null


    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER
        LayoutInflater.from(context).inflate(R.layout.layout_empty_view, this, true)

        icon = findViewById(R.id.empty_icon)
        title = findViewById(R.id.empty_text)
        button = findViewById(R.id.empty_action)

        var typeface: Typeface = Typeface.createFromAsset(context.assets, "fonts/iconfont.ttf")
        icon?.typeface = typeface
    }

    fun setIcon(@StringRes iconRes: Int) {
        icon?.setText(iconRes)
    }

    fun setTitle(text: String) {
        title?.text = text
        title?.visibility = if (TextUtils.isDigitsOnly(text)) View.GONE else View.VISIBLE
    }

    fun setButton(text: String, listener: OnClickListener) {
        if (text.isNullOrBlank()) {
            button?.visibility=View.GONE
        }else{
            button?.visibility=View.VISIBLE
            button?.text = text
            button?.setOnClickListener { listener }

        }

    }
}


