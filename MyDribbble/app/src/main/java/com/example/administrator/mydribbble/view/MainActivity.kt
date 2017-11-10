package com.example.administrator.mydribbble.view

import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import com.example.administrator.mydribbble.R
import com.example.administrator.mydribbble.entity.Token
import com.example.administrator.mydribbble.entity.User
import com.example.administrator.mydribbble.event.OpenDrawerEvent
import com.example.administrator.mydribbble.presenter.MainPresenter
import com.example.administrator.mydribbble.tools.*
import com.example.administrator.mydribbble.view.activity.BaseActivity
import com.example.administrator.mydribbble.view.activity.SettingActivity
import com.example.administrator.mydribbble.view.activity.UserActivity
import com.example.administrator.mydribbble.view.api.IMainView
import com.example.administrator.mydribbble.view.fragment.BunketsFragment
import com.example.administrator.mydribbble.view.fragment.ExploreFragment
import com.example.administrator.mydribbble.view.fragment.HomeFragment
import com.example.administrator.mydribbble.view.fragment.LikeFragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MainActivity : BaseActivity(),NavigationView.OnNavigationItemSelectedListener, IMainView{
    private var mHomeFragment: HomeFragment? = null
    private var mExploreFragment: ExploreFragment? = null
    private var mBucketFragment: BunketsFragment? = null
    private var mLikeFragment: LikeFragment? = null

    private val mPresenter: MainPresenter by lazy {
        MainPresenter(this)
    }

    private var mUser: User? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        if (savedInstanceState == null) initFragment()
        EventBus.getDefault().register(this)
        checkAuthCallback(intent)
    }



    private fun initFragment() {
       mHomeFragment = HomeFragment()
        mExploreFragment = ExploreFragment()
        mLikeFragment = LikeFragment()
        mBucketFragment = BunketsFragment.newInstance(BunketsFragment.LOCK_BUCKET)
        addFragment(mHomeFragment)
        mNvigation.setCheckedItem(R.id.mHomeMenu)
    }

    private fun addFragment(fragment:Fragment?) {
       fragmentManager.beginTransaction()
               .setCustomAnimations(R.anim.fragment_enter,R.anim.fragment_exit,R.anim.fragment_enter,R.anim.fragment_exit)
               .hide(mBucketFragment)
               .hide(mExploreFragment)
               .hide(mLikeFragment)
               .hide(mHomeFragment)
               .add(R.id.mContentLayout,fragment)
               .show(fragment)
               .commit()
    }

    private fun init() {
      //绑定抽屉
        val toggle = ActionBarDrawerToggle(this,mDrawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        mDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()
//        val params = mNvigation.layoutParams
//        params.width = screenWidth - screenWidth/4
        singleData.token = QuickSimpleIO.getString(Constant.KEY_TOKEN)
        mUser = Gson().fromJson(QuickSimpleIO.getString(Constant.KEY_USER),User::class.java)
        singleData.avatar = mUser?.avatar_url
        singleData.username = mUser?.name
        initUser()
    }

    private fun initUser() {
        //每次打开应用都要检查是否已经登陆，如果已经登录就要检查头像是否存在，如果再已登录的情况下无头像url,那我们需要去获取一次头像信息
        if (singleData.isLogin() && singleData.avatar.isNullOrBlank()){
            mPresenter.getMyInfo(singleData.token!!)
        }
    }

    @Subscribe
    fun DrawerOpen(drawerEvent:OpenDrawerEvent?){
        mDrawerLayout.openDrawer(GravityCompat.START)
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
                     ImageLoad.frescoLoadCircle(mUserAvatar,singleData.avatar.toString())
                    mNameText.text = singleData.username
                }

                mUserHead?.setOnClickListener {
                    if (!singleData.isLogin()){
                        login()
                    }else{
                       EventBus.getDefault().postSticky(mUser)
                        startActivity(Intent(applicationContext, UserActivity::class.java))
                    }

                }
            }
        })
    }

    /**n
     * 用浏览器打开验证链接，通过我们的CLIENT_ID去获取一个code吗用来获取用户的token
     * */
    private fun login() {
        mDrawerLayout.closeDrawer(GravityCompat.START)
        val intent = Intent("android.intent.action.VIEW", Uri.parse(Constant.OAUTH_URL))
        startActivity(intent)
    }

    private fun logout() {
       QuickSimpleIO.remove(Constant.KEY_TOKEN)
        QuickSimpleIO.remove(Constant.KEY_USER)
        singleData.username = null
        singleData.avatar = null
        singleData.token = null
        mDrawerLayout.closeDrawer(GravityCompat.START)
        mLogoutBtn.visibility = View.GONE
        ImageLoad.frescoLoadCircle(mUserAvatar,"")
        mNameText.setText(R.string.click_to_login)
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
        if (token!=null){
            QuickSimpleIO.setString(Constant.KEY_TOKEN,token.access_token)
            singleData.token = token.access_token
            toast(resources.getString(R.string.login_success))
            initUser()
        }else{
            toast(resources.getString(R.string.login_failed))
        }
    }

    override fun getTokenFailed(msg: String) {
        toast("${resources.getString(R.string.login_failed)}:$msg")
        Log.d("gavin",msg)
    }

    override fun getUserSuccess(user: User?) {
        if (user!=null){
            singleData.avatar = user.avatar_url
            singleData.username = user.name
            mNameText.text = user.name
            ImageLoad.frescoLoadCircle(mUserAvatar,singleData.avatar.toString())
            val  userJson = Gson().toJson(user)
            QuickSimpleIO.setString(Constant.KEY_USER,userJson)
        }
    }

    override fun showProgress() {
        mDialogManager.showCircleProgressDialog()
    }

    override fun hideProgress() {
        mDialogManager.dismissAll()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        mDrawerLayout.closeDrawer(GravityCompat.START)
        when(item.itemId){
            R.id.mHomeMenu -> if (!mHomeFragment!!.isAdded){
                addFragment(mHomeFragment)
            }else{
                replaceFragment(mHomeFragment)
            }
            R.id.mExploreMenu -> if (!mExploreFragment!!.isAdded){
                addFragment(mExploreFragment)
            }else{
                replaceFragment(mExploreFragment)
            }

            R.id.mBucketMenu -> if (!mBucketFragment!!.isAdded){
                addFragment(mBucketFragment)
            }else{
                replaceFragment(mBucketFragment)
            }

            R.id.mLikesMenu -> if (!mLikeFragment!!.isAdded){
                addFragment(mLikeFragment)
            }else{
                replaceFragment(mLikeFragment)
            }

            R.id.mSettingMenu ->{
                startActivity(Intent(this, SettingActivity::class.java))
                return false
            }

        }
        return true
    }

    private fun replaceFragment(fragment: Fragment?,addBackStack:Boolean = false) {
        if (fragment!=null && !fragment.isVisible){
            val transaction =  fragmentManager
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fragment_enter,R.anim.fragment_exit,R.anim.fragment_enter,R.anim.fragment_exit)
                    .hide(mBucketFragment)
                    .hide(mExploreFragment)
                    .hide(mLikeFragment)
                    .hide(mHomeFragment)
                    .show(fragment)
            if (addBackStack) transaction.addToBackStack(fragment.javaClass.simpleName).commit()
            else transaction.commit()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //onActivityResult 获取到的数据传给fragment一份
        mHomeFragment?.onActivityResult(requestCode,resultCode,data)
        mExploreFragment?.onActivityResult(requestCode,resultCode,data)
        mBucketFragment?.onActivityResult(requestCode,resultCode,data)
        mLikeFragment?.onActivityResult(requestCode,resultCode,data)

    }

    override fun onNewIntent(intent: Intent?) {
        checkAuthCallback(intent)
    }

    private fun checkAuthCallback(intent: Intent?) {
       if (intent!=null && intent.data != null && intent.data.authority.isNotEmpty() &&"towbbble.app" == intent.data.authority){
           mPresenter.getToken(intent.data.getQueryParameter("code"))
           return
       }
    }
}
