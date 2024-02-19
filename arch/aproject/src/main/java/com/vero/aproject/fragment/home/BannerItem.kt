package com.vero.aproject.fragment.home

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.vero.aproject.model.HomeBanner
import com.vero.aproject.route.HiRoute
import com.vero.common.ui.view.loadUrl
import com.vero.hilibrary.util.HiDisplayUtil
import com.vero.hiui.banner.HiBanner
import com.vero.hiui.banner.core.HiBannerMo
import org.devio.`as`.hi.hiitem.hiitem.HiDataItem
import retrofit2.http.PATCH

class BannerItem(val list: List<HomeBanner>) : HiDataItem<List<HomeBanner>, RecyclerView.ViewHolder>(list) {

    override fun onBindData(holder: RecyclerView.ViewHolder, position: Int) {
        val banner = holder.itemView as HiBanner
        val models = list.map {
            val banner = object : HiBannerMo() {}
            banner.url = it.url
            banner
        }
        banner.setBannerData(models)
        banner.setOnBannerClickListener { viewHolder, hiBannerMo, position ->
            HiRoute.startActivityForBrowser(list[position].url)
        }

        banner.setBindAdapter { viewHolder, mo, position ->
            (viewHolder.rootView as? ImageView)?.loadUrl(mo.url)
        }

    }


    override fun getItemView(parent: ViewGroup): View? {
        val context = parent.context
        val banner = HiBanner(context)
        val params = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, HiDisplayUtil.dp2px(160f))
        params.bottomMargin = HiDisplayUtil.dp2px(10f)
        banner.layoutParams = params
        banner.setBackgroundColor(Color.WHITE)
        return banner

    }


}