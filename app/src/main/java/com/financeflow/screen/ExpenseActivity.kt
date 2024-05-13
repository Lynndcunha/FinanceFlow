package com.financeflow.screen

import CommonViewModel
import Status
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProviders
import com.financeflow.R
import com.financeflow.base.BaseActivity
import com.financeflow.model.BudgetReqModel
import com.financeflow.model.ExpenseReqModel
import com.financeflow.model.GoalReqModel
import com.financeflow.model.IncomeReqModel
import com.financeflow.model.IncomeReqModelList
import com.financeflow.util.PrefManager
import com.financeflow.util.ValidationUtils
import com.financeflow.utils.CustomDialog
import com.financeflow.utils.NetworkUtil
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_expense.btn_budget_save
import kotlinx.android.synthetic.main.activity_expense.edtxt_amount
import kotlinx.android.synthetic.main.activity_expense.edtxt_source
import kotlinx.android.synthetic.main.activity_expense.mic_amount
import kotlinx.android.synthetic.main.activity_expense.mic_category
import kotlinx.android.synthetic.main.activity_expense.txt_back

import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.Objects
import javax.xml.datatype.DatatypeConstants.MONTHS


class ExpenseActivity : BaseActivity(), View.OnClickListener {

    lateinit var viewModel: CommonViewModel
    lateinit var dialog: CustomDialog
    lateinit var mypref: PrefManager
    lateinit var validation: ValidationUtils
    lateinit var gson : Gson
    lateinit var  date: String
    var picker: DatePickerDialog? = null
    private val calendar = Calendar.getInstance()
    private val REQUEST_CODE_SPEECH_INPUT = 1
    private val REQUEST_CODE_SPEECH_INPUT1 = 2

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        mypref = PrefManager(this)
        dialog = CustomDialog(this)
        //  dialog.hidSystemUI()
        gson = Gson()

        date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())

        setContentView(R.layout.activity_expense)

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        txt_back.setOnClickListener { onClick(txt_back) }
         btn_budget_save.setOnClickListener { onClick(btn_budget_save) }
        //  txt_signup.setOnClickListener { onClick(txt_signup) }

        validation = ValidationUtils(this)

        setupViewModel()
        setupObserver()

        txt_back.setOnClickListener {

            finish()
        }

        mic_category.setOnClickListener {


            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)


            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )


            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault()
            )

          intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text")

              try {
                startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
            } catch (e: Exception) {
                Toast
                    .makeText(
                        this, " " + e.message,
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }
        }

        mic_amount.setOnClickListener {


            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

            // on below line we are passing language model
            // and model free form in our intent
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )

            // on below line we are passing our
            // language as a default language.
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault()
            )

            // on below line we are specifying a prompt
            // message as speak to text on below line.
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text")

            // on below line we are specifying a try catch block.
            // in this block we are calling a start activity
            // for result method and passing our result code.
            try {
                startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT1)
            } catch (e: Exception) {
                // on below line we are displaying error message in toast
                Toast
                    .makeText(
                        this, " " + e.message,
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }
        }



    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.txt_back -> {
                onBackPressed()
            }
            R.id.btn_budget_save -> {

                if (NetworkUtil.getConnectivityStatus(this.getApplicationContext()) != 0) {

                    val signupReqModel = ExpenseReqModel(
                        mypref.userid.toString(),
                        edtxt_source.text.toString(),
                        edtxt_amount.text.toString(),

                    )

                   viewModel.SaveExpense(signupReqModel)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // in this method we are checking request
        // code with our result code.
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            // on below line we are checking if result code is ok
            if (resultCode == RESULT_OK && data != null) {

                // in that case we are extracting the
                // data from our array list
                val res: ArrayList<String> =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>

                // on below line we are setting data
                // to our output text view.
                edtxt_source.setText(
                    Objects.requireNonNull(res)[0]
                )
            }
        }

        if (requestCode == REQUEST_CODE_SPEECH_INPUT1) {
            // on below line we are checking if result code is ok
            if (resultCode == RESULT_OK && data != null) {

                // in that case we are extracting the
                // data from our array list
                val res: ArrayList<String> =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>

                // on below line we are setting data
                // to our output text view.
                edtxt_amount.setText(
                    Objects.requireNonNull(res)[0]
                )
            }
        }
    }

    fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(CommonViewModel::class.java)
    }

    fun setupObserver() {

        viewModel.getSaveExpense().observe(this, androidx.lifecycle.Observer {

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