package com.financeflow.screen

import CommonViewModel
import android.app.DatePickerDialog
import android.content.Intent

import android.os.Bundle

import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager

import android.util.Log
import android.view.View
import android.widget.Toast

import com.google.gson.Gson

import androidx.appcompat.app.AppCompatActivity

import com.financeflow.R
import com.financeflow.adapter.BudgetAdapter
import com.financeflow.adapter.IncomeAdapter
import com.financeflow.model.GoalDatum
import com.financeflow.model.IData
import com.financeflow.util.PrefManager
import com.financeflow.utils.CustomDialog
import com.financeflow.utils.NetworkUtil
import kotlinx.android.synthetic.main.budget_list.recycler_chat


import kotlinx.android.synthetic.main.income_list.btn_add
import kotlinx.android.synthetic.main.income_list.from_date
import kotlinx.android.synthetic.main.income_list.to_date
import kotlinx.android.synthetic.main.income_list.txt_back
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class IncomeListActivity : AppCompatActivity() {

    lateinit var chatAdapter:IncomeAdapter
    lateinit var viewModel: CommonViewModel
    lateinit var dialog: CustomDialog
    lateinit var mypref: PrefManager
    lateinit var albumid :String
    lateinit var gson : Gson
    var mobile : String = "9769363545"

    lateinit var  date: String
    private val calendar = Calendar.getInstance()
    private var incomelist: List<IData>? = null
     var from :String = ""
     var to :String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.income_list)

        mypref = PrefManager(this)
        dialog = CustomDialog(this)



        setupViewModel()
        setupObserver()

        val mLayoutManager = LinearLayoutManager(this)
        chatAdapter = IncomeAdapter(this)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recycler_chat.layoutManager = mLayoutManager
        recycler_chat.itemAnimator = DefaultItemAnimator()
        recycler_chat.adapter = chatAdapter

        btn_add.setOnClickListener(View.OnClickListener {

            val intent = Intent(this, IncomeActivity::class.java)
            startActivity(intent)
        })

        txt_back.setOnClickListener {

            finish()
        }

        from_date.setOnClickListener {

            showDatePicker("from")
        }

        to_date.setOnClickListener {

            showDatePicker("to")
        }


    }

    private fun showDatePicker(click : String) {
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
                //  edtxt_date.setText(formattedDate)

                if(click.equals("from")) {

                    from = formattedDate
                    from_date.setText(formattedDate)

                    if(!to.equals("")){
                        futureEvents()
                    }
                }
                if(click.equals("to")){
                    to = formattedDate
                    to_date.setText(formattedDate)
                    if(!from.equals("")){
                        futureEvents()
                    }
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        // Show the DatePicker dialog
        datePickerDialog.show()
    }

    override fun onStart() {
        super.onStart()
        if (NetworkUtil.getConnectivityStatus(this.getApplicationContext()) != 0) {

            viewModel.FetchIncome(mypref.userid.toString())

        }
        else{
            dialog.warningDialog()
        }

    }

    fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(CommonViewModel::class.java)
    }

    fun setupObserver() {

       viewModel.getIncomeList().observe(this, androidx.lifecycle.Observer {

            when (it.status) {

                Status.SUCCESS -> {
                    dialog.hideDialog()
                   /* Glide.with(this).load(it.data!!.data!![0].mainBanner).centerCrop().into(banner1)
                    competationAdapter.setList(it.data.data!!)*/
                    Log.d("NOTISIZE:", it.data?.data?.size.toString())
                    it.data?.data?.let { it1 -> chatAdapter.setList(it1,) }

                }
                Status.LOADING -> {

                    dialog.showDialog()
                 //      Toast.makeText(context, "Loading", Toast.LENGTH_LONG).show()

                }
                Status.ERROR -> {
                    dialog.hideDialog()
                    dialog.showToast(it.message.toString())

                }
            }

        })

    }

    fun futureEvents() {
        val futureEvents = ArrayList<IData>()
        val currentDate = Date()
        for (events in incomelist!!) {

            var temdate = events.createdAt!!.split("T")[0]
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            var date: Date? = null
            var fdate: Date? = null
            var tdate: Date? = null

            try {
                date = formatter.parse(temdate)
                fdate = formatter.parse(from)
                tdate = formatter.parse(to)

            } catch (e: ParseException) {
            }

            if ((date!!.after(fdate) || date.equals(fdate)) && (date.before(tdate)|| date.equals(tdate))) {
                futureEvents.add(events)
            }
        }

        chatAdapter.setList(futureEvents)
        Log.d("SIZE:",futureEvents.size.toString())
        //  adapter.filter(futureEvents)
    }


}