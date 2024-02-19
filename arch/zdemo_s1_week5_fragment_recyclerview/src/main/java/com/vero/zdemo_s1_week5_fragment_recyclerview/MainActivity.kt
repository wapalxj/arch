package com.vero.zdemo_s1_week5_fragment_recyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.vero.arch.activity.BaseActivity
import com.vero.zdemo_s1_week5_fragment_recyclerview.activity.LazyFragmentActivity
import com.vero.zdemo_s1_week5_fragment_recyclerview.activity.LazyVpFragmentActivity
import com.vero.zdemo_s1_week5_fragment_recyclerview.activity.MyFragmentActivity
import com.vero.zdemo_s1_week5_fragment_recyclerview.activity.RecyclerViewActivity
import com.vero.zdemo_s1_week5_fragment_recyclerview.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    fun toFragmentDemo(view: View) {
        startActivity(Intent(this, MyFragmentActivity::class.java))
    }

    fun btnLazyFragment(view: View) {
        startActivity(Intent(this, LazyFragmentActivity::class.java))

    }
    fun btnLazyFragmentV1(view: View) {
        startActivity(Intent(this, LazyVpFragmentActivity::class.java))

    }

    fun toRecyclerView(view: View) {
        startActivity(Intent(this, RecyclerViewActivity::class.java))
    }


}
