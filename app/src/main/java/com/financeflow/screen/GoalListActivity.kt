package com.financeflow.screen

import CommonViewModel
import Status
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract.Events
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.financeflow.Interface.OnGoalclick
import com.financeflow.R
import com.financeflow.adapter.GoalAdapter
import com.financeflow.model.CanceltransactionReqModel
import com.financeflow.model.GoalAmountReqModel
import com.financeflow.model.GoalDatum
import com.financeflow.model.IData
import com.financeflow.util.PrefManager
import com.financeflow.utils.CustomDialog
import com.financeflow.utils.NetworkUtil
import com.google.gson.Gson
import kotlinx.android.synthetic.main.budget_list.recycler_chat
import kotlinx.android.synthetic.main.goal_list.edtxt_searchg
import kotlinx.android.synthetic.main.goal_list.from_date
import kotlinx.android.synthetic.main.goal_list.to_date
import kotlinx.android.synthetic.main.income_list.btn_add
import kotlinx.android.synthetic.main.income_list.edtxt_search
import kotlinx.android.synthetic.main.income_list.txt_back
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class GoalListActivity : AppCompatActivity(),OnGoalclick {

    lateinit var chatAdapter:GoalAdapter
    lateinit var viewModel: CommonViewModel
    lateinit var dialog: CustomDialog
    lateinit var mypref: PrefManager
    lateinit var albumid :String
    lateinit var gson : Gson
    var mobile : String = "9769363545"
    lateinit var  date: String
    private val calendar = Calendar.getInstance()
    private var goallist: List<GoalDatum>? = null
     var from :String = ""
     var to :String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.goal_list)

        mypref = PrefManager(this)
        dialog = CustomDialog(this)


        date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())


        val mLayoutManager = LinearLayoutManager(this)
        chatAdapter = GoalAdapter(this,this)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recycler_chat.layoutManager = mLayoutManager
        recycler_chat.itemAnimator = DefaultItemAnimator()
        recycler_chat.adapter = chatAdapter

        btn_add.setOnClickListener(View.OnClickListener {

            val intent = Intent(this, GoalActivity::class.java)
            startActivity(intent)
        })

        txt_back.setOnClickListener {

            finish()
        }

        setupViewModel()
        setupObserver()

        from_date.setOnClickListener {

            showDatePicker("from")
        }

        to_date.setOnClickListener {

            showDatePicker("to")
        }

        edtxt_searchg.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                filterEvents(s.toString())
            }
        })


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

            viewModel.FetchGoal(mypref.userid.toString())

        }
        else{
            dialog.warningDialog()
        }

    }

    fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(CommonViewModel::class.java)
    }

    fun setupObserver() {

       viewModel.getGoalList().observe(this, androidx.lifecycle.Observer {

            when (it.status) {

                Status.SUCCESS -> {
                    dialog.hideDialog()
                   /* Glide.with(this).load(it.data!!.data!![0].mainBanner).centerCrop().into(banner1)
                    competationAdapter.setList(it.data.data!!)*/
                    Log.d("NOTISIZE:", it.data?.data?.size.toString())
                    it.data?.data?.let { it1 ->
                        chatAdapter.setList(it1)
                    goallist = it1
                    }

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

        viewModel.getGoalSaving().observe(this, androidx.lifecycle.Observer {

            when (it.status) {

                Status.SUCCESS -> {
                    dialog.hideDialog()
                    Toast.makeText(this, "Save successfully", Toast.LENGTH_LONG).show()
                    viewModel.FetchGoal(mypref.userid.toString())

                    /* Glide.with(this).load(it.data!!.data!![0].mainBanner).centerCrop().into(banner1)
                     competationAdapter.setList(it.data.data!!)*/
                  //  viewModel.PendingList(mypref.userid.toString())
                  //  viewModel.PaidList(mypref.userid.toString())


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
        val futureEvents = ArrayList<GoalDatum>()
        val currentDate = Date()
        for (events in goallist!!) {

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

    fun filterEvents(text:String) {

        var futureEvents: List<GoalDatum> = goallist!!.filter { s -> s.name!!.contains(text) }

        chatAdapter.setList(futureEvents)
        Log.d("SIZE:",futureEvents.size.toString())
        //  adapter.filter(futureEvents)
    }

    override fun onGoalClick(userid: String,setAmoount:String) {

        val inputEditTextField = EditText(this)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Add Amount")
            .setMessage("")
            .setView(inputEditTextField)
            .setPositiveButton("OK") { _, _ ->
                val editTextInput = inputEditTextField .text.toString()
                //Timber.d("editext value is: $editTextInput")

                if(editTextInput.toInt() < setAmoount.toInt()) {
                    val signupReqModel = GoalAmountReqModel(userid, editTextInput.toString())

                    viewModel.GoalamountUpdate(signupReqModel)
                }
                else{
                    Toast.makeText(this, "Enter amount is grater then set amount", Toast.LENGTH_LONG).show()

                }
            }
            .setNegativeButton("Cancel", null)
            .create()
        dialog.show()
    }

}