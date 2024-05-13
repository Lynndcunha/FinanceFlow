package com.financeflow.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ExpenseListesponseModel {
    @SerializedName("status")
    @Expose
    var status: Boolean? = null

    @SerializedName("data")
    @Expose
    var data: List<ExpenseDatum>? = null
}