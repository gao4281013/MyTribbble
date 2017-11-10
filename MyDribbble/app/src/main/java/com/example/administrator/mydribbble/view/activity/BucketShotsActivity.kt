package com.example.administrator.mydribbble.view.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.example.administrator.mydribbble.R
import com.example.administrator.mydribbble.application.App
import com.example.administrator.mydribbble.entity.Shot
import com.example.administrator.mydribbble.presenter.BucketShotPresenter
import com.example.administrator.mydribbble.tools.hideErrorImg
import com.example.administrator.mydribbble.tools.showErrorImg
import com.example.administrator.mydribbble.tools.toast
import com.example.administrator.mydribbble.view.adapter.ItemShotAdapter
import com.example.administrator.mydribbble.view.api.IBucketShotsView
import com.example.administrator.mydribbble.view.costumview.ItemSwipeRemoveCallBack
import kotlinx.android.synthetic.main.activity_bucket_shots.*
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.list.*
import org.greenrobot.eventbus.EventBus

class BucketShotsActivity : BaseActivity(),IBucketShotsView {
 companion object {
    val KEY_ID ="id"
    val KEY_TITLE = "title"
 }

  private var mId:Long = 0
  private var mTitle:String? = null
  private val mPresenter:BucketShotPresenter by lazy {
       BucketShotPresenter(this)
  }
  private var mAdapter:ItemShotAdapter? = null
  lateinit private var mShot:MutableList<Shot>
  private var isLoading:Boolean = false
  private var mPage = 1
  private var mDelPosition:Int = 0
  lateinit private var mDelShot:Shot

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_bucket_shots)
    mId = intent.getLongExtra(KEY_ID,0)
    mTitle = intent.getStringExtra(KEY_TITLE)
    initView()
    getShot()
  }

  private fun getShot(isLoadMore: Boolean = false) {
     isLoading = true
     mPresenter.getBucketShots(id = mId,page = mPage,isLoadMore = isLoadMore)
  }

  private fun initView() {
    bucket_tool_bar.title = title

    val layoutManager = LinearLayoutManager(App.instance,LinearLayoutManager.VERTICAL,false)
    mRecycleView.layoutManager = layoutManager
    mRecycleView.itemAnimator = DefaultItemAnimator()
  }

  override fun onStart() {
    super.onStart()
    bindEvent()
  }

  private fun bindEvent() {
    bucket_tool_bar.setNavigationOnClickListener {
       finish()
      }

    mErrorLayout.setOnClickListener {
      hideErrorImg(mErrorLayout)
      getShot(false)
    }
    mRefresh.setOnRefreshListener {
      getShot()
    }

    mRefresh.setOnLoadmoreListener {
       mPage+1
       getShot(true)
    }

    val itemTouchHelper = ItemTouchHelper(ItemSwipeRemoveCallBack { delPosition, _ ->
      mDelShot = mShot[delPosition]
      mDelPosition = delPosition
      mAdapter?.deleteItem(delPosition)
      mDialogManager.showOptionDialog(
          getString(R.string.delete_bucket),
          getString(R.string.whether_to_delete_bucket),
          confirmText = getString(R.string.delete),
          onConfim = {
            mPresenter.removeShotFromBuckets(id = mId,shot_id =mDelShot.id)
          }, onCancel = {
        mAdapter?.addItem(delPosition, mDelShot)
        mRecycleView.scrollToPosition(delPosition)
      })
    })
    itemTouchHelper.attachToRecyclerView(mRecycleView)

    mRecycleView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
      override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val layoutManager = recyclerView?.layoutManager as LinearLayoutManager
        val lastPos = layoutManager.findLastVisibleItemPosition()
        if (!isLoading && lastPos == mShot.size-1){
          mPage+1
          getShot(true)
        }
      }
    })
  }

  override fun onDestroy() {
    super.onDestroy()
    mPresenter.unSubsrciber()
  }

  private fun mountList(shots: MutableList<Shot>) {
    mShot = shots
    mAdapter = ItemShotAdapter(mShot,{
      EventBus.getDefault().postSticky(mShot[it])
      startActivity(Intent(this,DetailsActivity::class.java),
          ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    },{
      EventBus.getDefault().postSticky(shots[it].user)
      startActivity(Intent(this, UserActivity::class.java))
    })
    mRecycleView.adapter = mAdapter
  }

  override fun getShotSuccess(shots: MutableList<Shot>?, isLoadMore: Boolean) {
      isLoading = false
    if (shots!=null && shots.isNotEmpty()){
      if (!isLoadMore){
          mountList(shots)
      }else{
        mAdapter?.addItems(shots)
      }
    }else{
      if (!isLoadMore){
        showErrorImg(mErrorLayout,resources.getString(R.string.no_shot))
      }else{
        mAdapter?.hideProgress()
        }
      }
    }

  override fun getShotFailed(msg: String, isLoadMore: Boolean) {
    isLoading = false
    toast(msg)
    if (!isLoadMore){
      if (mAdapter == null){
         showErrorImg(mErrorLayout,msg,R.mipmap.img_network_error_2)
      }
    }else{
      mAdapter?.loadError {
        getShot(true)
      }
    }
  }

  override fun removeShotSuccess() {
    toast(R.string.delete_success)
  }

  override fun removeShotFailed(msg: String) {
    toast("${resources.getString(R.string.delete_success)}:$msg")
    mAdapter?.addItem(mDelPosition,mDelShot)
    mRecycleView.scrollToPosition(mDelPosition)
  }
}