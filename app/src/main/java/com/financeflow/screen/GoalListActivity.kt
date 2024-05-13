package com.financeflow.screen

import CommonViewModel
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
import com.financeflow.adapter.GoalAdapter
import com.financeflow.adapter.IncomeAdapter
import com.financeflow.util.PrefManager
import com.financeflow.utils.CustomDialog
import com.financeflow.utils.NetworkUtil
import kotlinx.android.synthetic.main.budget_list.recycler_chat
import kotlinx.android.synthetic.main.income_list.btn_add
import kotlinx.android.synthetic.main.income_list.txt_back


class GoalListActivity : AppCompatActivity() {

    lateinit var chatAdapter:GoalAdapter
    lateinit var viewModel: CommonViewModel
    lateinit var dialog: CustomDialog
    lateinit var mypref: PrefManager
    lateinit var albumid :String
    lateinit var gson : Gson
    var mobile : String = "9769363545"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.goal_list)

        mypref = PrefManager(this)
        dialog = CustomDialog(this)




        val mLayoutManager = LinearLayoutManager(this)
        chatAdapter = GoalAdapter(this)
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



}