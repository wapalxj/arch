package com.vero.arch.tab

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.vero.arch.R
import com.vero.arch.activity.BaseActivity
import com.vero.arch.databinding.ActivityHiTabBottomDemoBinding
import com.vero.arch.databinding.ActivityMainBinding
import com.vero.hilibrary.util.HiDisplayUtil
import com.vero.hiui.tab.bottom.HiTabBottomInfo
import kotlinx.android.synthetic.main.activity_hi_tab_bottom_demo.*

class HiTabBottomDemoActivity : BaseActivity<ActivityHiTabBottomDemoBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTabBottom()
    }

    private fun initTabBottom() {
        mBinding.hiTablayout.setTabAlpha(0.85f)

        val infos: MutableList<HiTabBottomInfo<*>> = mutableListOf()
        val homeInfo = HiTabBottomInfo(
            "首页",
            "fonts/iconfont.ttf",
            getString(R.string.if_home),
            null,
            "#ff656667",
            "#ffd44949"
        )
        val favoriteInfo = HiTabBottomInfo(
            "收藏",
            "fonts/iconfont.ttf",
            getString(R.string.if_favorite),
            null,
            "#ff656667",
            "#ffd44949"
        )
//        val categoryInfo = HiTabBottomInfo(
//            "分类",
//            "fonts/iconfont.ttf",
//            getString(R.string.if_category),
//            null,
//            "#ff656667",
//            "#ffd44949"
//        )
        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.fire, null)
        val categoryInfo = HiTabBottomInfo<String>(
            "分类",
            bitmap,
            bitmap
        )

        val recommendInfo = HiTabBottomInfo(
            "推荐",
            "fonts/iconfont.ttf",
            getString(R.string.if_recommend),
            null,
            "#ff656667",
            "#ffd44949"
        )
        val profileInfo = HiTabBottomInfo(
            "我的",
            "fonts/iconfont.ttf",
            getString(R.string.if_profile),
            null,
            "#ff656667",
            "#ffd44949"
        )
        infos.add(homeInfo)
        infos.add(favoriteInfo)
        infos.add(categoryInfo)
        infos.add(recommendInfo)
        infos.add(profileInfo)
        mBinding.hiTablayout.inflateInfo(infos)
        mBinding.hiTablayout.addTabSelectedChangeListener { _, _, nextInfo ->
            Toast.makeText(this@HiTabBottomDemoActivity, nextInfo.name, Toast.LENGTH_SHORT).show()
        }
        mBinding.hiTablayout.defaultSelected(homeInfo)


        //改变某个tab高度
        val tabBottom = mBinding.hiTablayout.findTab(categoryInfo)
        tabBottom?.apply {
            resetHeight(HiDisplayUtil.dp2px(66f, resources))
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_hi_tab_bottom_demo
    }
}
