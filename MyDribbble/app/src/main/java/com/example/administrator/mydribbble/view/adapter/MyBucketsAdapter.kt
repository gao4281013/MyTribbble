package com.example.administrator.mydribbble.view.adapter

import android.animation.ObjectAnimator
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.administrator.mydribbble.R
import com.example.administrator.mydribbble.application.App
import com.example.administrator.mydribbble.entity.Bucket
import com.example.administrator.mydribbble.tools.Utils
import com.example.administrator.mydribbble.tools.hasNavigationBar
import com.example.administrator.mydribbble.tools.log
import kotlinx.android.synthetic.main.item_bucket.view.*

/**
 * Created by Administrator on 2017/11/9.
 */
class MyBucketsAdapter(val mBuckets:MutableList<Bucket>,val onClick:(Int)->Unit,val onLongClick:(Int)
-> Unit):RecyclerView.Adapter<MyBucketsAdapter.ViewHolder>() {


  override fun getItemCount(): Int = mBuckets.size

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
     return ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_bucket,parent,false))
  }
  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
     holder.bindMyBuckets(mBuckets[position],position)
     App.instance.hasNavigationBar {
       if (position == mBuckets.size -1){
         val item = holder.itemView.mItemBucket
         val params = item.layoutParams as ViewGroup.MarginLayoutParams
         params.bottomMargin = Utils.dp2px(48).toInt()
         item.layoutParams = params
       }
     }
  }

  inner class ViewHolder(itemview:View):RecyclerView.ViewHolder(itemview) {
     fun bindMyBuckets(bucket: Bucket,position: Int){
        with(bucket){
          itemView.mBucketNameText.text = name
          itemView.mShotCountText.text = "$shots_count shot"
          itemView.mTimeText.text = "Updated ${Utils.formatDateUseCh(Utils.parseISO8601(
              updated_at!!))}"
        }
       itemView.mItemBucket.setOnClickListener {
         onClick.invoke(position)
       }

       itemView.mItemBucket.setOnLongClickListener {
         onLongClick.invoke(position)
         true
       }
     }
}

  fun addItems(buckets:MutableList<Bucket>){
    val position = buckets.size
    mBuckets.addAll(mBuckets)
    notifyItemInserted(position)
  }

  fun addItem(bucket: Bucket,position: Int){
    log("增加$position")
    mBuckets.add(position,bucket)
    notifyItemInserted(position)
  }

  fun deleteItem(position: Int){
    log("删除$position")
    mBuckets.removeAt(position)
    notifyItemRemoved(position)
  }

  private fun addItemAnimation(view: View?){
    view?.let {
      val translationX = ObjectAnimator.ofFloat(it,"translationX",it.width.toFloat(),0f)
      translationX.duration = 500
      translationX.start()
    }
  }

  override fun onViewAttachedToWindow(holder: ViewHolder?) {
    super.onViewAttachedToWindow(holder)
    addItemAnimation(holder?.itemView?.mItemBucket)
  }

}