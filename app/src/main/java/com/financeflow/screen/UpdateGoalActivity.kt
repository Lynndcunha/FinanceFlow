package com.financeflow.screen

import CommonViewModel
import Status
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
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
import com.financeflow.model.BudgetData
import com.financeflow.model.BudgetReqModel
import com.financeflow.model.GoalDatum
import com.financeflow.model.GoalReqModel
import com.financeflow.model.GoalUpdateReqModel
import com.financeflow.model.IncomeReqModel
import com.financeflow.model.IncomeReqModelList
import com.financeflow.util.PrefManager
import com.financeflow.util.ValidationUtils
import com.financeflow.utils.CustomDialog
import com.financeflow.utils.NetworkUtil
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieEntry
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_budget.edtxt_elect
import kotlinx.android.synthetic.main.activity_budget.edtxt_internet
import kotlinx.android.synthetic.main.activity_budget.edtxt_phone
import kotlinx.android.synthetic.main.activity_budget.edtxt_rent
import kotlinx.android.synthetic.main.activity_goal.btn_budget_save

import kotlinx.android.synthetic.main.activity_goal.edtxt_date
import kotlinx.android.synthetic.main.activity_goal.edtxt_desc
import kotlinx.android.synthetic.main.activity_goal.edtxt_amount
import kotlinx.android.synthetic.main.activity_goal.edtxt_source
import kotlinx.android.synthetic.main.activity_goal.piechart1
import kotlinx.android.synthetic.main.activity_goal.txt_back
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.xml.datatype.DatatypeConstants.MONTHS


class UpdateGoalActivity : BaseActivity(), View.OnClickListener {

    lateinit var viewModel: CommonViewModel
    lateinit var dialog: CustomDialog
    lateinit var mypref: PrefManager
    lateinit var validation: ValidationUtils
    lateinit var gson : Gson
    lateinit var  date: String
    var picker: DatePickerDialog? = null
    private val calendar = Calendar.getInstance()
    lateinit var filterDataModel : GoalDatum
    lateinit var id : String
    val listPie = ArrayList<BarEntry>()
    lateinit var barData: BarData
    lateinit var barDataSet: BarDataSet

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.extras?.let {

            filterDataModel = intent.getSerializableExtra("value") as GoalDatum
            Log.d("DAtaCall", filterDataModel.toString())
            id = intent.getStringExtra("id").toString()


        }

        mypref = PrefManager(this)
        dialog = CustomDialog(this)
        //  dialog.hidSystemUI()
        gson = Gson()

        date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())

        setContentView(R.layout.activity_goal)

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

        edtxt_date.setOnClickListener {

            showDatePicker()

        }
        var i = 0

        filterDataModel.savedAmount?.forEach {

            i += 1

            listPie.add(BarEntry(i.toFloat(), it.amount!!.toFloat()))

        }



        if(listPie.size > 0) {


            barDataSet = BarDataSet(listPie, "Bar Chart Data")

            // on below line we are initializing our bar data
            barData = BarData(barDataSet)

            // on below line we are setting data to our bar chart
            piechart1.data = barData

            // on below line we are setting colors for our bar chart text
            barDataSet.valueTextColor = Color.WHITE

            // on below line we are setting color for our bar data set
            barDataSet.setColor(resources.getColor(R.color.purple_200))

            // on below line we are setting text size
            barDataSet.valueTextSize = 16f

            // on below line we are enabling description as false
            piechart1.description.isEnabled = false
        }

        filterDataModel.name?.let {
            edtxt_source.setText(filterDataModel.name.toString())
        }
        filterDataModel.amount?.let {


            edtxt_amount.setText(filterDataModel.amount.toString())
        }

        filterDataModel.date?.let {

            edtxt_date.setText(filterDataModel.date.toString().split("T")[0])
            date= filterDataModel.date.toString()

        }
        filterDataModel.description?.let {

            edtxt_desc.setText(filterDataModel.description.toString())
        }
    }

    private fun showDatePicker() {
        // Create a DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            this, {DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                // Create a new Calendar instance to hold the selected date
                val selectedDate = Calendar.getInstance()
                // Set the selected date using the values received from the DatePicker dialog
                selectedDate.set(year, monthOfYear, dayOfMonth)
                // Create a SimpleDateFormat to format the date as "dd/MM/yyyy"
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                // Format the selected date into a string
                val formattedDate = dateFormat.format(selectedDate.time)
                // Update the TextView to display the selected date with the "Selected Date: " prefix
                edtxt_date.setText(formattedDate)
                date= formattedDate
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        // Show the DatePicker dialog
        datePickerDialog.show()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.txt_back -> {
                onBackPressed()
            }
            R.id.btn_budget_save -> {

                if (NetworkUtil.getConnectivityStatus(this.getApplicationContext()) != 0) {

                    val signupReqModel = GoalUpdateReqModel(
                        id,
                        edtxt_source.text.toString(),
                        edtxt_amount.text.toString(),
                        date,
                        edtxt_desc.text.toString(),
                    )

                   viewModel.UpdateGoal(signupReqModel)
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

        viewModel.getUpdateGoal().observe(this, androidx.lifecycle.Observer {

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