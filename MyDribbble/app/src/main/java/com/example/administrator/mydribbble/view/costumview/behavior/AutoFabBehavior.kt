package com.example.administrator.mydribbble.view.costumview.behavior

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import android.view.animation.OvershootInterpolator

/**
 * Created by Administrator on 2017/11/8.
 * 让我们自定义的FloatingActionButton（AutoHideFab）响应CoordinatorLayout
 * 与RecyclerView协同工作的类
 */
class AutoFabBehavior (context: Context, attrs: AttributeSet) : CoordinatorLayout.Behavior<View>(context, attrs) {
   private var mAutoHideFab:FloatingActionButton? = null
  private var mStatus:Boolean = false


  override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout?, child: View?,
      directTargetChild: View?, target: View?, nestedScrollAxes: Int): Boolean {
    return nestedScrollAxes and ViewCompat.SCROLL_AXIS_VERTICAL!=0
  }

  override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout?, child: View?, target: View?,
      dx: Int, dy: Int, consumed: IntArray?) {
    if (mAutoHideFab ==  null){
      mAutoHideFab = child as FloatingActionButton?
    }

    if (dy>10){
      hide()
    }else if(dy <10){
      show()
    }
  }

  fun show(){
     if (!mStatus){
       val scaleX = ObjectAnimator.ofFloat(mAutoHideFab,"scaleX",0f,1f)
       val scaleY = ObjectAnimator.ofFloat(mAutoHideFab,"scaleY",0f,1f)
       val alpha = ObjectAnimator.ofFloat(mAutoHideFab,"alpha",0f,1f)

       val animatorSet = AnimatorSet()
       animatorSet.playTogether(scaleX,scaleY,alpha)
       animatorSet.duration = 300
       animatorSet.interpolator = OvershootInterpolator()
       animatorSet.start()

       mStatus = true
     }
  }

  fun hide(){
    if (mStatus){
      val scaleX = ObjectAnimator.ofFloat(mAutoHideFab,"scaleX",1f,0f)
      val scaleY = ObjectAnimator.ofFloat(mAutoHideFab,"scaleY",1f,0f)
      val alpha = ObjectAnimator.ofFloat(mAutoHideFab,"alpha",1f,0f)

      val animatorSet = AnimatorSet()
      animatorSet.playTogether(scaleX,scaleY,alpha)
      animatorSet.duration = 300
      animatorSet.interpolator = OvershootInterpolator()
      animatorSet.start()

      mStatus = true
    }
  }

}