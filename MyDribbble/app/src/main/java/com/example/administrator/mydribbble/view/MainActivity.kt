package com.example.administrator.mydribbble.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import com.example.administrator.mydribbble.R
import com.example.administrator.mydribbble.entity.Token
import com.example.administrator.mydribbble.entity.User
import com.example.administrator.mydribbble.presenter.MainPresenter
import com.example.administrator.mydribbble.tools.singleData
import com.example.administrator.mydribbble.view.activity.BaseActivity
import com.example.administrator.mydribbble.view.api.IMainView
import com.example.administrator.mydribbble.view.fragment.BunketsFragment
import com.example.administrator.mydribbble.view.fragment.ExploreFragment
import com.example.administrator.mydribbble.view.fragment.HomeFragment
import com.example.administrator.mydribbble.view.fragment.LikeFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header.*
import org.greenrobot.eventbus.EventBus

class MainActivity : BaseActivity(), IMainView, NavigationView.OnNavigationItemSelectedListener {
    private var mHomeFragment: HomeFragment? = null
    private var mExploreFragment: ExploreFragment? = null
    private var mBucketFragment: BunketsFragment? = null
    private var mLikeFragment: LikeFragment? = null

    private val mPresenter: MainPresenter by lazy {
        MainPresenter(this)
    }

    private val mUser: User? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        if (savedInstanceState == null) initFragment()
        EventBus.getDefault().register(this)
        checkAuthCallback(intent)
    }

    private fun checkAuthCallback(intent: Intent?) {

    }

    private fun initFragment() {

    }

    private fun init() {

    }

    override fun onStart() {
        super.onStart()
        bindEvent()
    }

    private fun bindEvent() {
        mNvigation.setNavigationItemSelectedListener(this)
        mDrawerLayout.addDrawerListener(object :DrawerLayout.DrawerListener{
            override fun onDrawerStateChanged(newState: Int) {}
            override fun onDrawerSlide(drawerView: View?, slideOffset: Float) {}
            override fun onDrawerClosed(drawerView: View?) {}
            override fun onDrawerOpened(drawerView: View?) {
                if (singleData.isLogin() && mLogoutBtn.visibility == View.GONE){
                    mLogoutBtn.visibility = View.VISIBLE
                    mLogoutBtn.setOnClickListener {
                        mDialogManager.showOptionDialog(
                                resources.getString(R.string.logout_hint_title),
                                resources.getString(R.string.logout_hint),
                                onConfim = {
                                    logout()
                                },onCancel = {})
                    }
                }

                if (!singleData.avatar.isNullOrBlank()){

                }
            }
        })
    }

    private fun logout() {

    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        mHomeFragment?.onKeyDown(keyCode, event)
        mExploreFragment?.onKeyDown(keyCode, event)
        mBucketFragment?.onKeyDown(keyCode, event)
        mLikeFragment?.onKeyDown(keyCode, event)

        return super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {
        //给fragment传递返回事件
        if (mHomeFragment?.isVisible!! && mHomeFragment?.isShowSearchBar!!) {
            mHomeFragment?.onBackPresses()
            return
        }

        if (mExploreFragment?.isVisible!! && mExploreFragment?.isShowSearchBar!!) {
            mExploreFragment?.onBackPresses()
            return
        }

        if (mLikeFragment?.isVisible!! && mLikeFragment?.isShowSearchBar!!) {
            mLikeFragment?.onBackPresses()
            return
        }

        if (mBucketFragment?.isVisible!! && mBucketFragment?.isShowSearchBar!!) {
            mBucketFragment?.onBackPresses()
            return
        }

        finish()

    }


    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun getTokenSuccess(token: Token?) {

    }

    override fun getTokenFailed(msg: String) {
    }

    override fun getUserSuccess(user: User?) {
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        return false
    }
}
