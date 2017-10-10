package com.example.administrator.mydribbble.view.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.administrator.mydribbble.R
import com.example.administrator.mydribbble.application.App
import com.example.administrator.mydribbble.entity.Shot
import com.example.administrator.mydribbble.presenter.ShotPresenter
import com.example.administrator.mydribbble.tools.Constant
import com.example.administrator.mydribbble.tools.hideErrorImg
import com.example.administrator.mydribbble.tools.showErrorImg
import com.example.administrator.mydribbble.tools.singleData
import com.example.administrator.mydribbble.view.activity.UserActivity
import com.example.administrator.mydribbble.view.adapter.ItemShotAdapter
import com.example.administrator.mydribbble.view.api.IShotView
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.list.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.toast
import kotlin.reflect.KProperty

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ShotsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ShotsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShotsFragment : BaseFragment(),IShotView {
    private val mPresenter:ShotPresenter by lazy {
        ShotPresenter(this)
    }

    private var mSort:String?= null
    private var mSortList:String? = null
    private var mTimeFrame:String? = null
    private var mPage:Int = 1
    private var mShots:MutableList<Shot> by lazy {
        mutableListOf<Shot>()
    }
    private var isLoading:Boolean = false;
    private var mListAdapter:ItemShotAdapter? = null



    companion object {
        val SORT = "sort"
        val RECENT = "recent"
        fun newInstance(sort:String?=null):ShotsFragment{
            val fragment = ShotsFragment()
            val args = Bundle()
            args.putString(SORT,sort)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return LayoutInflater.from(activity).inflate(R.layout.fragment_shots,null)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        initViews();
        getShots(false)
    }

    private fun initViews() {
      mRefresh.setColorSchemeResources(R.color.google_red,R.color.google_yellow,R.color.google_green,R.color.google_blue)
      val layoutManager = LinearLayoutManager(App.instance,LinearLayoutManager.VERTICAL,false)
        mRecycleView.layoutManager = layoutManager
        mRecycleView.itemAnimator = DefaultItemAnimator()
    }
    private fun getShots(isLoadMore: Boolean) {
         isLoading = true
        val token = singleData.token?:Constant.ACCESS_TOKEN
        mPresenter.getShots(access_token = token,
                sort = mSort,
                list = mSortList,
                timeframe = mTimeFrame,
                page = mPage,
                isLoadMore = isLoadMore)
    }


    override fun onStart() {
        super.onStart()
        bindEvent()
    }

    private fun bindEvent() {
        mRefresh.setOnRefreshListener {
            mPage = 1
            getShots(false)
        }

        mErrorLayout.setOnClickListener {
            hideErrorImg(mErrorLayout)
            getShots(false)
        }

        mRecycleView.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                val linearMagager = recyclerView?.layoutManager as LinearLayoutManager
                val position = linearMagager.findLastVisibleItemPosition()
                if (mShots.isNotEmpty() && position == mShots.size){
                    if (!isLoading){
                        mPage +=1
                        getShots(true)
                    }
                }

            }

        })

    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.unSubsrciber()
    }

    override fun showProgress() {
        if (mListAdapter==null)mRefresh.isRefreshing = true
    }

    override fun hideProgress() {
        mRefresh.isRefreshing = false
    }




    override fun getShotSuccess(shots: MutableList<Shot>?, isLoadMore: Boolean) {
           isLoading = false
        if (shots!=null && shots.isNotEmpty()){
            if (!isLoadMore){
                mountList(shots)
            }else{
                mListAdapter?.addItems(shots)
            }
        }else{
            if (!isLoadMore){
                showErrorImg(mErrorLayout)
            }else{
                mListAdapter?.loadError {
                    getShots(true)
                }
            }
        }


    }


    private fun mountList(shots: MutableList<Shot>) {
        mShots.clear()
        mShots.addAll(shots)
        mListAdapter = ItemShotAdapter(mShots,{
            EventBus.getDefault().postSticky(mShots[it])
            startDetailsActivity()
        },{
            EventBus.getDefault().postSticky(shots[it].user)
            startActivity(Intent(activity,UserActivity::class.java))
        })
        mRecycleView.adapter = mListAdapter
    }

    override fun getShotFailed(msg: String, isLoadMore: Boolean) {
        isLoading = false
        toast(msg)
        if (!isLoadMore){
            if (mListAdapter == null){
                showErrorImg(mErrorLayout,msg,R.mipmap.img_network_error_2)
            }else{
                mListAdapter?.loadError {
                    getShots(true)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSort = arguments?.getString(SORT)
    }

    override fun onBackPresses() {
        super.onBackPresses()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?) {
        super.onKeyDown(keyCode, event)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}

private operator fun Any.setValue(shotsFragment: ShotsFragment, property: KProperty<*>, mutableList: MutableList<Shot>) {}
