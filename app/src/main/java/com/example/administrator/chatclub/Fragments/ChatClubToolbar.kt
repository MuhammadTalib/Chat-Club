package com.example.administrator.chatclub.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.administrator.chatclub.R

class ChatClubToolbar : Fragment() {

  override fun onCreateView(
          inflater: LayoutInflater, container: ViewGroup?,
          savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_chat_club_toolbar, container, false)
  }
  fun MessengerClick(view: View)
  {
  }


}

