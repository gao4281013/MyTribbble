package com.example.administrator.mydribbble.presenter

import com.example.administrator.mydribbble.R
import com.example.administrator.mydribbble.application.App
import com.example.administrator.mydribbble.biz.api.IDetailsBiz
import com.example.administrator.mydribbble.biz.impl.DetailBiz
import com.example.administrator.mydribbble.entity.Comment
import com.example.administrator.mydribbble.entity.LikeShotResponse
import com.example.administrator.mydribbble.tools.NetSubsrciber
import com.example.administrator.mydribbble.tools.log
import com.example.administrator.mydribbble.view.api.IDetailView
import org.jetbrains.annotations.NotNull

/**
 * Created by Administrator on 2017/10/13.
 */
class DetailsPresenter(val mDetailsView: IDetailView):BasePresenter() {
    private val mDetailBiz:IDetailsBiz by  lazy {
        DetailBiz()
    }

    fun getComment(@NotNull id:Long,@NotNull token:String,page:Int?){
        val subscribe = mDetailBiz.getComments(id,token,page,object:NetSubsrciber<MutableList<Comment>>(mDetailsView){
            override fun onFailed(msg: String) {
                mDetailsView.getcommentsFailed(msg)
            }

            override fun onNext(t: MutableList<Comment>?) {
                mDetailsView.getCommentsSuccess(t!!)
            }
        })
        mSubScription.add(subscribe)
    }

    fun likeShot(@NotNull id: Long,@NotNull token: String){
        val subscribe = mDetailBiz.likeShot(id,token,object :NetSubsrciber<LikeShotResponse>(){
            override fun onFailed(msg: String) {
                mDetailsView.likeShotFailed("${App.instance.getString(R.string.like_failed)}}:$msg")
            }

            override fun onNext(t: LikeShotResponse?) {
                if (t != null)
                    mDetailsView.likeShotSuccess() else
                    mDetailsView.likeShotFailed(App.instance.resources.getString(R.string.like_failed))
            }
        })
        mSubScription.add(subscribe)
    }

    fun checkIfLikeShot(@NotNull id: Long,@NotNull token: String){
        val subscribe = mDetailBiz.checkIfLikeShot(id,token,object :NetSubsrciber<LikeShotResponse>(){
            override fun onFailed(msg: String) {
                log(msg)
                mDetailsView.checkIfLikeFailed()
            }

            override fun onNext(t: LikeShotResponse?) {
                if (t != null) mDetailsView.checkIfLikeSuccess() else mDetailsView.checkIfLikeFailed()
            }
        })
        mSubScription.add(subscribe)
    }

    fun unlikeShot(@NotNull id: Long,@NotNull token: String){
        val subscribe = mDetailBiz.unLikeShot(id, token, object : NetSubsrciber<LikeShotResponse>() {
            override fun onFailed(msg: String) {
                mDetailsView.unLikeShotFailed("${App.instance.resources.getString(R.string.unlike_failed)}:$msg")
            }

            override fun onNext(t: LikeShotResponse?) {
                mDetailsView.unLikeShotSuccess()
            }
        })

        mSubScription.add(subscribe)
    }

    fun createComment(@NotNull id: Long,@NotNull token: String,@NotNull body:String){
        val subscribe = mDetailBiz.createComment(id,token,body,object :NetSubsrciber<Comment>(){
            override fun onFailed(msg: String) {
                mDetailsView.hideSendProgress()
                mDetailsView.addCommentFailed("${App.instance.resources.getString(R.string.account_not_player)}:$msg")
            }

            override fun onNext(t: Comment?) {
                mDetailsView.addCommentSuccess(t)
            }

            override fun onCompleted() {
                super.onCompleted()
                mDetailsView.hideSendProgress()
            }

            override fun onStart() {
                super.onStart()
                mDetailsView.showSendProgress()
            }
        })
        mSubScription.add(subscribe)
    }

}