package com.example.administrator.mydribbble.view.activity

import android.os.Bundle
import com.example.administrator.mydribbble.R
import com.example.administrator.mydribbble.entity.Comment
import com.example.administrator.mydribbble.entity.Shot
import com.example.administrator.mydribbble.tools.singleData
import com.example.administrator.mydribbble.view.adapter.CommentAdapter
import com.example.administrator.mydribbble.view.api.IDetailView
import com.example.administrator.mydribbble.view.fragment.BunketsFragment
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.comment_layout.*
import org.greenrobot.eventbus.EventBus

class DetailsActivity : BaseActivity(),IDetailView {
   private val mPresenter:DetailPresenter by lazy {

   }

    private var mId:Long =0
    private var mAdapter: CommentAdapter? = null
    private val mComments:MutableList<Comment> by lazy {
        mutableListOf(Comment())
    }
    private lateinit var mShot:Shot
    private var mLiked:Boolean = false
    private var mBuccketfragment:BunketsFragment?=null
    private var bucketIsShowing = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        initView()
        EventBus.getDefault().register(this)
    }

    private fun initView() {
        if (singleData.isLogin()){
            mCommentEdit.isFocusableInTouchMode = true
            mCommentEdit.isFocusable = true
        }
        toolbar.inflateMenu(R.menu.nav_menu)
    }

    override fun getCommentsSuccess(Comments: MutableList<Comment>) {
    }

    override fun getcommentsFailed(msg: String) {
    }

    override fun showSendProgress() {
    }

    override fun hideSendProgress() {
    }

    override fun addCommentFailed(msg: String) {
    }

    override fun addCommentSuccess(comment: Comment?) {
    }

    override fun likeShotSuccess() {
    }

    override fun likeShotFailed(msg: String) {
    }

    override fun checkIfLikeSuccess() {
    }

    override fun checkIfLikeFailed() {
    }

    override fun unLikeShotSuccess() {
    }

    override fun unLikeShotFailed(msg: String) {
    }
}
