package com.example.administrator.mydribbble.view.activity

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import com.example.administrator.mydribbble.R
import com.example.administrator.mydribbble.entity.Shot
import com.example.administrator.mydribbble.entity.User
import com.example.administrator.mydribbble.presenter.UserPresenter
import com.example.administrator.mydribbble.tools.singleData
import com.example.administrator.mydribbble.view.adapter.UserShotAdapter
import com.example.administrator.mydribbble.view.api.IUserView
import kotlinx.android.synthetic.main.activity_user.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class UserActivity : BaseActivity(),IUserView{
    lateinit private var mShots:MutableList<Shot>
    lateinit private var mPresenter:UserPresenter

    private val MYSELF = 0
    private val OTHERS = 1

    private val PATH_USER = "user"
    private val PATH_USERS = "users"

    val EXPANDED = 0x02
    val COLLAPSED = 0x03
    val INTERNEDIATE = 0x04

    private var mAppBarState = EXPANDED

    private var mWho = MYSELF
    lateinit private var mUser:User
    private var isLoading:Boolean = false
    private var mPage:Int = 1
    lateinit private var mAdapter: UserShotAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        init()
        EventBus.getDefault().register(this)
    }

    private fun init() {
        mPresenter = UserPresenter(this)
        val layoutManager = GridLayoutManager(this,3)
        mRecycleView.layoutManager = layoutManager
        mRecycleView.itemAnimator = DefaultItemAnimator()
        toolbar.inflateMenu(R.menu.search_menu)
        mShots = mutableListOf(Shot())
    }

    @Subscribe(threadMode =ThreadMode.MAIN,sticky = true)
    fun obtionUser(user: User){
        mUser = user
        mWho = if (singleData.isLogin() && user.avatar_url == singleData.avatar) MYSELF else OTHERS
        mountData(user)
    }

    private fun mountData(user: User) {

    }

    /**
     * 
     * */

    override fun getShotSuccess(shots: MutableList<Shot>?, isLoadMore: Boolean) {

    }

    override fun getShotFailed(msg: String, isLoadMore: Boolean) {
    }

    override fun followUserSuccess() {
    }

    override fun followUserFailed(msg: String) {
    }

    override fun unFollowUserSuccess() {
    }

    override fun unFollowUserFailed(msg: String) {
    }

    override fun following() {
    }

    override fun notFollowing() {
    }
}
