package com.vero.zdemo_s1_week5_fragment_recyclerview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vero.arch.activity.BaseActivity
import com.vero.zdemo_s1_week5_fragment_recyclerview.R
import com.vero.zdemo_s1_week5_fragment_recyclerview.databinding.ActivityLazyFragmentBinding
import com.vero.zdemo_s1_week5_fragment_recyclerview.databinding.ActivityLazyFragmentVp1Binding
import com.vero.zdemo_s1_week5_fragment_recyclerview.databinding.ActivityMyFragmentBinding
import com.vero.zdemo_s1_week5_fragment_recyclerview.fragment.FirstFragment
import com.vero.zdemo_s1_week5_fragment_recyclerview.fragment.SecondFragment
import com.vero.zdemo_s1_week5_fragment_recyclerview.fragment.ThirdFragment


/**
 * 懒加载 vp2 天生懒加载
 */
class LazyVpFragmentActivity : BaseActivity<ActivityLazyFragmentVp1Binding>() {

    private val fragments = mutableListOf<Fragment>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragments.add(FirstFragment())
        fragments.add(SecondFragment())
        fragments.add(ThirdFragment())
        fragments.add(ThirdFragment())
        fragments.add(ThirdFragment())
        fragments.add(ThirdFragment())
        mBinding.viewPager.adapter = MyAdapter()

    }

    override fun getLayout(): Int {
        return R.layout.activity_lazy_fragment_vp1

    }

    inner class MyAdapter : FragmentPagerAdapter(this.supportFragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }


    }
}