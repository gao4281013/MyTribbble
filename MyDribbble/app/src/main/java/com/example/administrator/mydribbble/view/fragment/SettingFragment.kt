package com.example.administrator.mydribbble.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceFragment
import android.preference.PreferenceScreen
import android.support.v4.app.Fragment
import com.example.administrator.mydribbble.R
import com.example.administrator.mydribbble.tools.RxHelper
import com.example.administrator.mydribbble.tools.Utils
import com.example.administrator.mydribbble.view.activity.LisenseActivity
import com.tencent.bugly.beta.Beta
import org.jetbrains.anko.toast
import rx.Observable
import java.io.File

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SettingFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SettingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingFragment : PreferenceFragment() {
   val KEY_VERSION = "version"
   val KEY_CLEAR_CACHE = "clearCache"
   val KEY_LISENSE = "lisense"

  private val mVersionItem:PreferenceScreen by lazy {
    findPreference(KEY_VERSION) as PreferenceScreen
  }

  private val mClearItem:PreferenceScreen by lazy {
    findPreference(KEY_CLEAR_CACHE) as PreferenceScreen
  }

  private val mLisenseItem:PreferenceScreen by lazy {
    findPreference(KEY_LISENSE) as PreferenceScreen
  }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    addPreferencesFromResource(R.xml.preference)
    init()
  }

  private fun init() {
     mVersionItem.summary = Utils.getVersion(activity)
    Observable.create<String>{ subscribe ->
      subscribe.onNext(Utils.formatFileSize(getFolderSize(activity.externalCacheDir).toDouble()))
      subscribe.onCompleted()
    }.compose(RxHelper.singleModeThreadNormal())
        .subscribe({ size ->
          mClearItem.summary = size
        },Throwable::printStackTrace)
  }

  /**
   * 获取文件大小
   * Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
   * Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
   * */
  fun getFolderSize(file:File):Long{
    var size:Long = 0
    try {
      val fileList = file.listFiles()
      for (i in fileList.indices) {
         //如果下面还有文件
        if (fileList[i].isDirectory){
          size+=getFolderSize(fileList[i])
        }else{
          size += fileList[i].length()
        }
      }
    }catch (e:Exception){
      e.printStackTrace()
    }
    return size
  }

  override fun onStart() {
    super.onStart()
    bindEvent()
  }

  private fun bindEvent() {
    mVersionItem.setOnPreferenceClickListener {
      Beta.checkUpgrade(true,false)
      true
    }

    mClearItem.setOnPreferenceClickListener {
      Utils.deleteFolderFile(activity.externalCacheDir.toString(),true)
      toast(R.string.clear_success)
      true
    }
    mLisenseItem.setOnPreferenceClickListener {
      startActivity(Intent(activity, LisenseActivity::class.java))
      true
    }
  }


  override fun onAttach(context: Context?) {
    super.onAttach(context)

  }
}