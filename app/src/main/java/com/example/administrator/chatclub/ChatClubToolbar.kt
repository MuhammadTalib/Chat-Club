package com.example.administrator.chatclub


import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

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

