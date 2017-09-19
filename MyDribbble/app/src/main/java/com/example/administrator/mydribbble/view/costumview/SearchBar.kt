package com.example.administrator.mydribbble.view.costumview

import android.animation.Animator
import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateInterpolator
import com.example.administrator.mydribbble.tools.Utils
import org.w3c.dom.Attr

/**
 * Created by Administrator on 2017/9/12.
 */
class SearchBar(context: Context?,attrs:AttributeSet?) : CardView(context, attrs) {
    val SEARCH_DURATION_ANIM:Long = 300

    fun showSearchView(width:Int,animationEnd:() -> Unit){
          val cx = width - Utils.dp2px(24)
        val cy = Utils.dp2px(24)
        val dx = Math.max(cx,width-cx)
        val dy = Math.max(cy,Utils.dp2px(48) - cy)
        val finalRadius = Math.hypot(dx.toDouble(),dy.toDouble()).toFloat()
        val animator = ViewAnimationUtils.createCircularReveal(this,cx.toInt(),cy.toInt(),0f,finalRadius)
        animator.interpolator = AccelerateInterpolator()
        animator.duration = SEARCH_DURATION_ANIM
        visibility = View.VISIBLE
        alpha = 1F
        animator.start()
        animator.addListener(object :Animator.AnimatorListener{

            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                animationEnd.invoke()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }
        })

    }


    fun hideSearchView(animationEnd: () -> Unit){
        val  cx = measuredWidth
        val cy=Utils.dp2px(24)
        val dx = Math.max(cx,measuredWidth - cx)
        val dy = Math.max(cy,Utils.dp2px(48) - cy)
        val finalRadius = Math.hypot(dx.toDouble(),dy.toDouble()).toFloat()
        val animator = ViewAnimationUtils.createCircularReveal(this,cx,cy.toInt(),finalRadius,0f)
        animator.interpolator = AccelerateInterpolator()
        animator.duration = SEARCH_DURATION_ANIM
        animator.start()

        animator.addListener(object :Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                  alpha = 0f
                  visibility = View.GONE
                  animationEnd.invoke()
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }
        })
    }


}