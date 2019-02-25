package com.example.administrator.chatclub.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.DisplayMetrics
import android.view.Gravity
import com.example.administrator.chatclub.R
import kotlinx.android.synthetic.main.dialog_view.*

class dialog(var context: Activity,
             var dialogmsg:String,
             var okbtn:String,
             var cancelbtn:String,
             val onokclick:()->Unit,
             val oncancelClicked:()->Unit) : Dialog(context){

    init {

        setContentView(R.layout.dialog_view)
        val display = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(display)

        window?.setBackgroundDrawable(ColorDrawable(android.R.color.transparent))
        window?.setGravity(Gravity.BOTTOM)
        window?.attributes?.windowAnimations = R.style.dialog_animation

        dialog_msg.text=dialogmsg
        ok.text=okbtn
        cancel.text=cancelbtn

        ok.setOnClickListener {
            onokclick()
        }
        cancel.setOnClickListener {
            dismiss()
            oncancelClicked()
        }


    }



}