package com.example.administrator.chatclub.Activities

import android.os.Bundle
import android.support.design.widget.TabLayout
import com.example.administrator.chatclub.Adapters.MypagerAdapter
import com.example.administrator.chatclub.Base.BaseActivity
import com.example.administrator.chatclub.Fragments.ChatList
import com.example.administrator.chatclub.Fragments.MakeFriend
import com.example.administrator.chatclub.Fragments.OptionPage
import com.example.administrator.chatclub.Fragments.UserProfile
import com.example.administrator.chatclub.R
import kotlinx.android.synthetic.main.activity_fragments_holder.*

class FragmentsHolder : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragments_holder)

        myPager.adapter = MypagerAdapter(supportFragmentManager, arrayListOf(

                ChatList(),
                UserProfile(),
                MakeFriend(),
                OptionPage()

        ))
        val tabLayout = findViewById(R.id.myTabs) as TabLayout
        tabLayout.setupWithViewPager(myPager)

    }
}
