package com.vero.aproject.biz

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.vero.aproject.R
import com.vero.aproject.databinding.ActivityLoginBinding
import com.vero.aproject.databinding.ActivityRegisterBinding
import com.vero.aproject.http.ApiFactory
import com.vero.aproject.http.api.AccountApi
import com.vero.aproject.route.RouteFlag
import com.vero.arch.activity.BaseActivity
import com.vero.common.utils.SPUtils
import com.vero.hilibrary.restful.HiCallback
import com.vero.hilibrary.restful.HiResponse

@Route(path = "/account/registration")
class RegistrationActivity : BaseActivity<ActivityRegisterBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.actionSubmit.setOnClickListener {
            submit()
        }
        mBinding.actionBack.setOnClickListener {
            onBackPressed()
        }

    }

    private fun submit() {
        val orderID = mBinding.inputItemOrderId.getEditText().text.toString()
        val moocId = mBinding.inputItemMoocId.getEditText().text.toString()
        val username = mBinding.inputItemUsername.getEditText().text.toString()
        val pwd = mBinding.inputItemPassword.getEditText().text.toString()
        val pwdSec = mBinding.inputItemPasswordCheck.getEditText().text.toString()

        if (orderID.isNullOrBlank()
                || moocId.isNullOrBlank()
                || username.isNullOrBlank()
                || pwd.isNullOrBlank()
                || (pwd != pwdSec)) {
            return
        }

        ApiFactory.create(AccountApi::class.java).register(username, pwd,moocId,orderID)
                .enqueue(object : HiCallback<String> {
                    override fun onSuccess(response: HiResponse<String>) {
                        if (response.code == HiResponse.SUCCESS) {
                            showToast(getString(R.string.register_success))
                            val intent =Intent()
                            intent.putExtra("username",username)
                            setResult(Activity.RESULT_OK,intent)
                            finish()
                        } else {
                            showToast(getString(R.string.register_failed) + response.msg)

                        }

                    }

                    override fun onFailed(throwable: Throwable) {
                        showToast(getString(R.string.register_failed) + throwable.message)
                    }

                })
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

    override fun getLayout(): Int {
        return R.layout.activity_register
    }
}