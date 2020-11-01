package com.vero.zdemo_s1_week5_fragment_recyclerview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vero.arch.activity.BaseActivity
import com.vero.zdemo_s1_week5_fragment_recyclerview.R
import com.vero.zdemo_s1_week5_fragment_recyclerview.databinding.ActivityMyFragmentBinding
import com.vero.zdemo_s1_week5_fragment_recyclerview.fragment.FirstFragment

class MyFragmentActivity : BaseActivity<ActivityMyFragmentBinding>() {
    private val FIRST_FRAGMENT = "first_fragment"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val firstFragment = FirstFragment()
//        //恢复导致重叠
//        supportFragmentManager.beginTransaction()
//            .add(R.id.container, firstFragment)
//            .commitNow()

        //解决重叠
        val fragment = supportFragmentManager.findFragmentByTag(FIRST_FRAGMENT)
        if (fragment == null) {
            val firstFragment = FirstFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.container, firstFragment, FIRST_FRAGMENT)
                .commitNow()
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_my_fragment

    }
}