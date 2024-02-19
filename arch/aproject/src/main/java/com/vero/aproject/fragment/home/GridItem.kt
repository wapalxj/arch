package com.vero.aproject.fragment.home

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vero.aproject.R
import com.vero.aproject.model.HomeBanner
import com.vero.aproject.model.Subcategory
import com.vero.common.ui.view.loadUrl
import com.vero.hilibrary.util.HiDisplayUtil
import kotlinx.android.synthetic.main.layout_home_op_grid_item.view.*
import org.devio.`as`.hi.hiitem.hiitem.HiDataItem

class GridItem(val list: List<Subcategory>) : HiDataItem<List<Subcategory>, RecyclerView.ViewHolder>(list) {

    override fun onBindData(holder: RecyclerView.ViewHolder, position: Int) {
        (holder.itemView as RecyclerView).adapter = GridAdapter(holder.itemView.context, list)
    }


    override fun getItemView(parent: ViewGroup): View? {
        val gridView = RecyclerView(parent.context)
        val params = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT)
        params.bottomMargin = HiDisplayUtil.dp2px(10f)

        gridView.layoutManager = GridLayoutManager(parent.context, 5)
        gridView.layoutParams = params
        gridView.setBackgroundColor(Color.WHITE)
        return gridView
    }

    inner class GridAdapter(val context: Context, val list: List<Subcategory>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private var inflater: LayoutInflater = LayoutInflater.from(context)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.layout_home_op_grid_item, parent, false)
            return object : RecyclerView.ViewHolder(view) {

            }
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val subcategory = list[position]
            holder.itemView.item_image.loadUrl(subcategory.subcategoryIcon)
            holder.itemView.item_title.text = subcategory.subcategoryName
            holder.itemView.setOnClickListener {
                Toast.makeText(context, "you touch me", Toast.LENGTH_SHORT)
            }


        }

    }

}