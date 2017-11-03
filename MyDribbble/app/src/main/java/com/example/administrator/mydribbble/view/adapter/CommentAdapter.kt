package com.example.administrator.mydribbble.view.adapter

import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.administrator.mydribbble.R
import com.example.administrator.mydribbble.entity.Comment
import com.example.administrator.mydribbble.entity.Shot
import com.example.administrator.mydribbble.tools.Constant
import com.example.administrator.mydribbble.tools.ImageLoad
import com.example.administrator.mydribbble.tools.Utils
import com.example.administrator.mydribbble.tools.ctx
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.count_info_layout.view.*
import kotlinx.android.synthetic.main.detail_user.view.*
import kotlinx.android.synthetic.main.item_comment.view.*
import kotlinx.android.synthetic.main.item_comment_load.view.*
import kotlinx.android.synthetic.main.item_details_head.view.*

/**
 * Created by Administrator on 2017/10/13.
 */
class CommentAdapter(val mShot: Shot,
    val mComments: MutableList<Comment>,
    val likeClick: (View, Int) -> Unit,
    val userClick: (View, Int) -> Unit,
    val authorClick: () -> Unit,
    val commentHintClick: () -> Unit,
    val countClick: (Int) -> Unit,
    val tagClick: (Int) -> Unit) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
  private val HEAD = 1
  private val COMMENT = 2
  private var mFirstHolder: ViewHolder? = null

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    if (position != 0) {
      holder.bindComment(mComments[position])
      bindCommentEvent(holder, position)
    } else {
      holder.bindShotInfo(mShot)
      mShot.tags?.let {
        if (mShot.tags!!.isNotEmpty()) {
          mountTags(mShot.tags!!, holder)
        }
      }
      mFirstHolder = holder
    }
  }

  fun showProgress() {
    mFirstHolder?.itemView?.mCommentProgress?.visibility = View.VISIBLE
  }

  fun hideProgress() {
    mFirstHolder?.itemView?.mCommentProgress?.visibility = View.GONE
  }

  fun hideCommentHint() {
    mFirstHolder?.itemView?.mCommentHintText?.visibility = View.GONE
  }

  fun showCommentHint(msgResId: Int) {
    mFirstHolder?.let {
      it.itemView.mCommentHintText.visibility = View.VISIBLE
      it.itemView.mCommentHintText.setText(msgResId)
    }
  }


  fun addItem(position: Int, comment: Comment) {
    mComments.add(comment)
    notifyItemInserted(position)
  }

  fun addItems(comments: MutableList<Comment>) {
    val position = 1
    mComments.addAll(comments)
    notifyItemInserted(position)
  }

  /**
   * 绑定标签数据
   * */
  private fun mountTags(tags: List<String>, holder: ViewHolder) {
    holder.itemView.mTagLayout.visibility = View.VISIBLE
    holder.itemView.mTags.adapter = object : TagAdapter<String>(tags) {
      override fun getView(p0: FlowLayout, p1: Int, p2: String?): View {
        val mTagBtn = LayoutInflater.from(p0.ctx).inflate(R.layout.tag_layout, holder
            .itemView.mTags, false) as TextView
        if (p1 != tags.size - 1)
          mTagBtn.text = "$tags, " else mTagBtn.text = toString()
        mTagBtn.setOnClickListener {
          tagClick(p1)
        }
        return mTagBtn
      }
    }
  }

  private fun bindCommentEvent(holder: ViewHolder, position: Int) {
    holder.itemView.mCommentAvatarImg.setOnClickListener {
      userClick(it, position)
    }

    holder.itemView.mCommentLikeBtn.setOnClickListener {
      likeClick(holder.itemView.mLikeImg, position)
    }
  }

  override fun getItemCount(): Int = mComments.size

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    if (viewType == COMMENT) {
      return ViewHolder(
          LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false))
    } else {
      return ViewHolder(
          LayoutInflater.from(parent.context).inflate(R.layout.item_details_head, parent, false))
    }
  }


  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindComment(comment: Comment) {
      with(comment) {
        ImageLoad.frescoLoadCircle(itemView.mCommentAvatarImg, user?.avatar_url.toString())
        itemView.mCommentText.text = Html.fromHtml(body)
        itemView.mNameText.text = user?.name
        itemView.mCommentLikeCount.text = likes_count.toString()
        itemView.mTimeText.text = Utils.formatDateUseCh(Utils.parseISO8601(created_at!!))
      }
    }

    fun bindShotInfo(shot: Shot) {
      with(shot) {
        itemView.mTitleText.text = title
        itemView.mAuthorText.text = user?.name
        if (description != null && !"".equals(description)) {
          itemView.mDescriptionText.text = Html.fromHtml(description)
        }
        ImageLoad.frescoLoadCircle(itemView.mAvaterImg, user?.avatar_url.toString())
        itemView.mLikeCountText.text = likes_count.toString()
        itemView.mBucketCountText.text = buckets_count.toString()
        itemView.mViewsCountText.text = views_count.toString()
        itemView.mCommentCountText.text = comments_count.toString()
        itemView.mAttachmentCountText.text = attachments_count.toString()
      }

      itemView.mCommentHintText.setOnClickListener { commentHintClick }
      itemView.mAuthorLayout.setOnClickListener { authorClick }
      itemView.mLikeCountBtn.setOnClickListener { countClick(Constant.DETAILS_EVENT_LIKE_COUNT) }
      itemView.mAttachmentCountBtn.setOnClickListener {
        countClick(Constant.DETAILS_EVENT_AYYACHMENT_COUNT)
      }
      itemView.mBucketCountBtn.setOnClickListener {
        countClick(Constant.DETAILS_EVENT_BUCKET_COUNT)
      }
    }
  }

  override fun getItemViewType(position: Int): Int = if (position == 0) HEAD else COMMENT

}