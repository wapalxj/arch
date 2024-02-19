package com.vero.aproject.fragment.home

import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.vero.aproject.R
import com.vero.aproject.model.GoodsModel
import com.vero.common.ui.view.loadUrl
import com.vero.hilibrary.util.HiDisplayUtil
import kotlinx.android.synthetic.main.layout_home_goods_list_item1.view.*
import org.devio.`as`.hi.hiitem.hiitem.HiDataItem

class GoodsItem(val goodsModel: GoodsModel, val hotTab: Boolean = false) : HiDataItem<GoodsModel, RecyclerView.ViewHolder>(goodsModel) {
    private val MAX_TAG_SIZE = 3
    override fun onBindData(holder: RecyclerView.ViewHolder, position: Int) {

        val context = holder.itemView.context
        holder.itemView.item_image.loadUrl(goodsModel.sliderImage)
        holder.itemView.item_title.text = goodsModel.goodsName
        holder.itemView.item_price.text = goodsModel.marketPrice
        holder.itemView.item_sale_desc.text = goodsModel.completedNumText


        //标签
        val container = holder.itemView.item_label_container
        if (container != null) {
            if (!TextUtils.isEmpty(goodsModel.tags)) {
                container.visibility = View.VISIBLE
                val split = goodsModel.tags!!.split(" ")
                for (index in split.indices) { //0...split.size-1
                    //0  ---3
                    val childCount = container.childCount
                    if (index > MAX_TAG_SIZE - 1) {
                        //倒叙
                        for (index in childCount - 1 downTo MAX_TAG_SIZE - 1) {
                            // itemLabelContainer childcount =5
                            // 3，后面的两个都需要被删除
                            container.removeViewAt(index)
                        }
                        break
                    }
                    //这里有个问题，有着一个服用的问题   5 ,4
                    //解决上下滑动复用的问题--重复创建的问题
                    val labelView: TextView = if (index > childCount - 1) {
                        val view = createLabelView(context, index != 0)
                        container.addView(view)
                        view
                    } else {
                        container.getChildAt(index) as TextView
                    }
                    labelView.text = split[index]
                }
            } else {
                container.visibility = View.GONE
            }
        }


        if (!hotTab) {
            val margin = HiDisplayUtil.dp2px(2F)
            val params = holder.itemView.layoutParams as RecyclerView.LayoutParams
            val parentLeft = hiAdapter?.getAttachRecyclerView()?.left ?: 0
            val parentPaddingLeft = hiAdapter?.getAttachRecyclerView()?.paddingLeft ?: 0
            val itemLeft = holder.itemView.left
            if (itemLeft == (parentLeft + parentPaddingLeft)) {
                //左边的item,设置右边的margin
                params.rightMargin = margin
            } else {
                //右边的item,设置左边的margin
                params.leftMargin = margin
            }

            holder.itemView.layoutParams = params

        }

    }

    /**
     * 默认：0，代表占满屏幕
     */
    override fun getSpanSize(): Int {
        return if (hotTab) super.getSpanSize() else 1
    }

    override fun getItemLayoutRes(): Int {
        return if (hotTab) R.layout.layout_home_goods_list_item1 else R.layout.layout_home_goods_list_item2
    }

    fun createLabelView(context: Context, withLeftMargin: Boolean): TextView {
        val labelView = TextView(context)
        labelView.setTextColor(ContextCompat.getColor(context, R.color.color_eed))
        labelView.textSize = 11f
        labelView.gravity = Gravity.CENTER
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, HiDisplayUtil.dp2px(14f))
        params.leftMargin = if (withLeftMargin) {
            HiDisplayUtil.dp2px(5f)
        } else 0
        labelView.layoutParams = params
        return labelView
    }

}