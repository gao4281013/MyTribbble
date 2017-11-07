package com.example.administrator.mydribbble.view.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.administrator.mydribbble.R
import com.example.administrator.mydribbble.application.App
import com.example.administrator.mydribbble.entity.Shot
import com.example.administrator.mydribbble.tools.ImageLoad
import com.example.administrator.mydribbble.tools.hasNavigationBar
import kotlinx.android.synthetic.main.bio_layout.view.*
import kotlinx.android.synthetic.main.item_comment_load.view.*
import kotlinx.android.synthetic.main.item_user_shot.view.*

/**
 * Created by Administrator on 2017/11/6.
 */
class UserShotAdapter(val mShots:MutableList<Shot>,val bio:String?,val itemClick:(View,Int) ->
Unit):RecyclerView.Adapter<UserShotAdapter.ViewHolder>() {
  private val BIO = 0
  private val CONTENT = 1
  private val LOAD = 2
  private var mLoadHolder:ViewHolder? = null
  private var  mOldPosition = 0



  override fun getItemCount(): Int = mShots.size +1

  override fun getItemViewType(position: Int): Int = when (position){
    0 -> BIO
    mShots.size -> LOAD
    else -> CONTENT
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    when(getItemViewType(position)){
      BIO -> bindBio(holder)
      LOAD -> {
        App.instance.hasNavigationBar {
          holder.itemView.mNavigationBar.visibility = View.VISIBLE
        }
        mLoadHolder = holder
      }
      else -> bindImg(mShots[position],holder,position)
    }
  }

  private fun bindImg(shot: Shot, holder: ViewHolder, position: Int) {
    ImageLoad.frescoLoadMini(holder.itemView.mContentImg,shot.images?.teaser)
    holder.itemView.mContentImg.setOnClickListener {
      itemClick(holder.itemView.mContentImg,position)
    }
  }

  private fun bindBio(holder: ViewHolder) {
    if (bio.isNullOrBlank()){
      holder.itemView.mBioText.text = App.instance.resources.getString(R.string
          .no_personal_introduction)
    }else{
      holder.itemView.mBioText.text = Html.fromHtml(bio)
    }
  }

  fun showProgress(){
    mLoadHolder?.itemView?.mCommentProgress?.visibility = View.VISIBLE
  }

  fun hideLoadHint(){
    mLoadHolder?.itemView?.mCommentHintText?.visibility = View.GONE
  }

  fun hideProgress(){
    mLoadHolder?.itemView?.mCommentProgress?.visibility = View.GONE
  }

  fun showLoadHint(msgResid:Int){
    mLoadHolder?.let {
      it.itemView.mCommentHintText.visibility = View.VISIBLE
      it.itemView.mCommentHintText.setText(msgResid)
    }
  }

  fun loadError(msgResid: Int,retryListener:() -> Unit){
    mLoadHolder?.let { holder ->
      holder.itemView.mCommentProgress.visibility = View.GONE
      showLoadHint(msgResid)
      holder.itemView.mLoadLayout.setOnClickListener {
        holder.itemView.mCommentProgress.visibility = View.VISIBLE
        holder.itemView.mCommentHintText.visibility = View.GONE
        retryListener()
      }
    }
  }

  override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
    super.onAttachedToRecyclerView(recyclerView)
    val manager = recyclerView.layoutManager
    if (manager is GridLayoutManager){
      manager.spanSizeLookup = object :GridLayoutManager.SpanSizeLookup(){
        override fun getSpanSize(position: Int): Int = when (getItemViewType(position)) {
          BIO, LOAD -> 3
          else -> 1
        }
        }
      }
  }

  override fun onViewAttachedToWindow(holder: ViewHolder) {
    super.onViewAttachedToWindow(holder)
    if (holder.layoutPosition > mOldPosition || holder.layoutPosition ==0){
      addItemAnination(holder.itemView.mContentImg)
      mOldPosition = holder.layoutPosition
    }
  }

  private fun addItemAnination(view: View?) {
    view?.let {
      val scaleX = ObjectAnimator.ofFloat(view,"scaleX",0f,1f)
      val scaleY = ObjectAnimator.ofFloat(view,"scaleY",0f,1f)
      val set = AnimatorSet()
      set.playTogether(scaleX,scaleY)
      set.duration = 500
      set.start()
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder  = when (viewType) {
    BIO -> ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.bio_layout, parent, false))
    LOAD -> ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_comment_load, parent, false))
    else -> ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user_shot, parent, false))
  }

  class ViewHolder(itemview:View):RecyclerView.ViewHolder(itemview)

  fun addItems(shots:MutableList<Shot>){
    val position = mShots.size
    mShots.addAll(shots)
    notifyItemInserted(position)
  }

  fun addItem(position: Int,shot: Shot){
    mShots.add(position,shot)
    notifyItemInserted(position)
  }

  fun deleteItem(position: Int){
    mShots.removeAt(position)
    notifyItemRemoved(position)
  }


}