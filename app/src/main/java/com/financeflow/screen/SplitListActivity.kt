package com.financeflow.screen

import CommonViewModel
import Status
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract.Events
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.financeflow.Interface.OnPayclick
import com.financeflow.R
import com.financeflow.adapter.GoalAdapter
import com.financeflow.adapter.PaidAdapter
import com.financeflow.adapter.RequestAdapter
import com.financeflow.model.CreatetransactionReqModel
import com.financeflow.model.GoalDatum
import com.financeflow.model.IData
import com.financeflow.model.SetteltransactionReqModel
import com.financeflow.model.UserData
import com.financeflow.util.PrefManager
import com.financeflow.utils.CustomDialog
import com.financeflow.utils.NetworkUtil
import com.google.gson.Gson
import kotlinx.android.synthetic.main.budget_list.recycler_chat
import kotlinx.android.synthetic.main.goal_list.edtxt_searchg
import kotlinx.android.synthetic.main.income_list.btn_add
import kotlinx.android.synthetic.main.income_list.txt_back
import kotlinx.android.synthetic.main.split_add.edtxts_amount
import kotlinx.android.synthetic.main.split_add.edtxts_source
import kotlinx.android.synthetic.main.split_list.paid
import kotlinx.android.synthetic.main.split_list.paid1
import kotlinx.android.synthetic.main.split_list.recycler_paid
import kotlinx.android.synthetic.main.split_list.recycler_request
import kotlinx.android.synthetic.main.split_list.request
import kotlinx.android.synthetic.main.split_list.requested
import java.lang.Math.round
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class SplitListActivity : AppCompatActivity(),OnPayclick {

    lateinit var chatAdapter:RequestAdapter
    lateinit var paidAdapter:PaidAdapter

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

        setContentView(R.layout.split_list)

        mypref = PrefManager(this)
        dialog = CustomDialog(this)


        date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())


        val mLayoutManager = LinearLayoutManager(this)
        val mLayoutManager1 = LinearLayoutManager(this)


        chatAdapter = RequestAdapter(this,this)
        paidAdapter = PaidAdapter(this)

        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recycler_request.layoutManager = mLayoutManager
        recycler_request.itemAnimator = DefaultItemAnimator()
        recycler_request.adapter = chatAdapter

        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recycler_paid.layoutManager = mLayoutManager1
        recycler_paid.itemAnimator = DefaultItemAnimator()
        recycler_paid.adapter = paidAdapter

        btn_add.setOnClickListener(View.OnClickListener {

            val intent = Intent(this, SplitAddActivity::class.java)
            startActivity(intent)
        })

        txt_back.setOnClickListener {

            finish()
        }

        request.isChecked = true

        request.setOnClickListener {
            recycler_request.visibility = View.VISIBLE
            recycler_paid.visibility = View.GONE

        }

        paid.setOnClickListener {

            recycler_request.visibility = View.GONE
            recycler_paid.visibility = View.VISIBLE
        }

        setupViewModel()
        setupObserver()




    }



    override fun onStart() {
        super.onStart()
        if (NetworkUtil.getConnectivityStatus(this.getApplicationContext()) != 0) {

            viewModel.PendingList(mypref.userid.toString())
            viewModel.PaidList(mypref.userid.toString())

//            viewModel.PendingList("662cdf61ac9a14038e5c1f9a")
//            viewModel.PaidList("662cdf61ac9a14038e5c1f9a")

        }
        else{
            dialog.warningDialog()
        }

    }

    fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(CommonViewModel::class.java)
    }

    fun setupObserver() {

       viewModel.getPending().observe(this, androidx.lifecycle.Observer {

            when (it.status) {

                Status.SUCCESS -> {
                    dialog.hideDialog()
                   /* Glide.with(this).load(it.data!!.data!![0].mainBanner).centerCrop().into(banner1)
                    competationAdapter.setList(it.data.data!!)*/
                    Log.d("NOTISIZE:", it.data?.data?.size.toString())
                    it.data?.data?.let { it1 ->
                        chatAdapter.setList(it1)
                        var amt = 0.0
                        it1.forEach {

                            val roundedValue = round(it.amount.toString().toDouble())

                            amt += roundedValue
                        }

                        requested.setText("$"+amt.toString())
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

        viewModel.getPaid().observe(this, androidx.lifecycle.Observer {

            when (it.status) {

                Status.SUCCESS -> {
                    dialog.hideDialog()
                    /* Glide.with(this).load(it.data!!.data!![0].mainBanner).centerCrop().into(banner1)
                     competationAdapter.setList(it.data.data!!)*/
                    Log.d("NOTISIZE1:", it.data?.data?.size.toString())
                    it.data?.data?.let { it1 ->
                        paidAdapter.setList(it1)

                        var amt = 0.0
                        it1.forEach {

                            val roundedValue = round(it.amount.toString().toDouble())

                            amt += roundedValue

                        }

                        paid1.setText("$"+amt.toString())
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

        viewModel.getSetTrans().observe(this, androidx.lifecycle.Observer {

            when (it.status) {

                Status.SUCCESS -> {
                    dialog.hideDialog()
                    /* Glide.with(this).load(it.data!!.data!![0].mainBanner).centerCrop().into(banner1)
                     competationAdapter.setList(it.data.data!!)*/
                    viewModel.PendingList(mypref.userid.toString())
                    viewModel.PaidList(mypref.userid.toString())


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

       // chatAdapter.setList(futureEvents)
        Log.d("SIZE:",futureEvents.size.toString())
      //  adapter.filter(futureEvents)
    }

    fun filterEvents(text:String) {

        var futureEvents: List<GoalDatum> = goallist!!.filter { s -> s.name!!.contains(text) }

       // chatAdapter.setList(futureEvents)
        Log.d("SIZE:",futureEvents.size.toString())
        //  adapter.filter(futureEvents)
    }

    override fun onPayClick(userid: String) {

        val signupReqModel = SetteltransactionReqModel(userid)

        viewModel.SettelTransaction(signupReqModel)

    }

}