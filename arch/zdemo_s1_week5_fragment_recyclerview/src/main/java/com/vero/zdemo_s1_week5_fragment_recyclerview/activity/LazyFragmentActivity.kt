package com.vero.zdemo_s1_week5_fragment_recyclerview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.vero.arch.activity.BaseActivity
import com.vero.zdemo_s1_week5_fragment_recyclerview.R
import com.vero.zdemo_s1_week5_fragment_recyclerview.databinding.ActivityLazyFragmentBinding
import com.vero.zdemo_s1_week5_fragment_recyclerview.databinding.ActivityMyFragmentBinding
import com.vero.zdemo_s1_week5_fragment_recyclerview.fragment.FirstFragment
import com.vero.zdemo_s1_week5_fragment_recyclerview.fragment.SecondFragment
import com.vero.zdemo_s1_week5_fragment_recyclerview.fragment.ThirdFragment


/**
 * 懒加载
 */
class LazyFragmentActivity : BaseActivity<ActivityLazyFragmentBinding>() {

    private val fragments = mutableListOf<Fragment>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragments.add(FirstFragment())
        fragments.add(SecondFragment())
        fragments.add(ThirdFragment())
        mBinding.viewPager.adapter = MyAdapter()
        mBinding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

    }

    override fun getLayout(): Int {
        return R.layout.activity_lazy_fragment

    }

    inner class MyAdapter : FragmentStateAdapter(this) {
        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }

    }
}