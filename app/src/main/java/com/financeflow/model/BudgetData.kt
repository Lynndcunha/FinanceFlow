package com.financeflow.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class BudgetData : Serializable {
    @SerializedName("_id")
    @Expose
    var id: String? = null

    @SerializedName("userId")
    @Expose
    var userId: String? = null

    @SerializedName("rent")
    @Expose
    var rent: Int? = null

    @SerializedName("electricityBill")
    @Expose
    var electricityBill: Int? = null

    @SerializedName("createdAt")
    @Expose
    var createdAt: String? = null

    @SerializedName("updatedAt")
    @Expose
    var updatedAt: String? = null

    @SerializedName("__v")
    @Expose
    var v: Int? = null

    @SerializedName("phoneBill")
    @Expose
    var phoneBill: Int? = null

    @SerializedName("internetBill")
    @Expose
    var internetBill: Int? = null

    @SerializedName("studentLoan")
    @Expose
    var studentLoan: Int? = null

    @SerializedName("grocery")
    @Expose
    var grocery: Int? = null

    @SerializedName("gym")
    @Expose
    var gym: Int? = null

    @SerializedName("dineOut")
    @Expose
    var dineOut: Int? = null

    @SerializedName("savings")
    @Expose
    var savings: Int? = null

    @SerializedName("subscriptions")
    @Expose
    var subscriptions: Int? = null

    @SerializedName("others")
    @Expose
    var others: Int? = null
}