package com.vero.zdemo_s1_week5_fragment_recyclerview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.vero.arch.activity.BaseActivity
import com.vero.zdemo_s1_week5_fragment_recyclerview.R
import com.vero.zdemo_s1_week5_fragment_recyclerview.databinding.ActivityLazyFragmentBinding
import com.vero.zdemo_s1_week5_fragment_recyclerview.databinding.ActivityRecyclerViewBinding
import com.vero.zdemo_s1_week5_fragment_recyclerview.fragment.FirstFragment
import com.vero.zdemo_s1_week5_fragment_recyclerview.fragment.SecondFragment
import com.vero.zdemo_s1_week5_fragment_recyclerview.fragment.ThirdFragment

class RecyclerViewActivity : BaseActivity<ActivityRecyclerViewBinding>() {

    private val datas = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        for (i in 0..20){
            datas.add("${i*1000000}")
        }
        mBinding.recyclerView.let {
            val layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            it.adapter = MyAdapter()
            it.layoutManager =layoutManager

        }
        mBinding.recyclerView.adapter?.notifyDataSetChanged()

    }

    override fun getLayout(): Int {
        return R.layout.activity_recycler_view

    }

    inner class MyAdapter : RecyclerView.Adapter<MyAdapter.ViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_expandable_list_item_1, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            (holder.itemView as? TextView)?.text = datas[position]
        }

        override fun getItemCount(): Int {
            return datas.size
        }


        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        }
    }
}