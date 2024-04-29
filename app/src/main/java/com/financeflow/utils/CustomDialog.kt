package com.financeflow.utils

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog
import com.financeflow.R
import com.financeflow.util.PrefManager


class CustomDialog(var activity: Activity) {

    var dialog: Dialog = Dialog(activity)
    lateinit var pDialog : SweetAlertDialog
    var mypref : PrefManager = PrefManager(activity)

    fun showDialog() {
       // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.customdialog)
        dialog.show()
    }

    fun hideDialog() {
        dialog.dismiss()
    }

    fun showToast(msg : String){
        Toast.makeText(activity,msg,Toast.LENGTH_SHORT).show()
    }

    fun warningDialog(){

        pDialog = SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
        pDialog.setCancelable(false)
        pDialog
            .setTitleText("Please check your internet connection and try again")
            .setConfirmText("Ok")
            .setConfirmClickListener(object : SweetAlertDialog.OnSweetClickListener {
                override fun onClick(sDialog: SweetAlertDialog) {
                    sDialog.dismissWithAnimation()
                }
            })
            .show()
    }

    fun warningDialog1(){

        pDialog = SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
        pDialog.setCancelable(false)
        pDialog
            .setTitleText("Are you sure want to logout?")
            .setConfirmText("Yes")
            .setCancelText("No")
            .setConfirmClickListener(object : SweetAlertDialog.OnSweetClickListener {
                override fun onClick(sDialog: SweetAlertDialog) {
                     mypref.usertoken = ""
                    mypref.profile = ""
                    mypref.capinterest= false
                    mypref.interest=""
                    sDialog.dismissWithAnimation()
                    /*val intent = Intent(activity, LandingActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    activity.startActivity(intent)
                    activity.finish()*/
                }
            })
            .setCancelClickListener {
                it.dismissWithAnimation()
            }
            .show()
    }

    fun hidSystemUI(){

        activity.window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

}