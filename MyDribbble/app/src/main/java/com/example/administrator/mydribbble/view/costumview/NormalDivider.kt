package com.example.administrator.mydribbble.view.costumview

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import com.example.administrator.mydribbble.application.App

/**
 * Created by Administrator on 2017/11/9.
 */
class NormalDivider:RecyclerView.ItemDecoration() {
  private val mDivider:Drawable

  init {
    val typeArray = App.instance.obtainStyledAttributes(attrs)//将数组转化为TypedArray
    mDivider = typeArray.getDrawable(0) //取出这个drawable文件
    typeArray.recycle() //回收typeArray
  }

  override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
    super.onDrawOver(c, parent, state)
    val childCount = parent.childCount
    if (childCount != 0){
      for (i in 0..childCount-1){
        if (i != childCount - 1){
          val child = parent.getChildAt(i)
          val params = child.layoutParams as RecyclerView.LayoutParams
          val top = child.bottom + params.bottomMargin
          val bottom = top + mDivider.intrinsicHeight
          mDivider.alpha = 100
          mDivider.setBounds(0,top,parent.width,bottom)
          mDivider.draw(c)
        }
      }
    }
  }

  companion object {
    private val attrs = intArrayOf(android.R.attr.listDivider)//系统自带分割线文件，获取后先保存为数组
  }
}