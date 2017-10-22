package com.example.administrator.mydribbble.view.adapter

import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.administrator.mydribbble.R
import com.example.administrator.mydribbble.entity.Comment
import com.example.administrator.mydribbble.entity.Shot
import com.example.administrator.mydribbble.tools.ImageLoad
import com.example.administrator.mydribbble.tools.Utils
import kotlinx.android.synthetic.main.item_comment.view.*

/**
 * Created by Administrator on 2017/10/13.
 */
class CommentAdapter( val mShot: Shot,
                      val mComments:MutableList<Comment>,
    val likeClick:(View,Int) -> Unit,
    val userClick:(View,Int) -> Unit,
    val authorClick:() -> Unit,
    val commentHintClick:() -> Unit,
    val countClick:(Int) -> Unit,
    val tagClick:(Int) -> Unit):RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    private val HEAD = 1
    private val COMMENT = 2
    private var mFirstHolder: ViewHolder? = null

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        if (position!=0){

        }
    }

    override fun getItemCount(): Int = mComments.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        if (viewType == COMMENT){
            return ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_comment,parent,false))
        }else{
            return ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_details_head,parent,false))

        }


  }


  inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
       fun bindComment(comment: Comment){
           with(comment){
               ImageLoad.frescoLoadCircle(itemView.mCommentAvatarImg,user?.avatar_url.toString())
               itemView.mCommentText.text = Html.fromHtml(body)
               itemView.mNameText.text = user?.name
               itemView.mCommentLikeCount.text = likes_count.toString()
               itemView.mTimeText.text = Utils.formatDateUseCh(Utils.parseISO8601(created_at!!))
           }
       }
  }
}