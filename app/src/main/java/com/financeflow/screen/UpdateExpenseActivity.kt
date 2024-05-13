package com.financeflow.screen

import CommonViewModel
import Status
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProviders
import com.financeflow.R
import com.financeflow.base.BaseActivity

import com.financeflow.model.ExpenseDatum
import com.financeflow.model.ExpenseUpdateReqModel
import com.financeflow.model.GoalUpdateReqModel

import com.financeflow.util.PrefManager
import com.financeflow.util.ValidationUtils
import com.financeflow.utils.CustomDialog
import com.financeflow.utils.NetworkUtil
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_expense.btn_budget_save
import kotlinx.android.synthetic.main.activity_expense.edtxt_amount
import kotlinx.android.synthetic.main.activity_expense.edtxt_source
import kotlinx.android.synthetic.main.activity_expense.txt_back


import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.xml.datatype.DatatypeConstants.MONTHS


class UpdateExpenseActivity : BaseActivity(), View.OnClickListener {

    lateinit var viewModel: CommonViewModel
    lateinit var dialog: CustomDialog
    lateinit var mypref: PrefManager
    lateinit var validation: ValidationUtils
    lateinit var gson : Gson
    lateinit var  date: String
    var picker: DatePickerDialog? = null
    private val calendar = Calendar.getInstance()
    lateinit var filterDataModel : ExpenseDatum
    lateinit var id : String

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.extras?.let {

            filterDataModel = intent.getSerializableExtra("value") as ExpenseDatum
            Log.d("DAtaCall", filterDataModel.toString())
            id = intent.getStringExtra("id").toString()


        }
        mypref = PrefManager(this)
        dialog = CustomDialog(this)
        //  dialog.hidSystemUI()
        gson = Gson()

        date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())

        setContentView(R.layout.activity_expense)

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



        filterDataModel.category?.let {
            edtxt_source.setText(filterDataModel.category.toString())
        }
        filterDataModel.amount?.let {


            edtxt_amount.setText(filterDataModel.amount.toString())
        }


    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.txt_back -> {
                onBackPressed()
            }
            R.id.btn_budget_save -> {

                if (NetworkUtil.getConnectivityStatus(this.getApplicationContext()) != 0) {

                    val signupReqModel = ExpenseUpdateReqModel(
                        id,
                        edtxt_source.text.toString(),
                        edtxt_amount.text.toString(),

                    )

                   viewModel.UpdateExpense(signupReqModel)
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

        viewModel.getUpdatExpense().observe(this, androidx.lifecycle.Observer {

            when (it.status) {

                Status.SUCCESS -> {
                    dialog.hideDialog()

                     Toast.makeText(this, "Update successfully", Toast.LENGTH_LONG).show()

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