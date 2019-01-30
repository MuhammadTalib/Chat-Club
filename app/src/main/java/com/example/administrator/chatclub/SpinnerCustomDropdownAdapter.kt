package com.example.administrator.chatclub

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import java.util.*

class SpinnerCustomDropdownAdapter (con: Context, var listItemsTxt: ArrayList<Country>): BaseAdapter()
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
        params.height = 60
        view.layoutParams = params
        val userData = listItemsTxt[position]
        vh.name.text = userData.name
        vh.flag.setImageResource(userData.flagImage)
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

    private class ItemRowHolder(row: View?) {

        val flag: ImageView
        init {
            this.flag=row?.findViewById(R.id.countryflag) as ImageView
        }

        val name: TextView
        init {
            this.name = row?.findViewById(R.id.countryname) as TextView
        }
    }
}