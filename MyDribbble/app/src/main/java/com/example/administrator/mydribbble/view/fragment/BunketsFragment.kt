package com.example.administrator.mydribbble.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.administrator.mydribbble.R
import com.example.administrator.mydribbble.application.App
import com.example.administrator.mydribbble.entity.Bucket
import com.example.administrator.mydribbble.event.HideBucketEvent
import com.example.administrator.mydribbble.event.OpenDrawerEvent
import com.example.administrator.mydribbble.presenter.MyBucketPresenter
import com.example.administrator.mydribbble.tools.*
import com.example.administrator.mydribbble.view.activity.BucketShotsActivity
import com.example.administrator.mydribbble.view.adapter.MyBucketsAdapter
import com.example.administrator.mydribbble.view.api.IMyBucketsView
import com.example.administrator.mydribbble.view.costumview.ItemSwipeRemoveCallBack
import com.example.administrator.mydribbble.view.costumview.NormalDivider
import com.example.administrator.mydribbble.view.dialog.DialogManager
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.fragment_bunkets.*
import kotlinx.android.synthetic.main.list.*
import org.greenrobot.eventbus.EventBus

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [BunketsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [BunketsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BunketsFragment : BaseFragment(), IMyBucketsView {
  private val mDialogManager: DialogManager by lazy {
    DialogManager(activity)
  }

  private val mPresenter: MyBucketPresenter by lazy {
    MyBucketPresenter(this)
  }
  private var madapter: MyBucketsAdapter? = null
  private val mBuckets: MutableList<Bucket> by lazy {
    mutableListOf<Bucket>()
  }
  private lateinit var mDelBucket: Bucket
  private var mDelPosition: Int = 0
  private var mType: String? = null
  private var mShotId: Long = 0


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    if (arguments != null) {
      mType = arguments.getString(TYPE)
      mShotId = arguments.getLong(SHOT_ID, 0)
    }
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    // Inflate the layout for this fragment
    return inflater!!.inflate(R.layout.fragment_bunkets, container, false)
  }


  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initView()
    getBuckets()
  }

  private fun getBuckets() {
    if (singleData.isLogin()) {
      mPresenter.getMyBuckets(singleData.token!!)
    } else {
      showErrorImg(mErrorLayout, R.string.not_logged, R.mipmap.img_empty_buckets)
    }
  }

  private fun initView() {
    activity.hasNavigationBar {
      if (mType != ADD_SHOT) {
        val params = mAddBtn.layoutParams as ViewGroup.MarginLayoutParams
        params.setMargins(0, 0, Utils.dp2px(16).toInt(), Utils.dp2px(64).toInt())
      }
    }

    val layoutManager = LinearLayoutManager(App.instance, LinearLayoutManager.VERTICAL, false)
    mRecycleView.layoutManager = layoutManager
    mRecycleView.itemAnimator = DefaultItemAnimator()
    mRecycleView.addItemDecoration(NormalDivider())

    if (mType == ADD_SHOT) {
      tool_bar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
      tool_bar.setTitle(R.string.into_the_bucket)
      mStatuView.visibility = View.VISIBLE
    }
  }


  override fun onAttach(context: Context?) {
    super.onAttach(context)
  }

  override fun onDetach() {
    super.onDetach()
  }

  override fun onDestroy() {
    super.onDestroy()
    mDialogManager.dismissAll()
    mPresenter.unSubsrciber()
  }

  override fun onStart() {
    super.onStart()
    bindEvent()
  }

  private fun bindEvent() {
    tool_bar.setNavigationOnClickListener {
      when (mType) {
        ADD_SHOT -> EventBus.getDefault().post(HideBucketEvent())
        LOCK_BUCKET -> EventBus.getDefault().post(OpenDrawerEvent())
      }
    }
    mAddBtn.setOnClickListener {
      addBucket()
    }

    mRefresh.setOnRefreshListener {
      getBuckets()
    }

    mRefresh.setOnLoadmoreListener {

    }

    val itemTouchHelper = ItemTouchHelper(ItemSwipeRemoveCallBack { delPosition, _ ->
      mDelBucket = mBuckets[delPosition]
      mDelPosition = delPosition
      madapter?.deleteItem(delPosition)
      mDialogManager.showOptionDialog(
          getString(R.string.delete_bucket),
          getString(R.string.whether_to_delete_bucket),
          confirmText = getString(R.string.delete),
          onConfim = {
            mPresenter.deleteBucket(singleData.token!!, mDelBucket.id)
          }, onCancel = {
        madapter?.addItem(mDelBucket, delPosition)
      })
    })
    itemTouchHelper.attachToRecyclerView(mRecycleView)
  }

  private fun addBucket() {
    if (singleData.isLogin()) {
      mDialogManager.showEditBucketDialog { name, description ->
        mPresenter.createBucket(singleData.token!!, name, description)
      }
    } else {
      toast(R.string.not_logged)
    }
  }

  companion object {
    val TYPE = "type"
    val SHOT_ID = "id"
    val LOCK_BUCKET = "lock"
    val ADD_SHOT = "add"
    fun newInstance(type: String? = null, shotId: Long = 0): BunketsFragment {
      val fragment = BunketsFragment()
      val args = Bundle()
      args.putString(TYPE, type)
      args.putLong(SHOT_ID, shotId)
      fragment.arguments = args
      return fragment
    }
  }


  override fun getBucketsSuccess(buckets: MutableList<Bucket>?) {
    if (buckets != null) {
      mBuckets.clear()
      mBuckets.addAll(buckets)
      mRecycleView.visibility = View.VISIBLE
      mRecycleView.adapter = getAdapter(mBuckets)
    } else {
      mRecycleView.visibility = View.GONE
      showErrorImg(mErrorLayout, R.string.no_bucket, R.mipmap.img_empty_buckets)
    }
  }

  private fun getAdapter(mBuckets: MutableList<Bucket>): RecyclerView.Adapter<*>? {
    madapter = MyBucketsAdapter(mBuckets, { position ->
      itemClick(position, mBuckets)
    }, { position ->
      mDialogManager.showEditBucketDialog(mBuckets[position].name!!, mBuckets[position]
          .description, resources.getString(R.string.modify_bucket)) { name, description ->
        mPresenter.modifyBucket(singleData.token!!, mBuckets[position].id, name, description,
            position)
      }

    })
    return madapter!!
  }

  private fun itemClick(position: Int, mBuckets: MutableList<Bucket>) {
    when (mType) {
      LOCK_BUCKET -> {
        val intent = Intent(activity, BucketShotsActivity::class.java)
        intent.putExtra(BucketShotsActivity.KEY_ID, mBuckets[position].id)
        intent.putExtra(BucketShotsActivity.KEY_TITLE, mBuckets[position].name)
        startActivity(intent)
      }
      ADD_SHOT -> mPresenter.addShot2Bucket(id = mBuckets[position].id, shotId = mShotId)
    }
  }

  override fun getBucketsFailed(msg: String) {
    toast(msg)
    if (madapter == null) {
      showErrorImg(mErrorLayout, msg, R.mipmap.img_empty_buckets)
    }
  }

  override fun showProgress() {
    super.showProgress()
  }

  override fun hideProgress() {
    super.hideProgress()
  }

  override fun showProgressDialog(msg: String?) {
    mDialogManager.showCircleProgressDialog()
  }

  override fun hideProgressDialog() {
    mDialogManager.dismissAll()
  }

  override fun createBucketSuccess(bucket: Bucket?) {
    if (bucket != null) {
      if (madapter != null) {
        madapter?.addItem(bucket, 0)
        mRecycleView.scrollToPosition(0)
      } else {
        mBuckets.add(bucket)
        mRecycleView.adapter = getAdapter(mBuckets)
      }
    }
  }

  override fun createBucketFailed(msg: String) {
    toast(msg)
  }

  override fun deleteBucketSuccess() {
    toast(R.string.delete_success)
  }

  override fun deleteBucketFailed(msg: String) {
    toast("${resources.getString(R.string.delete_failed)}:$msg}")
    madapter?.addItem(mDelBucket, mDelPosition)
  }

  override fun modifyBucketSuccess(bucket: Bucket?, position: Int) {
    mBuckets[position].name = bucket?.name
    mBuckets[position].description = bucket?.description
    mBuckets[position].updated_at = bucket?.updated_at
    madapter?.notifyDataSetChanged()
    toast(R.string.modify_success)
  }

  override fun modifyBucketFailed(msg: String) {
    toast("${resources.getString(R.string.modify_failed)}:$msg")
  }

  override fun addShotSuccess() {
    toast(resources.getString(R.string.add_success))
    EventBus.getDefault().post(HideBucketEvent())
  }

  override fun addShotFailed(msg: String) {
    toast("${resources.getString(R.string.add_failed)}:$msg")
  }

}
