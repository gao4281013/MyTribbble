package com.example.administrator.mydribbble.view.costumview

import android.graphics.Canvas
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import android.view.ViewGroup
import com.example.administrator.mydribbble.view.adapter.MyBucketsAdapter
import kotlinx.android.synthetic.main.item_bucket.view.*


/**
 * Created by Administrator on 2017/11/9.
 */
class ItemSwipeRemoveCallBack(val onDelete:(Int,View) -> Unit):ItemTouchHelper.Callback() {
  //限制ImageView长度所能增加的最大值
  private val ICON_MAX_SIZE = 50.0
  //ImageView的初始长宽
  private val fixedWidth = 150
  override fun getMovementFlags(recyclerView: RecyclerView?,
      viewHolder: RecyclerView.ViewHolder?): Int {
    if (recyclerView?.layoutManager is GridLayoutManager || recyclerView?.layoutManager is StaggeredGridLayoutManager){
         val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or
             ItemTouchHelper.RIGHT
         val swipeFlags = 0
      return makeMovementFlags(dragFlags,swipeFlags)
    }else{
      val dragFlags= ItemTouchHelper.UP or ItemTouchHelper.DOWN
      val swipeFlags = ItemTouchHelper.START //or ItemTouchHelper.END
      return makeMovementFlags(dragFlags,swipeFlags)
    }

  }

  override fun onChildDraw(c: Canvas?, recyclerView: RecyclerView,
      viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int,
      isCurrentlyActive: Boolean) {
    //仅对侧滑状态下的效果做出改变
    if (actionState === ItemTouchHelper.ACTION_STATE_SWIPE) {
      //如果dX小于等于删除方块的宽度，那么我们把该方块滑出来
      if (Math.abs(dX) <= getSlideLimitation(viewHolder)) {
        viewHolder.itemView.scrollTo((-dX).toInt(), 0)
      }
// else if (Math.abs(dX) <= recyclerView.getWidth() / 2) {
//        val distance = recyclerView.getWidth() / 2 - getSlideLimitation(viewHolder)
//        val factor = ICON_MAX_SIZE / distance
//        var diff = (Math.abs(dX) - getSlideLimitation(viewHolder)) * factor
//        if (diff >= ICON_MAX_SIZE)
//          diff = ICON_MAX_SIZE
//        (viewHolder as MyBucketsAdapter.ViewHolder).itemView.tv_text.text = ""   //把文字去掉
//        viewHolder.itemView.iv_img.visibility = View.VISIBLE  //显示眼睛
//        val params = viewHolder.itemView.iv_img.getLayoutParams() as FrameLayout.LayoutParams
//        params.width = (fixedWidth + diff).toInt()
//        params.height = (fixedWidth + diff).toInt()
//        viewHolder.itemView.iv_img.layoutParams = params
//      }
// 如果dX还未达到能删除的距离，此时慢慢增加“眼睛”的大小，增加的最大值为ICON_MAX_SIZE
    } else {
      //拖拽状态下不做改变，需要调用父类的方法
      super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
  }

  /**
   * 获取删除方块的宽度
   */
  fun getSlideLimitation(viewHolder: RecyclerView.ViewHolder): Int {
    val viewGroup = viewHolder.itemView as ViewGroup
    return viewGroup.getChildAt(1).layoutParams.width
  }

  override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?,
      target: RecyclerView.ViewHolder?): Boolean {
    if (viewHolder?.itemViewType != target?.itemViewType)return false
    return false
  }

  override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
     onDelete(viewHolder.adapterPosition,viewHolder.itemView)
  }

  override fun isLongPressDragEnabled(): Boolean = false

  override fun isItemViewSwipeEnabled(): Boolean = true

  override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
    super.clearView(recyclerView, viewHolder)
    //重置改变，防止由于复用而导致的显示问题
    viewHolder.itemView.scrollX = 0
    (viewHolder as MyBucketsAdapter.ViewHolder).itemView.tv_text.text="左滑删除"
//    val params = viewHolder.itemView.iv_img.layoutParams as
//        FrameLayout.LayoutParams
//    params.width = 150
//    params.height = 150
//    viewHolder.itemView.iv_img.layoutParams = params
//    viewHolder.itemView.iv_img.visibility=View.INVISIBLE
  }
}