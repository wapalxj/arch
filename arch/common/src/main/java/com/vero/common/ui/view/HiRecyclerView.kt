package com.vero.common.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.vero.common.R
import com.vero.hilibrary.log.HiLog
import org.devio.`as`.hi.hiitem.hiitem.HiAdapter

/**
 * HiRecyclerView
 */
class HiRecyclerView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private var loadMoreListener: LoadMoreScrollListener? = null
    private var footerView: View? = null
    private var isLoadingMore = false

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_empty_view, this, true)

    }

    inner class LoadMoreScrollListener(val prefetchSize: Int, val callback: () -> Unit) : OnScrollListener() {

        val hiAdapter = adapter as HiAdapter
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            //需要根据当前的滑动状态 决定要不要添加footer view,要不要执行上拉动作
            if (isLoadingMore) {
                return
            }
            //判断当前列表上已经显示的item数量
            //已显示的数量小于0，不触发分页
            val totalItemCount = hiAdapter.itemCount
            if (totalItemCount <= 0) {
                return
            }

            //滑动状态为拖动状态时，就要判断要不要添加footer
            //而不是在滑动停止了，才去添加footer

            //目的，防止列表滑动带底部，但是footer没有显示出来
            //1.canScrollVertical:依旧需要判断列表是否能够向下(从下往上)滑动

            val canScrollVertical = recyclerView.canScrollVertically(1)


            //特殊情况：列表已经滑动到底部，但是分页失败,拖动也需要显示loadingfooter
            // arriveBottom=当前最后一个显示的item和totalCount比较

            val lastVisibleItem = findLastVisibleItem(recyclerView)
            if (lastVisibleItem <= 0) {
                return
            }
            val arriveBottom = lastVisibleItem >= totalItemCount - 1

            if (newState == RecyclerView.SCROLL_STATE_DRAGGING && (canScrollVertical || arriveBottom)) {
                addFooterView()
            }


            if (newState != RecyclerView.SCROLL_STATE_IDLE) {
                return
            }

            //预加载:不需要等到滑动最后一个item时才加载下一页
            val arrivePrefetchPosition = totalItemCount - lastVisibleItem <= prefetchSize
            if (!arrivePrefetchPosition) {
                return
            }
            //开始loadMore
            isLoadingMore = true
            callback()

        }

        private fun addFooterView() {
            val footerView = getFooterView()
            //坑1：边界场景，会出现多次添加：不断重复的多次上拉
            //添加之前先remove
            //坑2：rv的notifyItem是会有延迟的,需要判断footerView?.parent!=null
            if (footerView.parent != null) {
                footerView.post {
                    addFooterView()
                }
            } else {
                hiAdapter.addFooterView(footerView)
            }

        }

        private fun getFooterView(): View {
            if (footerView == null) {
                footerView = LayoutInflater.from(context).inflate(R.layout.layout_footer_loading, this@HiRecyclerView, false)
            }
            return footerView!!
        }

        private fun findLastVisibleItem(recyclerView: RecyclerView): Int {
            return when (val layoutManager = recyclerView.layoutManager) {
                is LinearLayoutManager -> {
                    layoutManager.findLastVisibleItemPosition()
                }
                is StaggeredGridLayoutManager -> {
                    layoutManager.findLastVisibleItemPositions(null)[0]
                }
                else -> {
                    -1
                }
            }
        }
    }

    fun enableLoadMore(callback: () -> Unit, prefetchSize: Int) {
        if (adapter !is HiAdapter) {
            HiLog.e("enableLoadMore must use HiAdapter")
            return
        }
        loadMoreListener = LoadMoreScrollListener(prefetchSize, callback)
        loadMoreListener?.let {
            addOnScrollListener(it)
        }
    }

    fun disableLoadMore() {
        if (adapter !is HiAdapter) {
            HiLog.e("enableLoadMore must use HiAdapter")
            return
        }
        val hiAdapter = adapter as HiAdapter
        footerView?.let {
            if (it.parent != null) {
                hiAdapter.removeFooterView(it)
            }
        }

        loadMoreListener?.let {
            removeOnScrollListener(it)
        }
        loadMoreListener = null
        footerView = null
        isLoadingMore = false
    }

    fun isLoading(): Boolean {
        return isLoadingMore
    }

    fun loadFinished(success: Boolean) {
        if (adapter !is HiAdapter) {
            HiLog.e("enableLoadMore must use HiAdapter")
            return
        }
        isLoadingMore = false

        val hiAdapter = adapter as HiAdapter
        if (!success) {
            footerView?.let {
                if (it.parent != null) {
                    hiAdapter.removeFooterView(it)
                }
            }
        }else{
            //什么都不用做，因为footerView被挤下去了
        }

    }

}


