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
import com.financeflow.model.VerifyotpReqModel
import com.financeflow.utils.CustomDialog
import com.financeflow.utils.NetworkUtil
import kotlinx.android.synthetic.main.activity_forgotpassword.*
import kotlinx.android.synthetic.main.activity_forgotpassword.edtxt_confpass
import kotlinx.android.synthetic.main.activity_forgotpassword.edtxt_password
import kotlinx.android.synthetic.main.activity_forgotpassword.txt_back
import  com.financeflow.util.PrefManager
import  com.financeflow.util.ValidationUtils

class ForgotPasswordActivity : BaseActivity(),View.OnClickListener {

    lateinit var viewModel: CommonViewModel
    lateinit var dialog: CustomDialog
    lateinit var mypref: PrefManager
    lateinit var validation: ValidationUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgotpassword)

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

                    if (!edtxt_emailphone.text.equals("")) {

                        if (!validation.isValidEmail(edtxt_emailphone.text.toString())) {
                            dialog.showToast(getString(R.string.string_validemail))

                        }
                        else {
                            if (NetworkUtil.getConnectivityStatus(this.getApplicationContext()) != 0) {

                                val forgetReqModel = ForgetReqModel(edtxt_emailphone.text.toString())
                                viewModel.Forgetpassword(forgetReqModel)
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

    fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(CommonViewModel::class.java)
    }

    fun setupObserver() {

        viewModel.getForget().observe(this, androidx.lifecycle.Observer {

            when (it.status) {

                Status.SUCCESS -> {
                    dialog.hideDialog()
                    Toast.makeText(this, it.data!!.message, Toast.LENGTH_LONG).show()
                    val intent = Intent(this, ForgotPasswordOtpActivity::class.java)
                    intent.putExtra("email",edtxt_emailphone.text.toString())
                    startActivity(intent)
                    //edtxt_send.setText(R.string.string_send)
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