package com.example.administrator.mydribbble.tools

import android.Manifest
import com.example.administrator.mydribbble.application.App
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloader
import com.tbruyelle.rxpermissions.RxPermissions

/**
 * Created by Administrator on 2017/10/20.
 */
class DownloadUtil {
  companion object {
    fun DownloadImg(url:String,path:String){
       RxPermissions.getInstance(App.instance).request(Manifest.permission.READ_EXTERNAL_STORAGE,
           Manifest.permission.WRITE_EXTERNAL_STORAGE)
           .subscribe({aBoolean ->
             if (aBoolean){
               download(url,path)
             }
           },Throwable::printStackTrace)
    }

    private fun download(url: String, path: String) {
       toast("开始下载")
      FileDownloader.getImpl().create(url)
          .setPath(path)
          .setListener(object : FileDownloadListener(){
            override fun warn(p0: BaseDownloadTask?) {

            }

            override fun completed(p0: BaseDownloadTask?) {
              toast("下载完成")
            }

            override fun pending(p0: BaseDownloadTask?, p1: Int, p2: Int) {
            }

            override fun error(p0: BaseDownloadTask?, p1: Throwable?) {
              toast("下载失败")
            }

            override fun progress(p0: BaseDownloadTask?, p1: Int, p2: Int) {
            }

            override fun paused(p0: BaseDownloadTask?, p1: Int, p2: Int) {
            }
          }).start()

    }
  }
}