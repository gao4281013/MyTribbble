package com.example.administrator.mydribbble.view.costumview

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View

/**
 * Created by Administrator on 2017/11/9.
 */
class ItemSwipeRemoveCallBack(val onDelete:(Int,View) -> Unit):ItemTouchHelper.Callback() {

  override fun getMovementFlags(recyclerView: RecyclerView?,
      viewHolder: RecyclerView.ViewHolder?): Int {
    if (recyclerView?.layoutManager is GridLayoutManager || recyclerView?.layoutManager is StaggeredGridLayoutManager){
         val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or
             ItemTouchHelper.RIGHT
         val swipeFlags = 0
      return makeMovementFlags(dragFlags,swipeFlags)
    }else{
      val dragFlags= ItemTouchHelper.UP or ItemTouchHelper.DOWN
      val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
      return makeMovementFlags(dragFlags,swipeFlags)
    }

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

  override fun clearView(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?) {
    super.clearView(recyclerView, viewHolder)
  }
}