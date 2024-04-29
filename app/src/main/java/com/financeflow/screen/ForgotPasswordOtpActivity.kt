package com.financeflow.screen

import CommonViewModel
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.financeflow.R
import com.financeflow.base.BaseActivity
import com.financeflow.model.ForgetReqModel
import com.financeflow.model.VerifyOnlyotpReqModel
import com.financeflow.model.VerifyotpReqModel
import com.financeflow.utils.CustomDialog
import com.financeflow.utils.NetworkUtil
import kotlinx.android.synthetic.main.activity_forgotpassword.*
import kotlinx.android.synthetic.main.activity_forgotpassword.edtxt_confpass
import kotlinx.android.synthetic.main.activity_forgotpassword.edtxt_password
import kotlinx.android.synthetic.main.activity_forgotpassword.txt_back
import  com.financeflow.util.PrefManager
import  com.financeflow.util.ValidationUtils
import kotlinx.android.synthetic.main.activity_forgot_otp.otp_view

class ForgotPasswordOtpActivity : BaseActivity(),View.OnClickListener {

    lateinit var viewModel: CommonViewModel
    lateinit var dialog: CustomDialog
    lateinit var mypref: PrefManager
    lateinit var validation: ValidationUtils
    var email : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_otp)

        intent.extras?.let {

            if(intent.extras!!.getString("email") != null){
                email = intent.extras!!.getString("email")!!
            }

        }

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


                    /*val intent = Intent(this, ForgotPasswordNew::class.java)
                    startActivity(intent)*/
                    if (!otp_view.otp.equals("") ) {

                            if (NetworkUtil.getConnectivityStatus(this.getApplicationContext()) != 0) {

                                val verifyotpReqModel = VerifyOnlyotpReqModel(
                                    email,
                                    otp_view.otp.toString(),
                                )
                                viewModel.VerifyOnlyOtp(verifyotpReqModel)
                            } else {
                                dialog.warningDialog()

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


        viewModel.getOnlyOtpVerify().observe(this, androidx.lifecycle.Observer {

            when (it.status) {

                Status.SUCCESS -> {
                    dialog.hideDialog()
                    // mypref.usertoken = it.data!!.data!!.token
                    Toast.makeText(this, it.data!!.message, Toast.LENGTH_LONG).show()
                    val intent = Intent(this, ForgotPasswordNew::class.java)
                    intent.putExtra("email",email)
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

}