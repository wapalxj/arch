package com.vero.aproject.fragment

import android.os.Bundle
import android.util.SparseArray
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.vero.aproject.R
import com.vero.aproject.databinding.FragmentHomeBinding
import com.vero.aproject.fragment.home.HomeTabFragment
import com.vero.aproject.http.ApiFactory
import com.vero.aproject.http.api.HomeApi
import com.vero.aproject.model.TabCategory
import com.vero.common.ui.component.HiBaseFragment
import com.vero.hilibrary.restful.HiCallback
import com.vero.hilibrary.restful.HiResponse
import com.vero.hiui.tab.top.HiTabTopInfo

class HomePageFragment : HiBaseFragment<FragmentHomeBinding>() {

    private var topTabSelectIndex: Int = 0
    private var selectTabIndex: Int = 0

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        queryTabList()
    }

    private fun queryTabList() {
        ApiFactory.create(HomeApi::class.java)
                .queryTabList().enqueue(object : HiCallback<List<TabCategory>> {
                    override fun onSuccess(response: HiResponse<List<TabCategory>>) {
                        if (response.success() && response.data != null) {
                            updateUI(response.data!!)
                        }
                    }

                    override fun onFailed(throwable: Throwable) {

                    }

                })
    }

    private fun updateUI(data: List<TabCategory>) {
        //判断宿主是否存活
        //后续viewmodel+livedata就不需要判断了
        if (!isAlive()) return


        val topTabs = mutableListOf<HiTabTopInfo<Int>>()

        data?.forEachIndexed { index, tabCategory ->
            val defaultColor = ContextCompat.getColor(context!!, R.color.color_333)
            val selectedColor = ContextCompat.getColor(context!!, R.color.color_dd2)
            val tabTopInfo = HiTabTopInfo<Int>(tabCategory.categoryName, defaultColor, selectedColor)
            topTabs.add(tabTopInfo)
        }

        val topTabLayout = mBinding.topTabLayout
        topTabLayout.inflateInfo(topTabs as List<HiTabTopInfo<*>>)
        topTabLayout.defaultSelected(topTabs[selectTabIndex])
        topTabLayout.addTabSelectedChangeListener { index, preInfo, nextInfo ->
            //index：点击之后选中的
            if (mBinding.viewpager.currentItem != index) {
                mBinding.viewpager.setCurrentItem(index, false)
            }
        }
        mBinding.viewpager.adapter = HomePagerAdapter(childFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, data)
        mBinding.viewpager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                if (position != topTabSelectIndex) {
                    topTabLayout.defaultSelected(topTabs[position])
                    topTabSelectIndex = position
                }
            }
        })
    }

    inner class HomePagerAdapter(val fm: FragmentManager, val behavior: Int, val tabs: List<TabCategory>) : FragmentPagerAdapter(fm, behavior) {

        val fragments = SparseArray<Fragment>(tabs.size)
        override fun getItem(position: Int): Fragment {
            var fragment = fragments.get(position, null)
            if (fragment == null) {
                fragment = HomeTabFragment.newInstance(tabs[position].categoryId)
                fragments.put(position, fragment)
            }
            return fragment
        }

        override fun getCount(): Int {
            return fragments.size()
        }

    }
}