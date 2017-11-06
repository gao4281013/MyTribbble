package com.example.administrator.mydribbble.view.costumview.behavior

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import com.example.administrator.mydribbble.tools.Utils

/**
 * Created by Administrator on 2017/11/6.
 */
class BehaviorComment(context:Context,attr:AttributeSet):CoordinatorLayout.Behavior<View>
(context,attr) {
  override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout?, child: View?,
      directTargetChild: View?, target: View?, nestedScrollAxes: Int): Boolean {
    return nestedScrollAxes and ViewCompat.SCROLL_AXIS_VERTICAL !=0
  }

  override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout?, child: View?, target: View?,
      dx: Int, dy: Int, consumed: IntArray?) {
    if (dy >=2){
      hideChild(child)
    }else if (dy <-2){
      showChild(child)
    }
  }

  private fun showChild(child: View?) {
    child?.animate()?.translationY(0f)?.duration = 200
  }

  private fun hideChild(child: View?) {
    child?.animate()?.translationY(child.height + Utils.dp2px(16))?.duration = 200

  }

  override fun layoutDependsOn(parent: CoordinatorLayout?, child: View?,
      dependency: View?): Boolean {
    return dependency is Snackbar.SnackbarLayout
  }

  override fun onDependentViewChanged(parent: CoordinatorLayout?, child: View?,
      dependency: View?): Boolean {
    if (dependency is Snackbar.SnackbarLayout){
      updateTranslate(dependency,child)
    }

    return false
  }

  private fun updateTranslate(dependency: Snackbar.SnackbarLayout, child: View?) {
    val y = Utils.dp2px(48)-dependency.translationY
    if (dependency.left >= dependency.width){
      child?.animate()?.translationY(0f)?.duration = 200
    }else{
      child?.translationY = -y
    }

  }

}