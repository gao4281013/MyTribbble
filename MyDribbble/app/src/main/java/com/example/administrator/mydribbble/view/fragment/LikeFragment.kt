package com.example.administrator.mydribbble.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.administrator.mydribbble.R
import com.example.administrator.mydribbble.application.App
import com.example.administrator.mydribbble.entity.Like
import com.example.administrator.mydribbble.event.OpenDrawerEvent
import com.example.administrator.mydribbble.presenter.LikePresenter
import com.example.administrator.mydribbble.tools.hideErrorImg
import com.example.administrator.mydribbble.tools.showErrorImg
import com.example.administrator.mydribbble.tools.singleData
import com.example.administrator.mydribbble.tools.toast
import com.example.administrator.mydribbble.view.activity.UserActivity
import com.example.administrator.mydribbble.view.adapter.LikeAdapter
import com.example.administrator.mydribbble.view.api.ILikeView
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.fragment_like.*
import kotlinx.android.synthetic.main.list.*
import org.greenrobot.eventbus.EventBus

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [LikeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [LikeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LikeFragment : BaseFragment(),ILikeView {
    private val mPresenter:LikePresenter by lazy {
        LikePresenter(this)
    }
    private var mpage:Int = 1
    private val mLikes:MutableList<Like> by lazy {
        mutableListOf<Like>()
    }
    private var isLoading:Boolean = false
    private var mListAdapter:LikeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_like, container, false)
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        getLikes(false)
    }

    private fun getLikes(b: Boolean) {
        if (singleData.isLogin()) {
            isLoading = true
            mPresenter.getMyLikes(singleData.token!!
                , page = mpage
                , isLoadMore = b)
        }else{
           showErrorImg(mErrorLayout,R.string.not_logged,R.mipmap.img_empty_likes)
        }
    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(App.instance,LinearLayoutManager.VERTICAL,false)
        mRecycleView.layoutManager = layoutManager
        mRecycleView.itemAnimator = DefaultItemAnimator()
    }

    override fun onStart() {
        super.onStart()
        bindEvent()
    }

    private fun bindEvent() {
        tool_bar.setNavigationOnClickListener {
            EventBus.getDefault().post(OpenDrawerEvent())
        }

        mErrorLayout.setOnClickListener {
            hideErrorImg(mErrorLayout)
            mpage = 1
            getLikes(false)
        }

        mRefresh.setOnRefreshListener {
            mpage =1
            getLikes(false)
        }

        mRefresh.setOnLoadmoreListener {
            mpage+1
            getLikes(true)
        }

        mRecycleView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView?.layoutManager as LinearLayoutManager
                val lastPos = layoutManager.findLastVisibleItemPosition()
                if (!isLoading && lastPos == mLikes.size-1){
                    mpage+1
                    getLikes(true)
                }
            }
        })
    }

    override fun getLikesSuccess(likes: MutableList<Like>?, isLoadMore: Boolean) {
        isLoading = false
        finishRefresh(isLoadMore)
        if (likes!=null && likes.isNotEmpty()){
            if (!isLoadMore){
              mountList(likes)
            }else{
              mListAdapter?.addItems(likes)
            }
        }else{
            if (!isLoadMore){
                showErrorImg(mErrorLayout,R.string.no_likes,R.mipmap.img_empty_likes)
            }else{
                mListAdapter?.hideProgress()
            }
        }


    }

    private fun mountList(likes: MutableList<Like>) {
        mLikes.clear()
        mLikes.addAll(likes)
        mListAdapter = LikeAdapter(mLikes, {_,position ->
            EventBus.getDefault().postSticky(mLikes[position])
            startDetailsActivity()
        }, {_,position ->
            EventBus.getDefault().postSticky(mLikes[position].shot.user)
            startActivity(Intent(activity, UserActivity::class.java))
        })
        mRecycleView.adapter = mListAdapter
    }

    override fun getLikesFailed(msg: String, isLoadMore: Boolean) {
        isLoading = false
        finishRefresh(isLoadMore)
        toast(msg)
        if (!isLoadMore){
                if (mListAdapter == null)
                    showErrorImg(mErrorLayout,R.string.no_likes,R.mipmap.img_empty_likes)
        }else{
            mListAdapter?.loadError {
                getLikes(true)
            }
        }
    }


    private fun finishRefresh(isLoadMore: Boolean) {
        if (isLoadMore) {
            mRefresh.finishLoadmore()
        } else {
            mRefresh.finishRefresh()
        }
    }
}
