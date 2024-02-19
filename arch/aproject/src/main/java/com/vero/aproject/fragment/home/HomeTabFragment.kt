package com.vero.aproject.fragment.home

import com.vero.aproject.R
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vero.aproject.http.ApiFactory
import com.vero.aproject.http.api.HomeApi
import com.vero.aproject.model.HomeModel
import com.vero.common.ui.component.HiAbsListFragment
import com.vero.hilibrary.restful.HiCallback
import com.vero.hilibrary.restful.HiResponse
import org.devio.`as`.hi.hiitem.hiitem.HiDataItem

class HomeTabFragment : HiAbsListFragment() {
    private var categoryId: String? = null
    val DEFAULT_HOT_TAB_CATEGORY = "1"

    companion object {
        fun newInstance(categoryId: String): HomeTabFragment {
            val args = Bundle()
            args.putString("categoryId", categoryId)
            val fragment = HomeTabFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_profile
    }

    override fun createLayoutManager(): RecyclerView.LayoutManager {
        val isHotTab=categoryId == DEFAULT_HOT_TAB_CATEGORY
        return if (isHotTab) {
            super.createLayoutManager()
        } else {
            GridLayoutManager(context, 2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        categoryId = arguments?.getString("categoryId", DEFAULT_HOT_TAB_CATEGORY)
        super.onViewCreated(view, savedInstanceState)
        queryTabCategoryList()

        //上拉
        enableLoadMore {
            queryTabCategoryList()
        }
    }

    override fun onRefresh() {
        super.onRefresh()
        queryTabCategoryList()
    }

    private fun queryTabCategoryList() {
        ApiFactory.create(HomeApi::class.java).queryTabCategoryList(categoryId!!, pageIndex, 10)
                .enqueue(object : HiCallback<HomeModel> {
                    override fun onSuccess(response: HiResponse<HomeModel>) {
                        if (response.success() && response.data != null) {
                            updateUI(response.data!!)
                        } else {
                            finishRefresh(null)
                        }

                    }

                    override fun onFailed(throwable: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
    }

    private fun updateUI(data: HomeModel) {
        if (!isAlive()) {
            return
        }

        val dataItems = mutableListOf<HiDataItem<*, *>>()

        data.bannerList?.let {
            dataItems.add(BannerItem(it))
        }
        data.subcategoryList?.let {
            dataItems.add(GridItem(it))
        }
        data.goodsList?.forEach {
            dataItems.add(GoodsItem(it, categoryId == DEFAULT_HOT_TAB_CATEGORY))
        }

        finishRefresh(dataItems)
    }
}