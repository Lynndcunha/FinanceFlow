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
import com.financeflow.model.Income
import com.financeflow.model.IncomeReqModel
import com.financeflow.model.IncomeReqModelList
import com.financeflow.model.UpdateIncomeReqModel
import kotlinx.android.synthetic.main.activity_budget.btn_budget_save

import kotlinx.android.synthetic.main.activity_income.edtxt_amount
import kotlinx.android.synthetic.main.activity_income.edtxt_source
import kotlinx.android.synthetic.main.activity_updateincome.txt_back
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class UpdateIncomeActivity : BaseActivity(), View.OnClickListener {

    lateinit var viewModel: CommonViewModel
    lateinit var dialog: CustomDialog
    lateinit var mypref: PrefManager
    lateinit var validation: ValidationUtils
    lateinit var gson : Gson
    lateinit var  date: String
    lateinit var filterDataModel : Income
    lateinit var id : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        intent.extras?.let {

            filterDataModel = intent.getSerializableExtra("value") as Income

            id = intent.getStringExtra("id").toString()

            Log.d("DAtaCall", filterDataModel.toString())

        }
        mypref = PrefManager(this)
        dialog = CustomDialog(this)
      //  dialog.hidSystemUI()
        gson = Gson()

        date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())

        setContentView(R.layout.activity_updateincome)

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //txt_back.setOnClickListener { onClick(txt_back) }
        btn_budget_save.setOnClickListener { onClick(btn_budget_save) }
      //  txt_signup.setOnClickListener { onClick(txt_signup) }

        validation = ValidationUtils(this)

        filterDataModel.incomeType?.let {
            edtxt_source.setText(filterDataModel.incomeType.toString())
        }
        filterDataModel.incomeAmount?.let {

            edtxt_amount.setText(filterDataModel.incomeAmount.toString())
        }

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

                val list: ArrayList<IncomeReqModelList> = ArrayList()

                list.add(IncomeReqModelList(edtxt_source.text.toString(),edtxt_amount.text.toString()))


                            if (NetworkUtil.getConnectivityStatus(this.getApplicationContext()) != 0) {

                                val signupReqModel = UpdateIncomeReqModel(
                                    id,
                                    list
                                    )

                                viewModel.UpdateIncome(signupReqModel)
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

        viewModel.getUpdateIncomet().observe(this, androidx.lifecycle.Observer {

            when (it.status) {

                Status.SUCCESS -> {
                    dialog.hideDialog()

                     Toast.makeText(this, "Update successfully", Toast.LENGTH_LONG).show()


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