package com.financeflow.screen

import CommonViewModel
import Status
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.financeflow.R
import com.financeflow.application.Myapp
import com.financeflow.base.BaseActivity
import com.financeflow.model.LoginReqModel
import com.financeflow.model.SignupReqModel
import com.financeflow.util.PrefManager
import com.financeflow.util.ValidationUtils
import com.financeflow.utils.CustomDialog
import com.financeflow.utils.NetworkUtil
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.financeflow.model.BudgetModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_home.btn_Income
import kotlinx.android.synthetic.main.activity_home.btn_exp
import kotlinx.android.synthetic.main.activity_home.btn_expence
import kotlinx.android.synthetic.main.activity_home.btn_goal
import kotlinx.android.synthetic.main.activity_home.btn_split
import kotlinx.android.synthetic.main.activity_home.homepiechart
import kotlinx.android.synthetic.main.activity_home.logout
import kotlinx.android.synthetic.main.activity_home.noti
import kotlinx.android.synthetic.main.activity_home.profile
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_updategraphbudget.piechart
import kotlinx.android.synthetic.main.split_list.request
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class HomeActivity :BaseActivity(), View.OnClickListener {

    lateinit var viewModel: CommonViewModel
    lateinit var dialog: CustomDialog
    lateinit var mypref: PrefManager
    private val RC_SIGN_IN = 1
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private var callbackManager: CallbackManager? = null
    lateinit var loginManager: LoginManager
    lateinit var validation: ValidationUtils
    lateinit var gson : Gson
    var incomeAmount: Double = 0.0
    var expenseAmount: Double = 0.0
    var total :Double = 0.0
    val listPie = ArrayList<PieEntry>()
    val listColors = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)
        mypref = PrefManager(this)
        dialog = CustomDialog(this)
         gson = Gson()
        dialog.hidSystemUI()




        validation = ValidationUtils(this)

        //txt_back.setOnClickListener { onClick(txt_back) }
        btn_Income.setOnClickListener { onClick(btn_Income) }
        btn_expence.setOnClickListener { onClick(btn_expence) }
        btn_goal.setOnClickListener { onClick(btn_goal) }
        btn_exp.setOnClickListener { onClick(btn_exp) }
        btn_split.setOnClickListener { onClick(btn_split) }

        //fb.setOnClickListener { onClick(fb) }
        //google.setOnClickListener { onClick(google) }

        /*txt_link1.setOnClickListener {
            txt_link1.setMovementMethod(LinkMovementMethod.getInstance());

        }*/


        logout.setOnClickListener {

            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent)
            finish()

        }
        noti.setOnClickListener {

            val intent = Intent(this, NotificationListActivity::class.java)
            startActivity(intent)

        }

        profile.setOnClickListener {

            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)

        }


        setupViewModel()
        setupObserver()


    }

    fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(CommonViewModel::class.java)
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
    fun setupObserver() {


        viewModel.getIncomeList().observe(this, androidx.lifecycle.Observer {

            when (it.status) {

                Status.SUCCESS -> {
                    dialog.hideDialog()
                    /* Glide.with(this).load(it.data!!.data!![0].mainBanner).centerCrop().into(banner1)
                     competationAdapter.setList(it.data.data!!)*/
                    incomeAmount =0.0 ;
                    it.data?.data?.let {
                            it1 -> it1.forEach {
                              incomeAmount = it.income?.get(0)?.incomeAmount?.plus(incomeAmount) ?: 0.0
                    }
                         }

                    viewModel.FetchExpense(mypref.userid.toString())


                    btn_Income.setText("Income\n"+"$"+incomeAmount.toString())
                    Log.d("NOTISIZE:", incomeAmount.toString())


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

        viewModel.getExpenseList().observe(this, androidx.lifecycle.Observer {

            when (it.status) {

                Status.SUCCESS -> {
                    dialog.hideDialog()
                    expenseAmount = 0.0
                    /* Glide.with(this).load(it.data!!.data!![0].mainBanner).centerCrop().into(banner1)
                     competationAdapter.setList(it.data.data!!)*/
                    Log.d("NOTISIZE:", it.data?.data?.size.toString())
                    it.data?.data?.let { it1 ->

                        it1.forEach {

                            expenseAmount =
                                it.amount.toString().toDouble().plus(expenseAmount)

                        }

                    }

                    btn_exp.setText("Expenses\n"+"$"+expenseAmount.toString())

                    total = incomeAmount.plus(expenseAmount)

                    var ipercentage = (incomeAmount / total) * 100

                    Log.d("PER:",ipercentage.toString())
                    listPie.add(PieEntry(ipercentage.toFloat(), "Income"))
                    listColors.add(resources.getColor(R.color.pink))


                    var Epercentage = (expenseAmount / total) * 100

                    Log.d("PER:",Epercentage.toString())
                    listPie.add(PieEntry(Epercentage.toFloat(), "Expense"))
                    listColors.add(resources.getColor(R.color.white))

                    if(incomeAmount > expenseAmount){

                      var balance = incomeAmount.minus(expenseAmount)

                        var Bpercentage = (balance / total) * 100

                        Log.d("PER:",Bpercentage.toString())
                        listPie.add(PieEntry(Bpercentage.toFloat(), "Balance"))
                        listColors.add(resources.getColor(R.color.red_btn_bg_color))

                    }
                    if(incomeAmount < expenseAmount){

                        var balance = expenseAmount.minus(incomeAmount)

                        var Bpercentage = (balance / total) * 100

                        Log.d("PER:",Bpercentage.toString())
                        listPie.add(PieEntry(Bpercentage.toFloat(), "Balance"))
                        listColors.add(resources.getColor(R.color.red_btn_bg_color))

                    }

                    val pieDataSet = PieDataSet(listPie, "")
                    pieDataSet.colors = listColors
                    pieDataSet.setDrawValues(false);

                    val pieData = PieData(pieDataSet)
                    //pieData.setValueTextSize(CommonUtils.convertDpToSp(14))
                    homepiechart.data = pieData

                    homepiechart.setHoleRadius(30f);
                    homepiechart.setUsePercentValues(true)
                    homepiechart.legend.isEnabled = false
                    homepiechart.setDrawEntryLabels(true)
                    homepiechart.setDrawCenterText(true);
                    homepiechart.setDrawHoleEnabled(true);
                    homepiechart.isDrawHoleEnabled = true
                    homepiechart.description.isEnabled = true
                    homepiechart.setEntryLabelColor(R.color.white)
                    homepiechart.setHoleColor(R.color.white);
                    homepiechart.setEntryLabelTextSize(8f);

                    homepiechart.setTransparentCircleRadius(10f);
                    homepiechart.animateY(1400, Easing.EaseInOutQuad)


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


    override fun onClick(v: View?) {
        when (v!!.id) {

            R.id.btn_Income -> {
                val intent = Intent(this, IncomeListActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_expence -> {
                val intent = Intent(this, BudgetListActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_goal -> {
                val intent = Intent(this, GoalListActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_exp -> {
                val intent = Intent(this, ExpenseListActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_split -> {
                val intent = Intent(this, SplitListActivity::class.java)
                startActivity(intent)
            }
        }
    }







}