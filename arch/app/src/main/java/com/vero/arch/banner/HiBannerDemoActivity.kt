package com.vero.arch.banner

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.vero.arch.R
import com.vero.arch.activity.BaseActivity
import com.vero.arch.databinding.ActivityHiBannerDemoBinding
import com.vero.hiui.banner.core.HiBannerMo
import com.vero.hiui.banner.indicator.HiCircleIndicator
import com.vero.hiui.banner.indicator.IHiIndicator

class HiBannerDemoActivity : BaseActivity<ActivityHiBannerDemoBinding>() {
    private var urls = arrayOf(
        "https://www.devio.org/img/beauty_camera/beauty_camera1.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera3.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera4.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera5.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera2.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera6.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera7.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera8.jpeg"
    )
    private var autoPlay: Boolean = false

    private var hiIndicator: IHiIndicator<*>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView(hiIndicator, false)

        mBinding.autoPlay.setOnCheckedChangeListener { buttonView, isChecked ->
            autoPlay = isChecked
            initView(hiIndicator, autoPlay)
        }
        mBinding.tvSwitch.setOnClickListener {
            if (hiIndicator is HiCircleIndicator) {

            } else {
                initView(HiCircleIndicator(this), autoPlay)
            }

        }
    }

    private fun initView(
        indicator: IHiIndicator<*>?, autoPlay: Boolean
    ) {
        val moList = mutableListOf<HiBannerMo>()
        for (i in 0..7) {
            val mo = BannerMo(urls[i % urls.size])
            moList.add(mo)
        }

        mBinding.banner.let {
            it.setHiIndicator(indicator)
            it.setAutoPlay(autoPlay)
            it.setIntervalTime(2000)
            it.setBannerData(R.layout.banner_item_layout, moList)
            it.setBindAdapter { viewHolder, mo, position ->
                val iv = viewHolder.findViewById<ImageView>(R.id.iv_image)
                Glide.with(this@HiBannerDemoActivity)
                    .load(mo.url)
                    .into(iv)
                val titleView = viewHolder.findViewById<TextView>(R.id.tv_title)
                titleView.text = mo.url
                Log.e("-----position:", position.toString() + "  url:" + mo.url)
            }
        }

    }

    override fun getLayout(): Int {
        return R.layout.activity_hi_banner_demo
    }
}