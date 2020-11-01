package com.vero.arch.tab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.vero.arch.R
import com.vero.arch.activity.BaseActivity
import com.vero.arch.databinding.ActivityHiTabTopDemoBinding
import com.vero.hiui.tab.top.HiTabTopInfo

class HiTabTopDemoActivity : BaseActivity<ActivityHiTabTopDemoBinding>() {
    val titles = listOf(
        "热门",
        "服装",
        "数码",
        "鞋子",
        "零食",
        "家电",
        "汽车",
        "百货",
        "家居",
        "装修",
        "运动"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTabTop()
    }

    private fun initTabTop() {
        val infoList = mutableListOf<HiTabTopInfo<*>>()
        val defaultColor = resources.getColor(R.color.tabBottomDefaultColor)
        val tintColor = resources.getColor(R.color.tabBottomDTintColor)
        titles.forEach {
            val info = HiTabTopInfo<Int>(it, defaultColor, tintColor)
            infoList.add(info)
        }

        mBinding.hiTabTopLayout.let {
            it.inflateInfo(infoList)
            it.addTabSelectedChangeListener { index, preInfo, nextInfo ->
                Toast.makeText(this@HiTabTopDemoActivity, nextInfo.name, Toast.LENGTH_SHORT).show()

            }
            it.defaultSelected(infoList[0])
        }

    }

    override fun getLayout(): Int {
        return R.layout.activity_hi_tab_top_demo
    }
}
