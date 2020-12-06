package com.vero.aproject

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.vero.aproject.databinding.ActivityMainBinding
import com.vero.aproject.logic.MainActivityLogic
import com.vero.arch.activity.BaseActivity
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<ActivityMainBinding>(), MainActivityLogic.ActivityProvider {
    private var activityLogic: MainActivityLogic? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLogic = MainActivityLogic(this,savedInstanceState,mBinding)
    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        activityLogic?.onSaveInstanceState(outState)
    }
}
