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
import com.financeflow.model.UpdateBudgetReqModel
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
import kotlinx.android.synthetic.main.activity_updatebudget.txt_back
import java.text.SimpleDateFormat
import java.util.*


class UpdateBudgetActivity : BaseActivity(), View.OnClickListener {

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

        setContentView(R.layout.activity_updatebudget)

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



        filterDataModel.rent?.let {
            edtxt_rent.setText(filterDataModel.rent.toString())
        }
        filterDataModel.electricityBill?.let {

            edtxt_elect.setText(filterDataModel.electricityBill.toString())
        }

        filterDataModel.phoneBill?.let {

            edtxt_phone.setText(filterDataModel.phoneBill.toString())
        }
        filterDataModel.internetBill?.let {

            edtxt_internet.setText(filterDataModel.internetBill.toString())
        }
        filterDataModel.studentLoan?.let {

            edtxt_loan.setText(filterDataModel.studentLoan.toString())
        }
        filterDataModel.grocery?.let {

            edtxt_grocery.setText(filterDataModel.grocery.toString())
        }
        filterDataModel.gym?.let {

            edtxt_gym.setText(filterDataModel.gym.toString())
        }
        filterDataModel.dineOut?.let {

            edtxt_dining.setText(filterDataModel.dineOut.toString())
        }
        filterDataModel.savings?.let {

            edtxt_saving.setText(filterDataModel.savings.toString())
        }
        filterDataModel.subscriptions?.let {

            edtxt_subscr.setText(filterDataModel.subscriptions.toString())
        }
        filterDataModel.others?.let {

            edtxt_other.setText(filterDataModel.others.toString())
        }





    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.txt_back -> {
                onBackPressed()
            }
            R.id.btn_budget_save -> {

                            if (NetworkUtil.getConnectivityStatus(this.getApplicationContext()) != 0) {

                                val signupReqModel = UpdateBudgetReqModel(
                                    filterDataModel.id.toString(),
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

                                viewModel.UpdateBudget(signupReqModel)
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

        viewModel.getUpdateBudget().observe(this, androidx.lifecycle.Observer {

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