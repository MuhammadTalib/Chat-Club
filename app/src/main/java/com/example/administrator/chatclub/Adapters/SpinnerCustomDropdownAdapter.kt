package com.example.administrator.chatclub.Adapters

import android.content.Context
import android.support.v4.widget.CircularProgressDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.administrator.chatclub.CountriesResponse
import com.example.administrator.chatclub.Models.Country
import com.example.administrator.chatclub.R
import java.util.*

class SpinnerCustomDropdownAdapter (var con: Context, var listItemsTxt: ArrayList<CountriesResponse>): BaseAdapter()
{
     val mInflater: LayoutInflater = LayoutInflater.from(con)
     override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View
     {
         val view: View
         val vh: ItemRowHolder
         if (convertView == null)
         {
             view = mInflater.inflate(R.layout.spinner_country_drop_down_item_view, parent, false)
             vh = ItemRowHolder(view)
             view?.tag = vh
         }
         else
         {
             view = convertView
             vh = view.tag as ItemRowHolder
         }
         val params = view.layoutParams
         params.height = 70
         view.layoutParams = params
         val userData = listItemsTxt[position]
     // if(userData.flag!=null) Log.e("hahaha",userData.flag)
      //else Log.e("hahaha","is null")
         vh.name.text = userData.name
    //vh.flag.setImageResource(userData.flagImage)
    /*Glide.with(view.context).applyDefaultRequestOptions(RequestOptions().apply {
      placeholder(CircularProgressDrawable(view.context).apply {
        strokeWidth = 2f
        centerRadius = 2f
        start()
      })
    }).load(userData.flag).into(vh.flag)
*/
        return view
    }

     override fun getItem(position: Int): Any? {
         return null
     }
     override fun getItemId(position: Int): Long {
         return 0
     }
     override fun getCount(): Int {
        return listItemsTxt.size
     }

     private class ItemRowHolder(view: View?) {
         val flag: ImageView=view?.findViewById(R.id.countryflag) as ImageView
         val name: TextView=view?.findViewById(R.id.countryname) as TextView
     }
}