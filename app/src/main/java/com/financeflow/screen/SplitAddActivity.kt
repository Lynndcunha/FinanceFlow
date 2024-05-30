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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.financeflow.Interface.OnCheckBoxclick
import com.financeflow.R
import com.financeflow.adapter.GoalAdapter
import com.financeflow.adapter.SplitAdapter
import com.financeflow.model.CreatetransactionReqModel
import com.financeflow.model.ExpenseReqModel
import com.financeflow.model.GoalDatum
import com.financeflow.model.IData
import com.financeflow.model.UserData
import com.financeflow.model.UserDatum
import com.financeflow.util.PrefManager
import com.financeflow.utils.CustomDialog
import com.financeflow.utils.NetworkUtil
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_expense.edtxt_amount
import kotlinx.android.synthetic.main.activity_expense.edtxt_source
import kotlinx.android.synthetic.main.budget_list.recycler_chat
import kotlinx.android.synthetic.main.goal_list.edtxt_searchg
import kotlinx.android.synthetic.main.goal_list.from_date
import kotlinx.android.synthetic.main.goal_list.to_date
import kotlinx.android.synthetic.main.income_list.btn_add
import kotlinx.android.synthetic.main.income_list.edtxt_search
import kotlinx.android.synthetic.main.income_list.txt_back
import kotlinx.android.synthetic.main.split_add.btn_split_save
import kotlinx.android.synthetic.main.split_add.edtxts_amount
import kotlinx.android.synthetic.main.split_add.edtxts_source
import kotlinx.android.synthetic.main.split_add.recycler_split
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class SplitAddActivity : AppCompatActivity(),OnCheckBoxclick {

    lateinit var chatAdapter:SplitAdapter
    lateinit var viewModel: CommonViewModel
    lateinit var dialog: CustomDialog
    lateinit var mypref: PrefManager
    lateinit var albumid :String
    lateinit var gson : Gson
    var mobile : String = "9769363545"
    lateinit var  date: String
    private val calendar = Calendar.getInstance()
    private var userData: List<UserDatum>? = null
     var from :String = ""
     var to :String = ""
    private lateinit var itemList: MutableList<UserData>
    private lateinit var itemList1: MutableList<UserData>

    private var userId1: List<String>? = null
    private lateinit var UserList: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.split_add)

        mypref = PrefManager(this)
        dialog = CustomDialog(this)


        date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        itemList = mutableListOf()
        UserList = mutableListOf()



        val mLayoutManager = LinearLayoutManager(this)
        chatAdapter = SplitAdapter(this,this)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recycler_split.layoutManager = mLayoutManager
        recycler_split.itemAnimator = DefaultItemAnimator()
        recycler_split.adapter = chatAdapter



        txt_back.setOnClickListener {

            finish()
        }

        setupViewModel()
        setupObserver()


        btn_split_save.setOnClickListener {

            if (NetworkUtil.getConnectivityStatus(this.getApplicationContext()) != 0) {

                if(UserList.size > 0) {
                    val signupReqModel = CreatetransactionReqModel(
                        edtxts_amount.text.toString(),
                        edtxts_source.text.toString(),
                        UserList,
                        mypref.userid.toString(),

                        )

                    viewModel.SaveTransaction(signupReqModel)
                }
                else{
                    Toast.makeText(this, "please select user", Toast.LENGTH_LONG).show()

                }
            } else {

                dialog.warningDialog()

            }
        }





    }



    override fun onStart() {
        super.onStart()
        if (NetworkUtil.getConnectivityStatus(this.getApplicationContext()) != 0) {

            viewModel.FetchUserlist()

        }
        else{
            dialog.warningDialog()
        }

    }

    fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(CommonViewModel::class.java)
    }

    fun setupObserver() {

       viewModel.getUserLsit().observe(this, androidx.lifecycle.Observer {

            when (it.status) {

                Status.SUCCESS -> {
                    dialog.hideDialog()
                   /* Glide.with(this).load(it.data!!.data!![0].mainBanner).centerCrop().into(banner1)
                    competationAdapter.setList(it.data.data!!)*/
                    Log.d("NOTISIZE:", it.data?.data?.size.toString())
                    it.data?.data?.let { it1 ->

                        it1.forEach {

                            itemList.add(UserData(it.id!!,it.fullName!!,it.email!!,false))

                        }
                        chatAdapter.setList(itemList!!)
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


        viewModel.getTrans().observe(this, androidx.lifecycle.Observer {

            when (it.status) {

                Status.SUCCESS -> {
                    dialog.hideDialog()
                    Toast.makeText(this, "Save successfully", Toast.LENGTH_LONG).show()

                    finish()

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

    override fun onCheckClick(position: Int, userid: String,ischeck:Boolean,name:String) {

       // userData?.get(position)!!.ischeck = ischeck

        if(ischeck){
        UserList.add(userid)
           // UserList.add()
        }
        else{
            UserList.remove(userid)
        }

    }


}