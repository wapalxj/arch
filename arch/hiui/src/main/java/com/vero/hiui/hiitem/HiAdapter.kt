package org.devio.`as`.hi.hiitem.hiitem

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import java.lang.ref.WeakReference
import java.lang.reflect.ParameterizedType

/**
 * 通用数据适配器
 *
 *
 * bugfix:HiDataItem<*, out RecyclerView.ViewHolder>  都被改成了这样。否则会有类型转换问题
 */
class HiAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val recyclerViewRef: WeakReference<RecyclerView>? = null
    private var mContext: Context = context
    private var mInflater = LayoutInflater.from(context)
    private var dataSets = java.util.ArrayList<HiDataItem<*, out RecyclerView.ViewHolder>>()
    private var typeArrays = SparseArray<HiDataItem<*, out RecyclerView.ViewHolder>>()

    //添加header footer
    private var headers = SparseArray<View>()
    private var footers = SparseArray<View>()


    private var BASE_ITEM_HEADER = 100000
    private var BASE_ITEM_FOOTER = 200000

    fun addHeaderView(view: View) {
        if (headers.indexOfValue(view) < 0) {
            headers.put(BASE_ITEM_HEADER++, view)
            notifyItemInserted(headers.size() - 1)
        }
    }

    fun removeHeaderView(view: View) {
        val indexOfValue = headers.indexOfValue(view)
        if (indexOfValue < 0) {
            return
        }
        headers.removeAt(indexOfValue)
        notifyItemRemoved(indexOfValue)

    }

    fun addFooterView(view: View) {
        if (footers.indexOfValue(view) < 0) {
            footers.put(BASE_ITEM_FOOTER++, view)
            notifyItemInserted(itemCount)
        }
    }

    fun removeFooterView(view: View) {
        val indexOfValue = footers.indexOfValue(view)
        if (indexOfValue < 0) {
            return
        }
        footers.removeAt(indexOfValue)
        notifyItemRemoved(indexOfValue + getHeaderSize() + getOriginalItemSize())
    }

    fun getHeaderSize(): Int {
        return headers.size()
    }

    fun getFooterSize(): Int {
        return footers.size()
    }


    fun getOriginalItemSize(): Int {
        return dataSets.size
    }


    private fun isFooterPosition(position: Int): Boolean {
        return position >= getHeaderSize() + getOriginalItemSize()

    }

    private fun isHeaderPosition(position: Int): Boolean {
        return position < getHeaderSize()
    }

    /**
     *在指定为上添加HiDataItem
     */
    fun addItemAt(
            index: Int,
            dataItem: HiDataItem<*, out RecyclerView.ViewHolder>,
            notify: Boolean
    ) {
        if (index > 0) {
            dataSets.add(index, dataItem)
        } else {
            dataSets.add(dataItem)
        }

        val notifyPos = if (index > 0) index else dataSets.size - 1
        if (notify) {
            notifyItemInserted(notifyPos)
        }
    }

    /**
     * 往现有集合的尾部逐年items集合
     */
    fun addItems(items: List<HiDataItem<*, out RecyclerView.ViewHolder>>, notify: Boolean) {
        val start = dataSets.size
        for (item in items) {
            dataSets.add(item)
        }
        if (notify) {
            notifyItemRangeInserted(start, items.size)
        }
    }


    /**
     * 从指定位置上移除item
     */
    fun removeItemAt(index: Int): HiDataItem<*, out RecyclerView.ViewHolder>? {
        if (index > 0 && index < dataSets.size) {
            val remove = dataSets.removeAt(index)
            notifyItemRemoved(index)
            return remove
        } else {
            return null
        }
    }


    /**
     * 移除指定item
     */
    fun removeItem(dataItem: HiDataItem<*, out RecyclerView.ViewHolder>) {
        val index: Int = dataSets.indexOf(dataItem);
        removeItemAt(index)
    }

    /**
     * 指定刷新 某个item的数据
     */
    fun refreshItem(dataItem: HiDataItem<*, out RecyclerView.ViewHolder>) {
        val indexOf = dataSets.indexOf(dataItem)
        notifyItemChanged(indexOf)
    }


    /**
     * 以每种item类型的class.hashcode为 该item的viewType
     *
     * 这里把type存储起来，是为了onCreateViewHolder方法能够为不同类型的item创建不同的viewholder
     */
    override fun getItemViewType(position: Int): Int {
        //处理header
        if (isHeaderPosition(position)) {
            return headers.keyAt(position)
        }
        //处理footer
        if (isFooterPosition(position)) {
            //计算一下footer位置
            val footerPosition = position - getHeaderSize() - getOriginalItemSize()
            return footers.keyAt(footerPosition)
        }
        //处理普通Item
        val itemPosition = position - getHeaderSize()
        val dataItem = dataSets[itemPosition]
        val type = dataItem.javaClass.hashCode()
        //如果还没有包含这种类型的item，则添加进来
        if (typeArrays.indexOfKey(type) < 0) {
            typeArrays.put(type, dataItem)
        }
        return type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //处理Header
        if (headers.indexOfKey(viewType) >= 0) {
            val headerView = headers[viewType]
            return object : RecyclerView.ViewHolder(headerView) {}
        }

        //处理Header
        if (footers.indexOfKey(viewType) >= 0) {
            val footerView = footers[viewType]
            return object : RecyclerView.ViewHolder(footerView) {}
        }

        //处理普通item
        val dataItem = typeArrays.get(viewType)
        var view: View? = dataItem.getItemView(parent)
        if (view == null) {
            val layoutRes = dataItem.getItemLayoutRes()
            if (layoutRes < 0) {
                RuntimeException("dataItem:" + dataItem.javaClass.name + " must override getItemView or getItemLayoutRes")
            }
            view = mInflater.inflate(layoutRes, parent, false)
        }
        return createViewHolderInternal(dataItem.javaClass, view!!)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //处理header/footer
        if (isHeaderPosition(position) || isFooterPosition(position)) {
            return
        }
        val itemPosition = position - getHeaderSize()
        val dataItem = getItem(itemPosition)
        dataItem?.onBindData(holder, itemPosition)
    }

    private fun createViewHolderInternal(
            javaClass: Class<HiDataItem<*, out RecyclerView.ViewHolder>>,
            view: View
    ): RecyclerView.ViewHolder {
        //得到该Item的父类类型,即为HiDataItem.class。  class 也是type的一个子类。
        //type的子类常见的有 class，类泛型,ParameterizedType参数泛型 ，TypeVariable字段泛型
        //所以进一步判断它是不是参数泛型
        val superclass = javaClass.genericSuperclass;
        if (superclass is ParameterizedType) {
            //得到它携带的泛型参数的数组
            val arguments = superclass.actualTypeArguments;
            //挨个遍历判断 是不是咱们想要的 RecyclerView.ViewHolder 类型的。
            for (argument in arguments) {
                if (argument is Class<*> && RecyclerView.ViewHolder::class.java.isAssignableFrom(
                                argument
                        )
                ) {
                    try {
                        //如果是则使用反射 实例化类上标记的实际的泛型对象
                        //这里需要  try-catch 一把，如果咱们直接在HiDataItem子类上标记 RecyclerView.ViewHolder，抽象类是不允许反射的
                        return argument.getConstructor(View::class.java).newInstance(view) as RecyclerView.ViewHolder
                    } catch (e: Throwable) {
                        e.printStackTrace()

                    }
                }
            }
        }
        return object : RecyclerView.ViewHolder(view) {}
    }

    override fun getItemCount(): Int {
        return dataSets.size + getHeaderSize() + getFooterSize()
    }


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        /**
         * 为列表上的item 适配网格布局
         */
        val layoutManager = recyclerView.layoutManager;
        if (layoutManager is GridLayoutManager) {
            val spanCount = layoutManager.spanCount
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    if (isHeaderPosition(position) || isFooterPosition(position)) {
                        return spanCount
                    }
                    val itemPosition = position - getHeaderSize()
                    if (itemPosition < dataSets.size) {
                        val dataItem = getItem(itemPosition)
                        if (dataItem != null) {
                            val spanSize = dataItem.getSpanSize()
                            return if (spanSize <= 0) spanCount else spanSize
                        }
                    }
                    return spanCount
                }
            }
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        if (recyclerViewRef != null) {
            recyclerViewRef.clear()
        }
    }

    fun getAttachRecyclerView(): RecyclerView? {
        return if (recyclerViewRef != null) recyclerViewRef.get() else null
    }

    fun getItem(position: Int): HiDataItem<*, RecyclerView.ViewHolder>? {
        if (position < 0 || position >= dataSets.size)
            return null
        return dataSets[position] as HiDataItem<*, RecyclerView.ViewHolder>
    }


    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        val recyclerView = getAttachRecyclerView()
        if (recyclerView != null) {
            //瀑布流的item占比适配
            val position = recyclerView.getChildAdapterPosition(holder.itemView)

            val isHeaderFooter = isHeaderPosition(position) || isFooterPosition(position)
            val itemPosition = position - getHeaderSize()

            val dataItem = getItem(itemPosition)
            if (dataItem == null) return
            val lp = holder.itemView.layoutParams
            if (lp != null && lp is StaggeredGridLayoutManager.LayoutParams) {
                val manager = recyclerView.layoutManager as StaggeredGridLayoutManager?
                if (isHeaderFooter) {
                    //处理header/footer
                    lp.isFullSpan = true
                    return
                }
                val spanSize = dataItem.getSpanSize()
                if (spanSize == manager!!.spanCount) {
                    lp.isFullSpan = true
                }
            }

            dataItem.onViewAttachedToWindow(holder)
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        var position = holder.adapterPosition
        if (isHeaderPosition(position) || isFooterPosition(position)) {
            return
        }
        val itemPosition = position - getHeaderSize()
        val dataItem = getItem(itemPosition)
        dataItem?.onViewDetachedFromWindow(holder)
    }

    fun clearItems() {
        dataSets.clear()
        notifyDataSetChanged()
    }
}