package com.vero.aproject.biz

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.vero.aproject.R
import com.vero.aproject.databinding.ActivityLoginBinding
import com.vero.aproject.http.ApiFactory
import com.vero.aproject.http.api.AccountApi
import com.vero.aproject.route.RouteFlag
import com.vero.arch.activity.BaseActivity
import com.vero.common.utils.SPUtils
import com.vero.hilibrary.restful.HiCallback
import com.vero.hilibrary.restful.HiResponse
import java.lang.reflect.Proxy

@Route(path = "/account/login")
class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    private val REQUEST_CODE_REGISTRATION = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.actionLogin.setOnClickListener {
            goLogin()
        }
        mBinding.actionRegister.setOnClickListener {
            goRegistration()
        }

        mBinding.actionBack.setOnClickListener {
            onBackPressed()
        }

    }

    private fun goRegistration() {
        ARouter.getInstance().build("/account/registration")
                .navigation(this, REQUEST_CODE_REGISTRATION)
    }

    private fun goLogin() {
        val name = mBinding.inputItemUsername.getEditText().text
        val password = mBinding.inputItemPassword.getEditText().text
        if (name.isNullOrBlank() || password.isNullOrBlank()) {
            return
        }

        ApiFactory.create(AccountApi::class.java).login(name.toString(), password.toString())
                .enqueue(object : HiCallback<String> {

                    override fun onSuccess(response: HiResponse<String>) {
                        if (response.code == HiResponse.SUCCESS) {
                            showToast(getString(R.string.login_success))
                            val data = response.data
                            SPUtils.putString("boarding-pass", data!!)
                            setResult(Activity.RESULT_OK, Intent())
                            finish()
                        } else {
                            showToast(getString(R.string.login_failed) + response.msg)

                        }

                    }

                    override fun onFailed(throwable: Throwable) {
                        showToast(getString(R.string.login_failed) + throwable.message)
                    }

                })


    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null && requestCode == REQUEST_CODE_REGISTRATION) {
            //注册成功
            val username = data?.getStringExtra("username")
            if (!username.isNullOrBlank()) {
                mBinding.inputItemUsername.getEditText().setText(username)
            }

        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_login
    }
}