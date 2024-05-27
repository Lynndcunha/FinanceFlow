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
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
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
import kotlinx.android.synthetic.main.activity_updategraphbudget.edit
import kotlinx.android.synthetic.main.activity_updategraphbudget.piechart
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*


class UpdateGraphBudgetActivity : BaseActivity(), View.OnClickListener {

    lateinit var viewModel: CommonViewModel
    lateinit var dialog: CustomDialog
    lateinit var mypref: PrefManager
    lateinit var validation: ValidationUtils
    lateinit var gson : Gson
    lateinit var  date: String
    lateinit var filterDataModel : BudgetData
    var total :Int = 0
    val listPie = ArrayList<PieEntry>()
    val listColors = ArrayList<Int>()

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

        setContentView(R.layout.activity_updategraphbudget)

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

            total += filterDataModel.rent!!

        }
        filterDataModel.electricityBill?.let {

            edtxt_elect.setText(filterDataModel.electricityBill.toString())

            total += filterDataModel.electricityBill!!

        }

        filterDataModel.phoneBill?.let {

            edtxt_phone.setText(filterDataModel.phoneBill.toString())
            total += filterDataModel.phoneBill!!

        }
        filterDataModel.internetBill?.let {

            edtxt_internet.setText(filterDataModel.internetBill.toString())
            total += filterDataModel.internetBill!!

        }
        filterDataModel.studentLoan?.let {

            edtxt_loan.setText(filterDataModel.studentLoan.toString())
            total += filterDataModel.studentLoan!!

        }
        filterDataModel.grocery?.let {

            edtxt_grocery.setText(filterDataModel.grocery.toString())
            total += filterDataModel.grocery!!

        }
        filterDataModel.gym?.let {

            edtxt_gym.setText(filterDataModel.gym.toString())
            total += filterDataModel.gym!!

        }
        filterDataModel.dineOut?.let {

            edtxt_dining.setText(filterDataModel.dineOut.toString())
            total += filterDataModel.dineOut!!

        }
        filterDataModel.savings?.let {

            edtxt_saving.setText(filterDataModel.savings.toString())
            total += filterDataModel.savings!!

        }
        filterDataModel.subscriptions?.let {

            edtxt_subscr.setText(filterDataModel.subscriptions.toString())
            total += filterDataModel.subscriptions!!

        }
        filterDataModel.others?.let {

            edtxt_other.setText(filterDataModel.others.toString())
            total += filterDataModel.others!!

        }




        Log.d("TOT:",total.toString())



        filterDataModel.rent?.let {
            edtxt_rent.setText(filterDataModel.rent.toString())


            var percentage = (filterDataModel.rent!!.toDouble() / total.toDouble()) * 100

            listPie.add(PieEntry(percentage.toFloat(), "Rent"))
            listColors.add(resources.getColor(R.color.pink))

        }
        filterDataModel.electricityBill?.let {

            edtxt_elect.setText(filterDataModel.electricityBill.toString())

            var percentage = (filterDataModel.electricityBill!!.toDouble() / total.toDouble()) * 100

            listPie.add(PieEntry(percentage.toFloat(), "Eletricity"))
            listColors.add(resources.getColor(R.color.white))


        }

        filterDataModel.phoneBill?.let {

            edtxt_phone.setText(filterDataModel.phoneBill.toString())
            var percentage = (filterDataModel.phoneBill!!.toDouble() / total.toDouble()) * 100

            listPie.add(PieEntry(percentage.toFloat(), "Phone bill"))
            listColors.add(resources.getColor(R.color.grey))

        }
        filterDataModel.internetBill?.let {

            edtxt_internet.setText(filterDataModel.internetBill.toString())
            var percentage = (filterDataModel.internetBill!!.toDouble() / total.toDouble()) * 100

            listPie.add(PieEntry(percentage.toFloat(), "Internet"))
            listColors.add(resources.getColor(R.color.color_1f5af6))

        }
        filterDataModel.studentLoan?.let {

            edtxt_loan.setText(filterDataModel.studentLoan.toString())
            var percentage = (filterDataModel.studentLoan!!.toDouble() / total.toDouble()) * 100

            listPie.add(PieEntry(percentage.toFloat(), "Loan"))
            listColors.add(resources.getColor(R.color.badgeColor))

        }
        filterDataModel.grocery?.let {

            edtxt_grocery.setText(filterDataModel.grocery.toString())
            var percentage = (filterDataModel.grocery!!.toDouble() / total.toDouble()) * 100

            listPie.add(PieEntry(percentage.toFloat(), "Grocery"))
            listColors.add(resources.getColor(R.color.blue_violet))

        }
        filterDataModel.gym?.let {

            edtxt_gym.setText(filterDataModel.gym.toString())
            var percentage = (filterDataModel.gym!!.toDouble() / total.toDouble()) * 100

            listPie.add(PieEntry(percentage.toFloat(), "Gym"))
            listColors.add(resources.getColor(R.color.borderColor))

        }
        filterDataModel.dineOut?.let {

            edtxt_dining.setText(filterDataModel.dineOut.toString())
            var percentage = (filterDataModel.dineOut!!.toDouble() / total.toDouble()) * 100

            listPie.add(PieEntry(percentage.toFloat(), "Dineout"))
            listColors.add(resources.getColor(R.color.carousel_arrow_bg_color))

        }
        filterDataModel.savings?.let {

            edtxt_saving.setText(filterDataModel.savings.toString())
            var percentage = (filterDataModel.savings!!.toDouble() / total.toDouble()) * 100

            listPie.add(PieEntry(percentage.toFloat(), "Saving"))
            listColors.add(resources.getColor(R.color.teal_700))

        }
        filterDataModel.subscriptions?.let {

            edtxt_subscr.setText(filterDataModel.subscriptions.toString())
            var percentage = (filterDataModel.subscriptions!!.toDouble() / total.toDouble()) * 100

            listPie.add(PieEntry(percentage.toFloat(), "Subscription"))
            listColors.add(resources.getColor(R.color.cb_blue_button_light))

        }
        filterDataModel.others?.let {

            edtxt_other.setText(filterDataModel.others.toString())
            var percentage = (filterDataModel.others!!.toDouble() / total.toDouble()) * 100

            listPie.add(PieEntry(percentage.toFloat(), "Other"))
            listColors.add(resources.getColor(R.color.cb_divider_color))

        }

        val pieDataSet = PieDataSet(listPie, "")
        pieDataSet.colors = listColors
        pieDataSet.setDrawValues(false);

        val pieData = PieData(pieDataSet)
        //pieData.setValueTextSize(CommonUtils.convertDpToSp(14))
        piechart.data = pieData

        piechart.setHoleRadius(30f);
        piechart.setUsePercentValues(true)
        piechart.legend.isEnabled = false
        piechart.setDrawEntryLabels(true)
        piechart.setDrawCenterText(true);
        piechart.setDrawHoleEnabled(true);
        piechart.isDrawHoleEnabled = true
        piechart.description.isEnabled = true
        piechart.setEntryLabelColor(R.color.white)
        piechart.setHoleColor(R.color.white);
        piechart.setEntryLabelTextSize(8f);

        piechart.setTransparentCircleRadius(10f);
        piechart.animateY(1400, Easing.EaseInOutQuad)


        edit.setOnClickListener {

            val intent = Intent(this, UpdateBudgetActivity::class.java)
            intent.putExtra("value", filterDataModel)
            this.startActivity(intent)

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