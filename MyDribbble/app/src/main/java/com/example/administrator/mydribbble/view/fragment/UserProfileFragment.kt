package com.example.administrator.mydribbble.view.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.administrator.mydribbble.R

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [UserProfileFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [UserProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserProfileFragment : Fragment() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return inflater!!.inflate(R.layout.fragment_user_profile, container, false)
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
  }

  override fun onDetach() {
    super.onDetach()
  }

}
