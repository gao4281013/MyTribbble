package com.example.administrator.mydribbble.view.activity

import android.animation.ValueAnimator
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.administrator.mydribbble.R
import com.example.administrator.mydribbble.entity.Shot
import com.example.administrator.mydribbble.entity.User
import com.example.administrator.mydribbble.presenter.UserPresenter
import com.example.administrator.mydribbble.tools.ImageLoad
import com.example.administrator.mydribbble.tools.singleData
import com.example.administrator.mydribbble.view.adapter.UserShotAdapter
import com.example.administrator.mydribbble.view.api.IUserView
import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.android.synthetic.main.user_center_counts.*
import kotlinx.android.synthetic.main.user_center_top.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.toast

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
    private var isFollowing: Boolean = false

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
        toolbar.inflateMenu(R.menu.user_center_menu)
        mShots = mutableListOf(Shot())
    }

    @Subscribe(threadMode =ThreadMode.MAIN,sticky = true)
    fun obtionUser(user: User){
        mUser = user
        mWho = if (singleData.isLogin() && user.avatar_url == singleData.avatar) MYSELF else OTHERS
        mountData(user)
    }

    private fun mountData(user: User) {
      with(user){
          ImageLoad.frescoLoadCircle(mAvatarImg,avatar_url.toString())
          if (!user.location.isNullOrBlank()) mLocationText.text = location else mLocation
              .visibility = View.GONE
          mNameText.text = name
          countAnimation(mShotText,shots_count)
          countAnimation(mBucketText,buckets_count)
          countAnimation(mFollowerText,followers_count)
          countAnimation(mFollingsText,followings_count)
          countAnimation(mGetLikedText,likes_received_count)
          countAnimation(mLikesText,likes_count)
          countAnimation(mProjectText,projects_count)
          countAnimation(mTeamsText,teams_count)
          if (mWho == MYSELF || !singleData.isLogin()){
              hideFollowMenu()
          }else{
              mPresenter.checkIfFollingUser(mUser.id)
          }

          mAdapter = UserShotAdapter(mShots,bio,{ _,i ->
              val shot = mShots[i]
              shot.user = mUser
              EventBus.getDefault().postSticky(shot)
              startActivity(Intent(this@UserActivity,DetailsActivity::class.java),
                  ActivityOptions.makeSceneTransitionAnimation(this@UserActivity).toBundle())

          })

          mRecycleView.adapter = mAdapter
          getShot(false)

      }
    }

    private fun getShot(isLoadMore: Boolean) {
        isLoading = true
        val userPath = if (mWho == MYSELF) PATH_USER else PATH_USERS
        val id = if(mWho == MYSELF) "" else mUser.id.toString()
        mPresenter.getUserShot(user = userPath,id = id,page = mPage,isLoadMore = isLoadMore)
    }

    private fun hideFollowMenu() {
        toolbar.menu.findItem(R.id.mFollow).isVisible = false

    }

    /**
     *数值变化动画
     * */
    private fun countAnimation(textview: TextView, max: Int){
       val anim = ValueAnimator.ofInt(0,max)
        anim.addUpdateListener {
            textview.text = "${anim.animatedValue}"
        }
        anim.duration = 1000
        anim.start()
    }

    override fun onStart() {
        super.onStart()
        bindEvent()
    }

    private fun bindEvent() {
        toolbar.setOnClickListener {
            finish()
        }

        mRecycleView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                val linearManager = recyclerView?.layoutManager as LinearLayoutManager
                val position = linearManager.findLastVisibleItemPosition()
                if (mShots.isNotEmpty() && position == mShots.size){
                    mPage+=1
                    getShot(true)
                }
            }
        })

        toolbar.setOnMenuItemClickListener { item ->
            when(item.itemId){
                R.id.mFollow -> follow()
                R.id.mShare -> share()
            }

            true
        }

        mAppBar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (verticalOffset==0){
                mAppBarState = EXPANDED
                toolbar.title = ""
            }else if (Math.abs(verticalOffset) >= appBarLayout.totalScrollRange){
                if(mAppBarState != COLLAPSED){
                    mAppBarState = COLLAPSED
                    toolbar.title = mUser.name
                }
            }else {
                if (mAppBarState != INTERNEDIATE){
                    if (mAppBarState == COLLAPSED){
                        toolbar.title = ""
                    }
                    mAppBarState = INTERNEDIATE
                }
            }
        }

    }

    private fun follow(){
      if (isFollowing){
          mPresenter.unFollowUser(mUser.id)
      }else{
          mPresenter.folloUser(mUser.id)
      }
    }

    private fun share(){
       val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.type = "text/plain"
        sendIntent.putExtra(Intent.EXTRA_TEXT,"我分享了设计师${mUser.name}的主页,快去看看他的作品吧\\n ${mUser.html_url}\\n来自@${getString(
            R.string.app_name)}")
        sendIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(Intent.createChooser(sendIntent,resources.getString(R.string.share)))
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        mPresenter.unSubsrciber()
    }



    override fun getShotSuccess(shots: MutableList<Shot>?, isLoadMore: Boolean) {
        isLoading = false
        if (shots!=null && shots.isNotEmpty()){
            mAdapter.addItems(shots)
        }else{
            if (!isLoadMore){
                mAdapter.loadError(R.string.no_shot){
                    getShot(true)
                }
            }else{
                isLoading = true
            }
        }
    }

    override fun getShotFailed(msg: String, isLoadMore: Boolean) {
        isLoading = false
        toast(msg)
        mAdapter.loadError(R.string.click_retry){
            getShot(true)
        }
    }

    override fun showProgress() {
        mAdapter.showProgress()
    }

    override fun hideProgress() {
        mAdapter.hideProgress()
    }

    override fun followUserSuccess() {
        isFollowing = true
        mFollingsText.text = "${mUser.followers_count+1}"
        toolbar.menu.findItem(R.id.mFollow).setTitle(R.string.unfollow)
        toast(R.string.follow_success)
    }

    override fun followUserFailed(msg: String) {
        toast("${resources.getString(R.string.follow_failed)}:$msg")
    }

    override fun unFollowUserSuccess() {
        isFollowing = false
        mFollingsText.text = "${mUser.followers_count - 1}"
        toolbar.menu.findItem(R.id.mFollow).setTitle(R.string.follow)
        toast(R.string.un_follow_success)
    }

    override fun unFollowUserFailed(msg: String) {
        toast("${resources.getString(R.string.un_follow_failed)}:$msg")
    }

    override fun following() {
        isFollowing = true
        toolbar.menu.findItem(R.id.mFollow).setTitle(R.string.unfollow)

    }

    override fun notFollowing() {
        isFollowing = false
    }

    override fun showProgressDialog(msg: String?) {
        mDialogManager.showCircleProgressDialog()
    }

    override fun hideProgressDialog() {
       mDialogManager.dismissAll()
    }
}
