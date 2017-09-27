package com.example.administrator.mydribbble.view.fragment

import android.app.Fragment
import android.app.FragmentManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v13.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.administrator.mydribbble.R
import com.example.administrator.mydribbble.event.OpenDrawerEvent
import com.example.administrator.mydribbble.tools.startSpeak
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.search_layout.*
import org.greenrobot.eventbus.EventBus

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [HomeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : BaseFragment() {
    val TITLE_RECENT = "RECENT"
    val TITLE_POPULAR = "POPULAR"
    val TITLE_FOLLOWING = "FOLLOWING"

    private val mRecentFragment : ShotsFragment by lazy {
        ShotsFragment.newInstance(ShotsFragment.RECENT)
    }

    private val mPopularFragment:ShotsFragment by lazy {
        ShotsFragment.newInstance()
    }

    private val mFollingFragment:FollowingFragment by lazy {
        FollowingFragment()
    }

    private lateinit var mFragments:MutableList<Fragment>
    private val mTitles:MutableList<String> by lazy {
        mutableListOf(TITLE_POPULAR,TITLE_RECENT)
    }

    private var mPagerAdapter : PagerAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        init()
        return LayoutInflater.from(activity).inflate(R.layout.fragment_home,null)
    }

    private fun init() {
        mFragments = arrayListOf(mPopularFragment,mRecentFragment)
    }

    override fun onBackPresses() {
        hideSearcgView()
    }

    private fun hideSearcgView() {
        mSearch_layout.hideSearchView {
            isShowSearchBar = false
        }
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        initView()
        loadPager()
    }

    private fun loadPager() {
        mPagerAdapter = PageAdapter(childFragmentManager)
        mViewPager.adapter = mPagerAdapter
        mTabLayout.setViewPager(mViewPager)
    }

    private fun initView() {

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }


    override fun onStart() {
        super.onStart()
        bindEvent()
    }

    private fun bindEvent() {
        tool_bar.setNavigationOnClickListener {
             EventBus.getDefault().post(OpenDrawerEvent())
        }

        tool_bar.setOnMenuItemClickListener { menu ->
            when(menu.itemId){
                R.id.mSearch -> mSearch_layout.showSearchView(tool_bar.width,{
                    isShowSearchBar = true
                })
            }
            true
        }

        mBackBtn.setOnClickListener {
            hideSearcgView()
        }

        mSearchBtn.setOnClickListener {
            search()
        }

        mVoiceBtn.setOnClickListener { startSpeak()
        }

        mSearchEdit.setOnKeyListener { _, keyCode, event ->

            if (event.action == KeyEvent.ACTION_DOWN) {//判断是否为点按下去触发的事件，如果不写，会导致该案件的事件被执行两次
                when (keyCode) {
                    KeyEvent.KEYCODE_ENTER -> search()
                }
            }
           false
        }

    }



    private fun search() {

    }



    override fun onKeyDown(keyCode: Int, event: KeyEvent?) {
        super.onKeyDown(keyCode, event)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }



    //
    inner class PageAdapter(fm: FragmentManager?): FragmentStatePagerAdapter(fm){
        override fun getItem(position: Int): Fragment = mFragments[position]

        override fun getCount(): Int = mFragments.size

        override fun getPageTitle(position: Int): CharSequence {
            return mTitles[position]
        }
    }

}
