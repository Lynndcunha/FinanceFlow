package com.financeflow.screen

import CommonViewModel
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.financeflow.R
import com.financeflow.base.BaseActivity
import com.financeflow.model.ForgetReqModel
import com.financeflow.model.VerifyotpReqModel
import com.financeflow.utils.CustomDialog
import com.financeflow.utils.NetworkUtil
import kotlinx.android.synthetic.main.activity_forgotpassword.*
import kotlinx.android.synthetic.main.activity_forgotpassword.edtxt_confpass
import kotlinx.android.synthetic.main.activity_forgotpassword.edtxt_password
import kotlinx.android.synthetic.main.activity_forgotpassword.txt_back
import  com.financeflow.util.PrefManager
import  com.financeflow.util.ValidationUtils

class ForgotPasswordNew : BaseActivity(),View.OnClickListener {

    lateinit var viewModel: CommonViewModel
    lateinit var dialog: CustomDialog
    lateinit var mypref: PrefManager
    lateinit var validation: ValidationUtils
    var email : String = ""
    var otp : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.extras?.let {

            if(intent.extras!!.getString("email") != null){
                email = intent.extras!!.getString("email")!!

            }

        }
        setContentView(R.layout.activity_new_password)


        mypref = PrefManager(this)
        dialog = CustomDialog(this)

        dialog.hidSystemUI()

        txt_back.setOnClickListener { onClick(txt_back) }
        edtxt_send.setOnClickListener { onClick(edtxt_send) }


        validation = ValidationUtils(this)

        setupViewModel()
        setupObserver()
    }


    override fun onClick(v: View?) {

        when (v!!.id) {
            R.id.txt_back -> {
                onBackPressed()
            }
            R.id.edtxt_send -> {


                    if ( !edtxt_password.text.equals("") && !edtxt_confpass.text.equals("")) {
                        if (edtxt_password.text.toString() != edtxt_confpass.text.toString()) {

                            dialog.showToast(getString(R.string.string_confpassword))
                        } else {
                            if (NetworkUtil.getConnectivityStatus(this.getApplicationContext()) != 0) {

                                val verifyotpReqModel = VerifyotpReqModel(
                                   email,
                                    edtxt_password.text.toString(),
                                )
                                viewModel.VerifyOtp(verifyotpReqModel)
                            } else {
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

    fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(CommonViewModel::class.java)
    }

    fun setupObserver() {

        viewModel.getOtpVerify().observe(this, androidx.lifecycle.Observer {

            when (it.status) {

                Status.SUCCESS -> {
                    dialog.hideDialog()
                    // mypref.usertoken = it.data!!.data!!.token
                    Toast.makeText(this, it.data!!.message, Toast.LENGTH_LONG).show()

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

}