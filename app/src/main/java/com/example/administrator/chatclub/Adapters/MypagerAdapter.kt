package com.example.administrator.chatclub.Adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class MypagerAdapter(fm: FragmentManager, val pages:ArrayList<Fragment>) : FragmentPagerAdapter(fm){
    override fun getItem(position: Int): Fragment = pages[position]
    override fun getCount(): Int = pages.size
  //  override fun getPageTitle(position: Int): CharSequence? = "Hello"

    private val NUM_ITEMS = 3

 /*   fun MyPagerAdapter(fragmentManager: FragmentManager) {
        super(fragmentManager)
    }
   */

 /*   // Returns the fragment to display for that page
    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 // Fragment # 0 - This will show FirstFragment
            -> return FirstFragment.newInstance(0, "Page # 1")
            1 // Fragment # 0 - This will show FirstFragment different title
            -> return FirstFragment.newInstance(1, "Page # 2")
            2 // Fragment # 1 - This will show SecondFragment
            -> return SecondFragment.newInstance(2, "Page # 3")
            else -> return null
        }
    }
*/
    // Returns the page title for the top indicator
    override fun getPageTitle(position: Int): CharSequence? {
        return "Page $position"
    }

}