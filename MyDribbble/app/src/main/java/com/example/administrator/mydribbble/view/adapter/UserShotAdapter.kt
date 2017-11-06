package com.example.administrator.mydribbble.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.administrator.mydribbble.R
import com.example.administrator.mydribbble.entity.Shot

/**
 * Created by Administrator on 2017/11/6.
 */
class UserShotAdapter(val mShots:MutableList<Shot>,val bio:String?,val itemClick:(View,Int) ->
Unit):RecyclerView.Adapter<UserShotAdapter.ViewHolder>() {
  private val BIO = 0
  private val CONTENT = 1
  private val LOAD = 2
  private val mLoadHolder:ViewHolder? = null
  private var  mOldPosition = 0



  override fun getItemCount(): Int = mShots.size +1

  override fun getItemViewType(position: Int): Int = when (position){
    0 -> BIO
    mShots.size -> LOAD
    else -> CONTENT
  }

  override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder  = when (viewType) {
    BIO -> ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.bio_layout, parent, false))
    LOAD -> ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_comment_load, parent, false))
    else -> ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user_shot, parent, false))
  }

  class ViewHolder(itemview:View):RecyclerView.ViewHolder(itemview)
}