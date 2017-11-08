package com.example.administrator.mydribbble.view.fragment

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.example.administrator.mydribbble.R
import com.example.administrator.mydribbble.application.App
import com.example.administrator.mydribbble.entity.Shot
import com.example.administrator.mydribbble.event.OpenDrawerEvent
import com.example.administrator.mydribbble.presenter.ShotPresenter
import com.example.administrator.mydribbble.tools.*
import com.example.administrator.mydribbble.view.activity.SearchActivity
import com.example.administrator.mydribbble.view.activity.UserActivity
import com.example.administrator.mydribbble.view.adapter.ItemShotAdapter
import com.example.administrator.mydribbble.view.api.IShotView
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.fragment_explore.*
import kotlinx.android.synthetic.main.list.*
import kotlinx.android.synthetic.main.search_layout.*
import org.greenrobot.eventbus.EventBus

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ExploreFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ExploreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExploreFragment : BaseFragment(),IShotView {

    private val mPresenter :ShotPresenter by lazy {
        ShotPresenter(this)
    }

    private var mSort:String? = null
    private var mSortList:String? = null
    private var mTimeFrame:String? = null
    private var mPage:Int =1
    private lateinit var mShots:MutableList<Shot>
    private var mListAdapter:ItemShotAdapter?=null
    private var isLoading:Boolean = false

    private val mSorts = listOf(null,Parameters.COMMENTS,Parameters.RECENT,Parameters.VIEWS)
    private val mLists = listOf(null,Parameters.ANIMATED,Parameters.ATTACHMENTS,Parameters.DEBUTS
    ,Parameters.PLAYOFFS,Parameters.REBOUNDS,Parameters.TEAMS)

    private var isFirstEnter = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        bindEvent()
        getShots(false)
    }

    private fun getShots(isLoadMore: Boolean = false) {
        isLoading = true
        val token = QuickSimpleIO.getString(Constant.KEY_TOKEN)
        if (token==null || token==""){
            mPresenter.getShots(sort = mSort,
                list =mSortList,timeframe = mTimeFrame,
                page = mPage,
                isLoadMore = isLoadMore)
        }else{
            mPresenter.getShots(
                access_token = token,
                sort = mSort,
                list =mSortList,
                timeframe = mTimeFrame,
                page = mPage,
                isLoadMore = isLoadMore)
        }
    }

    override fun onStart() {
        super.onStart()
    }

    private fun bindEvent() {
        mSearchEdit.setOnKeyListener { _, keyCode, event ->
            //判断是否为点按下去触发的事件，如果不写，会导致该案件的事件被执行两次
            if (event.action == KeyEvent.ACTION_DOWN){
               when(keyCode){
                   KeyEvent.KEYCODE_ENTER -> search()
               }
            }
            false
        }

        mBackBtn.setOnClickListener { hideSearchView() }
        mSearchBtn.setOnClickListener { search() }
        mVoiceBtn.setOnClickListener { startSpeak() }

        Toolbar.setNavigationOnClickListener {
            EventBus.getDefault().post(OpenDrawerEvent())
        }

        mRefresh.setOnRefreshListener {
            mPage = 1
            getShots(false)
        }
        mRefresh.setOnLoadmoreListener {

        }

        mErrorLayout.setOnClickListener {
            hideErrorImg(mErrorLayout)
            getShots(false)
        }


        msortSpinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int,
                id: Long) {
                mSort = mSorts[position]
                mListAdapter = null
                getShots(false)
            }
        }

        msortListSpinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int,
                id: Long) {
                mSortList = mLists[position]
                mListAdapter = null
                getShots(false)
            }
        }

        Toolbar.setOnMenuItemClickListener { item ->
            when(item.itemId){
                R.id.mNow -> timeFrameUpdate(null)
                R.id.mWeek -> timeFrameUpdate(Parameters.WEEK)
                R.id.mMonth -> timeFrameUpdate(Parameters.YEAR)
                R.id.mYear -> timeFrameUpdate(Parameters.YEAR)
                R.id.mAllTime -> timeFrameUpdate(Parameters.EVER)
                R.id.mSearch -> mSearch_layout.showSearchView(Toolbar.width, { isShowSearchBar = true })
            }
            true

        }
    }

    /**
     * 更新时间先
     * @param timeFrame 时间线，默认为null，代表Now，其它的值在Parameters这个单利对象中保存
     * */
    private fun timeFrameUpdate(timeFrame:String?){
        mListAdapter = null
        mTimeFrame = timeFrame
        getShots(false)
    }

    override fun showProgress() {
    }

    override fun hideProgress() {
    }

    private fun search(){
      if (mSearchEdit.text != null && mSearchEdit.text.toString() != ""){
          val intent = Intent(activity,SearchActivity::class.java)
          intent.putExtra(SearchActivity.KEY_KEYWORD,mSearchEdit.text.toString())
          startActivity(intent,ActivityOptions.
          makeSceneTransitionAnimation(activity,mSearch_layout,"searchBar").toBundle())
      }
    }

    private fun initView() {
       Toolbar.inflateMenu(R.menu.explore_menu)
        val layoutManager = LinearLayoutManager(App.instance,LinearLayoutManager.VERTICAL,false)
        mRecycleView.layoutManager = layoutManager
        mRecycleView.itemAnimator = DefaultItemAnimator()
        msortSpinner.setSelection(0,true)
        msortListSpinner.setSelection(0,true)

        if (isFirstEnter) {
            isFirstEnter = false
            mRefresh.autoRefresh()//第一次进入触发自动刷新，演示效果
        }

        app_bar_layout.setExpanded(false, true);
        app_bar_layout.setEnabled(false);
        mRecycleView.setNestedScrollingEnabled(false);
    }

    override fun onBackPresses() {
        hideSearchView()
    }

    private fun hideSearchView() {
        mSearch_layout.hideSearchView {
            isShowSearchBar = false
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

    }

    override fun onDetach() {
        super.onDetach()
    }


    private fun finishRefresh(isLoadMore: Boolean) {
        if (isLoadMore) {
            mRefresh.finishLoadmore()
        } else {
            mRefresh.finishRefresh()
        }
    }


    override fun getShotSuccess(shots: MutableList<Shot>?, isLoadMore: Boolean) {
        Log.d("Gavin","Success-----"+ shots.toString())
        finishRefresh(isLoadMore)
       isLoading = false
        if (shots!=null && shots.isNotEmpty()){
            if (!isLoadMore){
                mountList(shots)
            }else{
                mListAdapter?.addItems(shots)
            }
        }else{
            if (!isLoadMore){
                if (mListAdapter == null){
                    showErrorImg(mErrorLayout)
                }else{
                    mListAdapter?.loadError {
                        getShots(true)
                    }
                }
            }
        }

    }

    private fun mountList(shots: MutableList<Shot>) {
            mShots = shots
            mListAdapter = ItemShotAdapter(mShots,{
                EventBus.getDefault().postSticky(mShots[it])
                startDetailsActivity()
            },{
                EventBus.getDefault().postSticky(shots[it].user)
                startActivity(Intent(activity, UserActivity::class.java))
            })
            mRecycleView.adapter = mListAdapter
    }

    override fun getShotFailed(msg: String, isLoadMore: Boolean) {
        Log.d("Gavin", "Failed-----"+msg)
        finishRefresh(isLoadMore)
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
}
