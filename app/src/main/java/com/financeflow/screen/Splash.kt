package com.financeflow.screen

import CommonViewModel
import Status
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.financeflow.R
import com.financeflow.model.LoginData
import com.financeflow.util.PrefManager

import com.google.gson.Gson



class Splash : AppCompatActivity() {

    lateinit var mypref: PrefManager
    lateinit var gson : Gson
    lateinit var  obj: LoginData
    lateinit var viewModel: CommonViewModel
    var cbanner : Int = 0
    var title : String = ""
    var messageBody : String = ""
    var celbname : String = ""
    var image : String = ""
    var categ : String = ""
    var id : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.splash)
        mypref = PrefManager(this)
        gson = Gson()



        myhandle()


    }

        fun myhandle(){

        val handler = Handler()
        handler.postDelayed({

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)


    }


}