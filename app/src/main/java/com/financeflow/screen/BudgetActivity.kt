package com.financeflow.screen

import CommonViewModel
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.financeflow.model.BudgetData
import com.financeflow.model.BudgetReqModel
import kotlinx.android.synthetic.main.activity_budget.btn_budget_save
import kotlinx.android.synthetic.main.activity_budget.edtxt_dining
import kotlinx.android.synthetic.main.activity_budget.edtxt_elect
import kotlinx.android.synthetic.main.activity_budget.edtxt_grocery
import kotlinx.android.synthetic.main.activity_budget.edtxt_gym
import kotlinx.android.synthetic.main.activity_budget.edtxt_internet
import kotlinx.android.synthetic.main.activity_budget.edtxt_loan
import kotlinx.android.synthetic.main.activity_budget.edtxt_other
import kotlinx.android.synthetic.main.activity_budget.edtxt_phone
import kotlinx.android.synthetic.main.activity_budget.edtxt_rent
import kotlinx.android.synthetic.main.activity_budget.edtxt_saving
import kotlinx.android.synthetic.main.activity_budget.edtxt_subscr
import kotlinx.android.synthetic.main.activity_budget.txt_back
import java.text.SimpleDateFormat
import java.util.*


class BudgetActivity : BaseActivity(), View.OnClickListener {

    lateinit var viewModel: CommonViewModel
    lateinit var dialog: CustomDialog
    lateinit var mypref: PrefManager
    lateinit var validation: ValidationUtils
    lateinit var gson : Gson
    lateinit var  date: String
    lateinit var filterDataModel : BudgetData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.extras?.let {

            filterDataModel = intent.getSerializableExtra("value") as BudgetData
            Log.d("DAtaCall", filterDataModel.toString())

        }
        mypref = PrefManager(this)
        dialog = CustomDialog(this)
      //  dialog.hidSystemUI()
        gson = Gson()

        date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())

        setContentView(R.layout.activity_budget)

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //txt_back.setOnClickListener { onClick(txt_back) }
        btn_budget_save.setOnClickListener { onClick(btn_budget_save) }
      //  txt_signup.setOnClickListener { onClick(txt_signup) }

        validation = ValidationUtils(this)

        setupViewModel()
        setupObserver()

        txt_back.setOnClickListener {

            finish()
        }


    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.txt_back -> {
                onBackPressed()
            }
            R.id.btn_budget_save -> {

                            if (NetworkUtil.getConnectivityStatus(this.getApplicationContext()) != 0) {

                                val signupReqModel = BudgetReqModel(
                                    mypref.userid.toString(),
                                    edtxt_rent.text.toString(),
                                    edtxt_elect.text.toString(),
                                    edtxt_phone.text.toString(),
                                    edtxt_internet.text.toString(),
                                    edtxt_loan.text.toString(),
                                    edtxt_grocery.text.toString(),
                                    edtxt_gym.text.toString(),
                                    edtxt_dining.text.toString(),
                                    edtxt_saving.text.toString(),
                                    edtxt_subscr.text.toString(),
                                    edtxt_other.text.toString(),
                                    )

                                viewModel.SaveBudget(signupReqModel)
                            } else {

                                dialog.warningDialog()

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

        viewModel.getBudget().observe(this, androidx.lifecycle.Observer {

            when (it.status) {

                Status.SUCCESS -> {
                    dialog.hideDialog()

                     Toast.makeText(this, "Save successfully", Toast.LENGTH_LONG).show()

                    finish()

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