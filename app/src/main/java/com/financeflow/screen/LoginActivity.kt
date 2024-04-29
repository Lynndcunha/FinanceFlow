package com.financeflow.screen

import CommonViewModel
import Status
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.financeflow.R
import com.financeflow.application.Myapp
import com.financeflow.base.BaseActivity
import com.financeflow.model.LoginReqModel
import com.financeflow.model.SignupReqModel
import com.financeflow.util.PrefManager
import com.financeflow.util.ValidationUtils
import com.financeflow.utils.CustomDialog
import com.financeflow.utils.NetworkUtil
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class LoginActivity :BaseActivity(), View.OnClickListener {

    lateinit var viewModel: CommonViewModel
    lateinit var dialog: CustomDialog
    lateinit var mypref: PrefManager
    private val RC_SIGN_IN = 1
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private var callbackManager: CallbackManager? = null
    lateinit var loginManager: LoginManager
    lateinit var validation: ValidationUtils
    lateinit var gson : Gson
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
        mypref = PrefManager(this)
        dialog = CustomDialog(this)
         gson = Gson()
        dialog.hidSystemUI()




        validation = ValidationUtils(this)

        //txt_back.setOnClickListener { onClick(txt_back) }
        txt_forgotpass.setOnClickListener { onClick(txt_forgotpass) }
        txt_signup.setOnClickListener { onClick(txt_signup) }
        btn_login.setOnClickListener { onClick(btn_login) }
        //fb.setOnClickListener { onClick(fb) }
        //google.setOnClickListener { onClick(google) }

        /*txt_link1.setOnClickListener {
            txt_link1.setMovementMethod(LinkMovementMethod.getInstance());

        }*/

        setupViewModel()
        setupObserver()


    }

    fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(CommonViewModel::class.java)
    }

    fun setupObserver() {

        viewModel.getLogin().observe(this, androidx.lifecycle.Observer {

            when (it.status) {

                Status.SUCCESS -> {
                    dialog.hideDialog()
                    mypref.usertoken = it.data!!.data!!.token

                    /*mypref.email = it.data!!.data!!.email
                    mypref.phonenumber = it.data!!.data!!.mobile
                    mypref.name = it.data!!.data!!.firstName
                    mypref.lname = it.data!!.data!!.lastName
                    mypref.usertoken = it.data!!.data!!.token*/

                    Toast.makeText(this, it.data!!.message, Toast.LENGTH_LONG).show()
                  /*  val json = gson.toJson(it.data!!.data!!)
                    mypref.profile = json


                    val payload : HashMap<String, Any> = HashMap()
               //     payload["full_name"] = it.data.data!!.firstName + it.data.data!!.lastName
                    payload["email"] = it.data.data!!.email.toString()
                    payload["sign_in_channel"] = "manual"

                    val props = JSONObject()
                    props.put("source", "Android")
                    props.put("email", it.data.data?.email)
                    Myapp.instance.mMixpanel.track("Login", props)*/

//                    Smartech.getInstance(WeakReference(applicationContext)).setUserIdentity(it.data.data!!.email.toString())

  //                  Smartech.getInstance(WeakReference(this)).trackEvent("login_user", payload)


                  //  Toast.makeText(this, it.data!!.meta!!.message, Toast.LENGTH_LONG).show()

                  /*  if(it.data.data!!.mobile!!.isEmpty()){

                        val intent = Intent(this, HomeScreen::class.java)
                        intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP)
                        intent.addFlags(FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent)
                        finish()
                    }
                    else {
                        val intent = Intent(this, HomeScreen::class.java)
                        intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP)
                        intent.addFlags(FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent)
                        finish()
                    }*/
                }
                Status.LOADING -> {

                    dialog.showDialog()
                    //   Toast.makeText(this, "Loading", Toast.LENGTH_LONG).show()

                }
                Status.ERROR -> {
                    dialog.hideDialog()
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()

                }
            }

        })


    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.txt_back -> {
                onBackPressed()
            }
            R.id.txt_forgotpass -> {
                val intent = Intent(this, ForgotPasswordActivity::class.java)
                startActivity(intent)
            }
            R.id.txt_signup -> {
                val intent = Intent(this, SignupActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_login -> {

                if (!edtxt_email.text.equals("") &&!edtxt_pass.text.equals("")){

                    if (!validation.isValidEmail(edtxt_email.text.toString())) {
                        dialog.showToast(getString(R.string.string_validemail))

                    }
                    else{
                        if (NetworkUtil.getConnectivityStatus(this.getApplicationContext()) != 0) {

                            val loginReqModel = LoginReqModel(
                                edtxt_email.text.toString(),
                                edtxt_pass.text.toString(),
                            )
                            viewModel.Login(loginReqModel)
                        }
                        else{
                            dialog.warningDialog()
                        }

                    }
                }
                else{
                    dialog.showToast(getString(R.string.string_allfieldmandatory))
                }


            }


        }
    }







}