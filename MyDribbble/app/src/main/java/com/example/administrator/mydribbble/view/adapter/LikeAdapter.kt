package com.example.administrator.mydribbble.view.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.example.administrator.mydribbble.R
import com.example.administrator.mydribbble.application.App
import com.example.administrator.mydribbble.entity.Like
import com.example.administrator.mydribbble.entity.Shot
import com.example.administrator.mydribbble.tools.ImageLoad
import com.example.administrator.mydribbble.tools.Utils
import com.example.administrator.mydribbble.tools.hasNavigationBar
import kotlinx.android.synthetic.main.item_card_bottom.view.*
import kotlinx.android.synthetic.main.item_card_head.view.*
import kotlinx.android.synthetic.main.item_shots.view.*
import kotlinx.android.synthetic.main.pull_up_load_layout.view.*

/**
 * Created by Administrator on 2017/11/10.
 */
class LikeAdapter(var mLikes:MutableList<Like>,val itemClick:(View,Int) -> Unit,val userClick:
(View,Int) -> Unit) : RecyclerView.Adapter<LikeAdapter.ViewHolder>() {
   val NORMAL = 0
   val LOAD_MORE = 1
   val CARD_TAB_DURATION:Long = 100
   private var mListViewHolder:ViewHolder? = null


  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       if (position == mLikes.size){
          App.instance.hasNavigationBar {
            holder.itemView.mNavigationBar.visibility = View.VISIBLE
          }
         mListViewHolder = holder
       }else{
         holder.bindShot(mLikes[position].shot)
         holder.itemView.mItemCard.setOnClickListener {
           itemClick.invoke(holder.itemView.mItemCard,position)
         }

         holder.itemView.mHeadLayout.setOnClickListener {
           userClick.invoke(holder.itemView.mHeadLayout,position)
         }
         addCardAnimation(holder.itemView.mItemCard)
       }
  }

  override fun onViewAttachedToWindow(holder: ViewHolder?) {
    super.onViewAttachedToWindow(holder)
    addItemAnimation(holder?.itemView?.mItemCard)
  }

  private fun addItemAnimation(mItemCard:CardView?){
    mItemCard?.let {
      val scaleX = ObjectAnimator.ofFloat(it,"scaleX",0.5f,1f)
      val scaleY = ObjectAnimator.ofFloat(it,"scaleY",0.5f,1f)
      val set = AnimatorSet()
      set.playTogether(scaleX,scaleY)
      set.duration = 500
      set.start()
    }
  }

  fun loadError(retyrListener:() -> Unit){
    mListViewHolder?.let { holder ->
      holder.itemView.mRetryLoadProgress.visibility = View.GONE
      holder.itemView.mRetryText.visibility = View.VISIBLE
      holder.itemView.mLoadLayout.setOnClickListener{
        holder.itemView.mRetryLoadProgress.visibility = View.VISIBLE
        holder.itemView.mRetryText.visibility = View.GONE
        retyrListener()
      }

    }
  }

  fun hideProgress(){
    mListViewHolder?.itemView?.mLoadLayout?.visibility = View.VISIBLE
  }

  fun addItems(likes:MutableList<Like>){
    val position = mLikes.size
    mLikes.addAll(likes)
    notifyItemInserted(position)
  }

  fun getSize():Int = mLikes.size

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
    if (viewType == NORMAL){
      return ViewHolder(
          LayoutInflater.from(parent?.context).inflate(R.layout.item_shots,parent,false))
    }else{
      return ViewHolder(
          LayoutInflater.from(parent?.context).inflate(R.layout.pull_up_load_layout,parent,false))
    }
  }

  override fun getItemCount(): Int = mLikes.size+1

  override fun getItemViewType(position: Int): Int = if (position == mLikes.size) LOAD_MORE else NORMAL


  inner class ViewHolder(itemview:View) : RecyclerView.ViewHolder(itemview){
         fun bindShot(shot:Shot){
           with(shot){
             ImageLoad.frescoLoadCircle(itemView.mAvatarImg,user?.avatar_url.toString())
             ImageLoad.frescoLoadNormal(itemView.mContentImg,itemView.mImgProgress,images?.normal
                 .toString(),images?.teaser.toString())
             itemView.mTitleText.text = title
             itemView.mAuthorText.text = user?.name
             itemView.mLikeCountText.text = likes_count.toString()
             itemView.mCommentCountText.text = comments_count.toString()
             itemView.mViewsCountText.text = views_count.toString()
             itemView.mImgProgress.visibility = View.VISIBLE
             if (shot.animated) itemView.mGifTag.visibility = View.VISIBLE
             else itemView.mGifTag.visibility = View.GONE

             if (shot.rebounds_count>0){
               itemView.mReboundCountText.visibility = View.VISIBLE
               itemView.mReboundCountText.text = rebounds_count.toString()
             }else{
               itemView.mRoundLayout.visibility = View.GONE
             }

             if (shot.attachments_count>0){
               itemView.mAttachmentLayout.visibility = View.VISIBLE
               itemView.mAttachmentCountText.text = attachments_count.toString()
             }else{
               itemView.mAttachmentLayout.visibility = View.GONE
             }
           }
         }
  }


  fun  addCardAnimation(mItemCardView: CardView?){
    mItemCardView?.setOnTouchListener { _, event ->
      when(event.action) {
        MotionEvent.ACTION_DOWN -> mItemCardView.animate().translationZ(
            Utils.dp2px(24)).duration = CARD_TAB_DURATION
        MotionEvent.ACTION_CANCEL -> mItemCardView.animate().translationZ(
            0f).duration = CARD_TAB_DURATION
        MotionEvent.ACTION_UP -> mItemCardView.animate().translationZ(
            0f).duration = CARD_TAB_DURATION
      }
      false
    }
  }

}