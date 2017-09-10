package com.example.administrator.mydribbble.view.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.administrator.mydribbble.R
import com.example.administrator.mydribbble.tools.toast
import kotlinx.android.synthetic.main.create_bucket_dialog.view.*

/**
 * Created by Administrator on 2017/9/9.
 */
class DialogManager(private val mContext: Context) {
    private var mDialog:Dialog?=null

    /**
     * 圆形进度条dialog
     * @param text
     **/

    fun showCircleProgressDialog(text:String=mContext.resources.getString(R.string.please_wait)){
        val dialog = ProgressDialog(mContext)
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        dialog.isIndeterminate = true
        dialog.setMessage(text)
        dialog.show()
        mDialog = dialog
    }

    fun showOptionDialog(title:String,
                         content:String,
                         CancelText:String ="Cancel",
                         confirmText:String = "Confirm",
                         onConfim:() -> Unit,
                         onCancel:() -> Unit){
       val dialog = AlertDialog.Builder(mContext)
        dialog.setTitle(title).setMessage(content).setPositiveButton(confirmText){
            _,_->
           onConfim()
        }.setNegativeButton(CancelText){
            _,_->
            onCancel.invoke()
        }.setCancelable(false)
        mDialog = dialog.show()
    }

    fun showEditBucketDialog(name:String = "",description:String?="",title: String?=mContext.resources.getString(R.string.create_bunket),onConfim: (String,String?) -> Unit) {
        val dialog = AlertDialog.Builder(mContext)
        val view = LayoutInflater.from(mContext).inflate(R.layout.create_bucket_dialog, null)
        view.mBucketNameEdit.setText(name)
        view.mBucketDesrciptionEdit.setText(description)
        dialog.setTitle(title).setPositiveButton(mContext.resources.getString(R.string.create)) {
            _, _ ->
            if (!view.mBucketNameEdit.text.isNullOrBlank()) {
                onConfim(view.mBucketNameEdit.text.toString(), view.mBucketDesrciptionEdit.text.toString())
            } else {
                toast(R.string.bucket_name_null)
            }
        }.setNegativeButton(R.string.cancel, null).setView(view)
        mDialog = dialog.show()
    }

    fun dismissAll(){
        if (mDialog!=null && mDialog!!.isShowing){
            mDialog!!.dismiss()
            mDialog = null
        }
    }

    val isShowing:Boolean
    get() = mDialog!!.isShowing

}