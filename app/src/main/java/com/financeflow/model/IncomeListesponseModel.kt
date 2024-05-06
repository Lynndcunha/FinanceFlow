package com.financeflow.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class IncomeListesponseModel {
    @SerializedName("status")
    @Expose
    var status: Boolean? = null

    @SerializedName("data")
    @Expose
    var data: List<IData>? = null
}


class IData {
    @SerializedName("_id")
    @Expose
    var id: String? = null

    @SerializedName("userId")
    @Expose
    var userId: String? = null

    @SerializedName("income")
    @Expose
    var income: List<Income>? = null

    @SerializedName("createdAt")
    @Expose
    var createdAt: String? = null

    @SerializedName("updatedAt")
    @Expose
    var updatedAt: String? = null

    @SerializedName("__v")
    @Expose
    var v: Int? = null
}


class Income : Serializable {
    @SerializedName("incomeType")
    @Expose
    var incomeType: String? = null

    @SerializedName("incomeAmount")
    @Expose
    var incomeAmount: Int? = null

    @SerializedName("_id")
    @Expose
    var id: String? = null
}