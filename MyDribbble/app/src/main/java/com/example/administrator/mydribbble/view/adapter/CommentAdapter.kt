package com.example.administrator.mydribbble.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.administrator.mydribbble.entity.Comment
import com.example.administrator.mydribbble.entity.Shot

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
   private val HEAD =1
   private val COMMENT = 2
   private var  mFirstHolder:ViewHolder? = null

  override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

  }

  override fun getItemCount(): Int = mComments.size

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
  }


  inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

  }
}