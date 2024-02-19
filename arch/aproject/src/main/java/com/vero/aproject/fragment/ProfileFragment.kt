package com.vero.aproject.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import com.vero.common.ui.component.HiBaseFragment
import com.vero.aproject.R
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.alibaba.android.arouter.launcher.ARouter
import com.vero.aproject.databinding.FragmentProfileBinding
import com.vero.aproject.http.ApiFactory
import com.vero.aproject.http.api.AccountApi
import com.vero.aproject.model.CourseNotice
import com.vero.aproject.model.Notice
import com.vero.aproject.model.UserProfile
import com.vero.aproject.route.HiRoute
import com.vero.common.ui.view.loadCircle
import com.vero.common.ui.view.loadCorner
import com.vero.hilibrary.restful.HiCallback
import com.vero.hilibrary.restful.HiResponse
import com.vero.hilibrary.util.HiDisplayUtil
import com.vero.hiui.banner.core.HiBannerMo

class ProfileFragment : HiBaseFragment<FragmentProfileBinding>() {

    companion object {
        val REQUEST_CODE_LOGIN_PROFILE = 1001
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_profile
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //底部 课程公告
        mBinding.itemCourse.setText(R.string.if_notify)
        mBinding.itemCourse.append("    " + getString(R.string.item_notify))

        mBinding.itemCollection.setText(R.string.if_collection)
        mBinding.itemCollection.append("    " + getString(R.string.item_collection))

        mBinding.itemAddress.setText(R.string.if_address)
        mBinding.itemAddress.append("    " + getString(R.string.item_address))


        mBinding.itemHistory.setText(R.string.if_history)
        mBinding.itemHistory.append("    " + getString(R.string.item_history))

        queryLoginUserData()
        queryCourseNotice()
    }

    private fun queryCourseNotice() {

        ApiFactory.create(AccountApi::class.java).notice().enqueue(object : HiCallback<CourseNotice> {
            override fun onSuccess(response: HiResponse<CourseNotice>) {
                val courseNotice = response.data
                if (response.code == HiResponse.SUCCESS && courseNotice != null && courseNotice.total > 0) {
                    mBinding.notifiCount.text = courseNotice.total.toString()
                    mBinding.notifiCount.visibility = View.VISIBLE

                } else {
                    showToast(response.msg)
                }

            }

            override fun onFailed(throwable: Throwable) {
                showToast(throwable.message)
            }
        })
    }

    private fun queryLoginUserData() {
        ApiFactory.create(AccountApi::class.java).profile().enqueue(object : HiCallback<UserProfile> {
            override fun onSuccess(response: HiResponse<UserProfile>) {
                val profile = response.data
                if (response.code == HiResponse.SUCCESS && profile != null) {
                    updateUI(profile)
                } else {
                    showToast(response.msg)
                }

            }

            override fun onFailed(throwable: Throwable) {
                showToast(throwable.message)
            }
        })

    }

    private fun updateUI(profile: UserProfile) {

        mBinding.userName.text = if (profile.isLogin) {
            profile.userName
        } else {
            getString(R.string.profile_not_login)
        }

        mBinding.loginDesc.text = if (profile.isLogin) {
            getString(R.string.profile_login_desc_welcome_back)
        } else {
            getString(R.string.profile_not_login)
        }
        if (profile.isLogin && profile.avatar!=null) {
            mBinding.userAvatar.loadCircle(profile.avatar)
        } else {
            mBinding.userAvatar.setImageResource(R.mipmap.ic_launcher_round)
            mBinding.userName.setOnClickListener {
                ARouter.getInstance()
                        .build("/account/login")
                        .navigation(activity, REQUEST_CODE_LOGIN_PROFILE)
            }

        }




        mBinding.tabItemCollection.text = spannableTabItem(profile.favoriteCount, getString(R.string.profile_tab_item_collection))
        mBinding.tabItemHistory.text = spannableTabItem(profile.browseCount, getString(R.string.profile_tab_item_history))
        mBinding.tabItemLearn.text = spannableTabItem(profile.learnMinutes, getString(R.string.profile_tab_item_learn))

        updateBanner(profile.bannerNoticeList)
    }

    private fun updateBanner(bannerNoticeList: List<Notice>?) {
        if (bannerNoticeList.isNullOrEmpty()) {
            return
        }
        val models = bannerNoticeList.map {
            object : HiBannerMo() {}.apply {
                url = it.cover
            }
        }
        mBinding.hiBanner.setBannerData(R.layout.layout_profile_banner_item, models)
        mBinding.hiBanner.setBindAdapter { viewHolder, model, position ->
            viewHolder?.let {
                val imageView = it.findViewById<ImageView>(R.id.banner_item_iv)
                imageView.loadCorner(model.url, HiDisplayUtil.dp2px(10f, resources))
            }

        }
        mBinding.hiBanner.setOnBannerClickListener { viewHolder, hiBannerMo, position ->
            HiRoute.startActivityForBrowser(bannerNoticeList[position].url)
        }
        mBinding.hiBanner.visibility = View.VISIBLE

    }

    private fun spannableTabItem(topText: Int, bottomText: String): CharSequence? {
        val spanStr = topText.toString()
        val ssb = SpannableStringBuilder()
        val ssTop = SpannableStringBuilder(spanStr)

        ssTop.setSpan(ForegroundColorSpan(resources.getColor(R.color.color_000)),
                0,
                ssTop.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        ssTop.setSpan(AbsoluteSizeSpan(18, true), 0, ssTop.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        ssTop.setSpan(StyleSpan(Typeface.BOLD), 0, ssTop.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        ssb.append(ssTop).append(bottomText)
        return ssb
    }

    private fun showToast(msg: String?) {
        msg?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_LOGIN_PROFILE && requestCode == Activity.RESULT_OK && data != null) {
            queryLoginUserData()
        }
    }
}