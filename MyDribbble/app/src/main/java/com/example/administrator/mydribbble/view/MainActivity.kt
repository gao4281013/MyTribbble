package com.example.administrator.mydribbble.view

import android.content.Context
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.view.MenuItem
import com.example.administrator.mydribbble.R
import com.example.administrator.mydribbble.entity.Token
import com.example.administrator.mydribbble.entity.User
import com.example.administrator.mydribbble.presenter.MainPresenter
import com.example.administrator.mydribbble.view.activity.BaseActivity
import com.example.administrator.mydribbble.view.api.IMainView
import com.example.administrator.mydribbble.view.fragment.BunketsFragment
import com.example.administrator.mydribbble.view.fragment.ExploreFragment
import com.example.administrator.mydribbble.view.fragment.HomeFragment
import com.example.administrator.mydribbble.view.fragment.LikeFragment

class MainActivity : BaseActivity(),IMainView,NavigationView.OnNavigationItemSelectedListener {
    private var mHomeFragment:HomeFragment? = null
    private var mExploreFragment:ExploreFragment? = null
    private var mBucketFragment:BunketsFragment? = null
    private var mLikeFragment:LikeFragment? = null

    private val mPresenter:MainPresenter by lazy {
        MainPresenter(this)
    }

    private val mUser:User?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun getTokenSuccess(token: Token?) {

    }

    override fun getTokenFailed(msg: String) {
    }

    override fun getUserSuccess(user: User?) {
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
    }
}
