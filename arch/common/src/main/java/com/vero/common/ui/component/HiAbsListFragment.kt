package com.vero.common.ui.component

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vero.common.R
import com.vero.common.databinding.FragmentListBinding
import com.vero.common.ui.view.EmptyView
import com.vero.common.ui.view.HiRecyclerView
import com.vero.hiui.refresh.HiOverView
import com.vero.hiui.refresh.HiRefresh
import com.vero.hiui.refresh.HiRefreshLayout
import com.vero.hiui.refresh.overview.HiTextOverView
import org.devio.`as`.hi.hiitem.hiitem.HiAdapter
import org.devio.`as`.hi.hiitem.hiitem.HiDataItem

abstract class HiAbsListFragment : HiBaseFragment<FragmentListBinding>(), HiRefresh.HiRefreshListener {


    var pageIndex = 1
    private lateinit var hiAdapter: HiAdapter
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var refreshHeaderView: HiTextOverView? = null
    private var loadingView: ContentLoadingProgressBar? = null
    private var emptyView: EmptyView? = null
    private var recyclerView: HiRecyclerView? = null
    private var refreshLayout: HiRefreshLayout? = null


    companion object {
        const val PREFETCH_SIZE = 5
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_list
    }

    @CallSuper//这个注解表示子类不能覆盖这里的逻辑
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshLayout = mBinding.refreshLayout
        recyclerView = mBinding.recyclerView
        emptyView = mBinding.emptyView
        loadingView = mBinding.contentLoading

        refreshHeaderView = HiTextOverView(context ?: return)
        refreshLayout?.setRefreshOverView(refreshHeaderView)
        refreshLayout?.setRefreshListener(this)

        layoutManager = createLayoutManager()
        hiAdapter = HiAdapter(context ?: return)

        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = hiAdapter
        emptyView?.visibility = View.GONE
        emptyView?.setIcon(R.string.if_empty)
        emptyView?.setDesc(getString(R.string.list_empty_desc))
        emptyView?.setButton(getString(R.string.list_empty_action), View.OnClickListener {
            onRefresh()
        })
        loadingView?.visibility = View.VISIBLE
        pageIndex = 1
    }

    fun finishRefresh(dataItems: List<HiDataItem<*, out RecyclerView.ViewHolder>>?) {
        val success = !dataItems.isNullOrEmpty()
        //光这么判断还是不够的，我们还需要别的措施
        //因为在下拉刷新的时候，又执行了上拉分页
        val refresh = pageIndex == 1
        if (refresh) {
            loadingView?.visibility = View.VISIBLE
            refreshLayout?.refreshFinished()
            if (success) {
                emptyView?.visibility = View.GONE
                hiAdapter.clearItems()
                hiAdapter.addItems(dataItems ?: listOf(), true)
            } else {
                //此时需要判断列表是否已经有数据，没有则显示空页面状态
                if (hiAdapter.itemCount <= 0) {
                    emptyView?.visibility = View.VISIBLE
                }
            }
        } else {
            //上拉分页
            if (success) {
                hiAdapter.addItems(dataItems ?: listOf(), true)
            }
            recyclerView?.loadFinished(success)
        }
    }

    fun enableLoadMore(callback: () -> Unit) {
        //为了防止同时上拉和下拉刷新
        recyclerView?.enableLoadMore({
            if (refreshHeaderView?.state == HiOverView.HiRefreshState.STATE_REFRESH) {
                //正在下拉刷新
                recyclerView?.loadFinished(false)
                return@enableLoadMore
            }

            pageIndex++
            callback()
        }, PREFETCH_SIZE)
    }

    fun disableLoadMore(callback: () -> Unit) {
        //为了防止同时上拉和下拉刷新
        recyclerView?.disableLoadMore()
    }

    open fun createLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun enableRefresh(): Boolean {
        return true
    }

    @CallSuper//这个注解表示子类不能覆盖这里的逻辑
    override fun onRefresh() {
        //bug 场景：如果上拉记载时，快读返回，往下拉，松手：刷新的圈圈停住不动了
        //原因：立刻调用refreshFinished()时，refreshHeader的bottom的值超过了它的height,
        //refreshLayout#recver(dis) dis大于height,则恢复不到原来的初始位置，而是回到了下拉暂停的位置
        //解决：加一个post,等待下拉刷新松手的动作执行完，再refreshFinished
        if (recyclerView?.isLoading() == true) {
            //正在上拉加载
//            refreshLayout?.refreshFinished()
            refreshLayout?.post {
                refreshLayout?.refreshFinished()
            }
            return
        }
        pageIndex = 1
    }
}