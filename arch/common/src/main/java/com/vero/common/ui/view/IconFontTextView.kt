package com.vero.common.ui.view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import java.util.jar.Attributes


/**
 * 用以全局iconfont资源引用。可以在布局文件直接设置text
 */
class IconFontTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        val typeface=Typeface.createFromAsset(context.assets,"fonts/iconfont.ttf")
        setTypeface(typeface)
    }






}