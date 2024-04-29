package com.financeflow.screen

import CommonViewModel
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.financeflow.R
import com.financeflow.base.BaseActivity
import com.financeflow.model.SignupReqModel
import com.financeflow.utils.CustomDialog
import com.financeflow.utils.NetworkUtil
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_signup.*
import  com.financeflow.util.PrefManager
import  com.financeflow.util.ValidationUtils
import android.view.WindowManager
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*


class SignupActivity : BaseActivity(), View.OnClickListener {

    lateinit var viewModel: CommonViewModel
    lateinit var dialog: CustomDialog
    lateinit var mypref: PrefManager
    lateinit var validation: ValidationUtils
    lateinit var gson : Gson
    lateinit var  date: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mypref = PrefManager(this)
        dialog = CustomDialog(this)
      //  dialog.hidSystemUI()
        gson = Gson()

        date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())

        setContentView(R.layout.activity_signup)

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //txt_back.setOnClickListener { onClick(txt_back) }
        btn_singup.setOnClickListener { onClick(btn_singup) }
        txt_signup.setOnClickListener { onClick(txt_signup) }

        validation = ValidationUtils(this)

        setupViewModel()
        setupObserver()

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.txt_back -> {
                onBackPressed()
            }
            R.id.btn_singup -> {
                if (!edtxt_email.text.isEmpty() &&
                        !edtxt_password.text.isEmpty()) {

                    if (!validation.isValidEmail(edtxt_email.text.toString())) {
                        dialog.showToast(getString(R.string.string_validemail))

                    } else {
                        if (edtxt_password.text.toString() != edtxt_confpass.text.toString()) {

                            dialog.showToast(getString(R.string.string_confpassword))
                        }
                        else {

                            if (NetworkUtil.getConnectivityStatus(this.getApplicationContext()) != 0) {

                                val signupReqModel = SignupReqModel(
                                    edtxt_email.text.toString(),
                                    edtxt_name.text.toString(),
                                    edtxt_password.text.toString(),
                                )

                                viewModel.Signup(signupReqModel)
                            } else {

                                dialog.warningDialog()

                            }

                        }


                    }


                } else {
                    dialog.showToast(getString(R.string.string_allfieldmandatory))
                }
            }
            R.id.txt_signup -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

            }
    }

    fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(CommonViewModel::class.java)
    }

    fun setupObserver() {

        viewModel.getSignup().observe(this, androidx.lifecycle.Observer {

            when (it.status) {

                Status.SUCCESS -> {
                    dialog.hideDialog()

                     Toast.makeText(this, it.data!!.message, Toast.LENGTH_LONG).show()


                   // mypref.usertoken = it.data!!.data!!.token


                }
                Status.LOADING -> {

                    dialog.showDialog()
                    //   Toast.makeText(this, "Loading", Toast.LENGTH_LONG).show()

                }
                Status.ERROR -> {
                    dialog.hideDialog()
                    dialog.showToast(it.message.toString())
                   // Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()

                }
            }

        })

    }
}