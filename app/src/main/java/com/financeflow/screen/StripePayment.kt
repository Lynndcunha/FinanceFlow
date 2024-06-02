package com.financeflow.screen

import CommonViewModel
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.financeflow.R
import com.financeflow.utils.CustomDialog
import com.stripe.android.ApiResultCallback
import com.stripe.android.PaymentConfiguration
import com.stripe.android.PaymentIntentResult
import com.stripe.android.Stripe
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.model.PaymentMethodCreateParams
import com.stripe.android.model.StripeIntent
import com.stripe.android.view.CardInputWidget
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class StripePayment : AppCompatActivity() {

    private lateinit var stripe: Stripe
    lateinit var viewModel: CommonViewModel
    lateinit var dialog: CustomDialog
    lateinit var params: PaymentMethodCreateParams
    var resultPay : String = "pending"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        dialog = CustomDialog(this)

        // Initialize Stripe SDK
        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51PLTGkDuHl5rdm9s1FhuLIfewmEDKXHlw6gOvXmd4vzMGVl9l14eNWeQaaw4s5rWTmPGbbLqJzCqafZeSY2cUNO300HNUepcsu"
        )
        stripe = Stripe(
            applicationContext,
            PaymentConfiguration.getInstance(applicationContext).publishableKey
        )

        // Initialize CardInputWidget
        val cardInputWidget: CardInputWidget = findViewById(R.id.cardInputWidget)

        val payButton: Button = findViewById(R.id.payButton)
        payButton.setOnClickListener {
             params = cardInputWidget.paymentMethodCreateParams!!
            if (params != null) {

                viewModel.Payment();
              //  createPaymentIntent(params)
            } else {
                Log.d("PRM:","null")
                // Handle error: CardInputWidget is empty
            }
        }

        setupViewModel()
        setupObserver()
    }

    override fun onStart() {
        super.onStart()


    }

    fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(CommonViewModel::class.java)
    }

    fun setupObserver() {

        viewModel.getPayment().observe(this, androidx.lifecycle.Observer {

            when (it.status) {

                Status.SUCCESS -> {

                    Log.d("PAY:",it.data.toString())
                    /* Glide.with(this).load(it.data!!.data!![0].mainBanner).centerCrop().into(banner1)
                     competationAdapter.setList(it.data.data!!)*/
                    //  viewModel.PendingList(mypref.userid.toString())
                    //  viewModel.PaidList(mypref.userid.toString())
                    val confirmParams =
                        ConfirmPaymentIntentParams.createWithPaymentMethodCreateParams(
                            params,
                            it.data.toString()
                        )
                    runOnUiThread {
                        stripe.confirmPayment(this@StripePayment, confirmParams)
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

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        stripe.onPaymentResult(requestCode, data, object : ApiResultCallback<PaymentIntentResult> {

            override fun onSuccess(result: PaymentIntentResult) {
                dialog.hideDialog()

                val paymentIntent = result.intent
                val status = paymentIntent.status
                if (status == StripeIntent.Status.Succeeded) {
                    // Payment succeeded
                    resultPay = "success"
                    println("Payment succeeded: ${paymentIntent.toString()}")
                } else if (status == StripeIntent.Status.RequiresPaymentMethod) {
                    // Payment failed
                    resultPay = "fail"
                    println("Payment failed")
                }

                val resultIntent = Intent()
                resultIntent.putExtra("resultKey", resultPay) // Replace with your key-value pair
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }

            override fun onError(e: Exception) {
                // Payment request failed

                dialog.hideDialog()

                resultPay = "fail"
                val resultIntent = Intent()
                resultIntent.putExtra("resultKey", resultPay) // Replace with your key-value pair
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
                println("Payment failed: ${e.message}")
            }
        })
    }
}
