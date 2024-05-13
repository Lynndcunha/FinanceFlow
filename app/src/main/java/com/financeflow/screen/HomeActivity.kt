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
import com.financeflow.model.BudgetModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_home.btn_Income
import kotlinx.android.synthetic.main.activity_home.btn_exp
import kotlinx.android.synthetic.main.activity_home.btn_expence
import kotlinx.android.synthetic.main.activity_home.btn_goal
import kotlinx.android.synthetic.main.activity_home.noti
import kotlinx.android.synthetic.main.activity_home.profile
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class HomeActivity :BaseActivity(), View.OnClickListener {

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

        setContentView(R.layout.activity_home)
        mypref = PrefManager(this)
        dialog = CustomDialog(this)
         gson = Gson()
        dialog.hidSystemUI()




        validation = ValidationUtils(this)

        //txt_back.setOnClickListener { onClick(txt_back) }
        btn_Income.setOnClickListener { onClick(btn_Income) }
        btn_expence.setOnClickListener { onClick(btn_expence) }
        btn_goal.setOnClickListener { onClick(btn_goal) }
        btn_exp.setOnClickListener { onClick(btn_exp) }

        //fb.setOnClickListener { onClick(fb) }
        //google.setOnClickListener { onClick(google) }

        /*txt_link1.setOnClickListener {
            txt_link1.setMovementMethod(LinkMovementMethod.getInstance());

        }*/

        noti.setOnClickListener {

            val intent = Intent(this, NotificationListActivity::class.java)
            startActivity(intent)

        }

        profile.setOnClickListener {

            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)

        }

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
                    mypref.userid = it.data.data!!.id

                    Toast.makeText(this, it.data!!.message, Toast.LENGTH_LONG).show()
                    val intent = Intent(this, IncomeListActivity::class.java)
                    startActivity(intent)
                    finish()

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

            R.id.btn_Income -> {
                val intent = Intent(this, IncomeListActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_expence -> {
                val intent = Intent(this, BudgetListActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_goal -> {
                val intent = Intent(this, GoalListActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_exp -> {
                val intent = Intent(this, ExpenseListActivity::class.java)
                startActivity(intent)
            }
        }
    }







}