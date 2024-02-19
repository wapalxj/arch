package com.vero.common.ui.view

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlin.math.min

fun ImageView.loadUrl(url: String) {
    Glide.with(this).load(url).into(this)
}

//圆形
fun ImageView.loadCircle(url: String) {
    Glide.with(this).load(url).transform(CenterCrop()).into(this)
}

//圆角
//巨坑:Glide的 图片裁剪和 ImageView scaleType冲突
fun ImageView.loadCorner(url: String, corner: Int) {
//    Glide.with(this).load(url).transform(RoundedCorners(corner)).into(this)
    //先居中裁剪，再圆角
    Glide.with(this).load(url).transform(CenterCrop(), RoundedCorners(corner)).into(this)
}

//圆角描边
fun ImageView.loadCircleBorder(url: String, borderWidth: Float = 0f, borderColor: Int = Color.WHITE) {
    Glide.with(this).load(url).transform(CircleBorderTransform()).into(this)

}

//描边
class CircleBorderTransform(val borderWidth: Float = 0f,
                            val borderColor: Int = Color.WHITE) : CircleCrop() {
    private var borderPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        borderPaint.color = borderColor
        borderPaint.style = Paint.Style.STROKE
        borderPaint.strokeWidth = borderWidth

    }

    override fun transform(pool: BitmapPool,
                           toTransform: Bitmap,
                           outWidth: Int,
                           outHeight: Int): Bitmap {
        val transform = super.transform(pool, toTransform, outWidth, outHeight)
        val canvas = Canvas(transform)
        val halfWidth = outWidth / 2.toFloat()
        val halfHeight = outHeight / 2.toFloat()

        canvas.drawCircle(halfWidth,
                halfHeight,
                //减去一半是因为不让描边画到外边去
                min(halfWidth, halfHeight) - borderWidth / 2,
                borderPaint
        )
        canvas.setBitmap(null)
        return transform
    }

}