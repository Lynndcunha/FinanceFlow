package com.financeflow.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class PaidListesponseModel {
    @SerializedName("status")
    @Expose
    var status: Int? = null

    @SerializedName("data")
    @Expose
    var data: List<PAIDDatum>? = null
}

class PAIDDatum {
    @SerializedName("_id")
    @Expose
    var id: String? = null

    @SerializedName("userId")
    @Expose
    var userId: UserIdPaid? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("borrower")
    @Expose
    var borrower: BorrowerPaid? = null

    @SerializedName("amount")
    @Expose
    var amount: Any? = null

    @SerializedName("isSetteled")
    @Expose
    var isSetteled: Boolean? = null

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

class UserIdPaid {
    @SerializedName("_id")
    @Expose
    var id: String? = null

    @SerializedName("fullName")
    @Expose
    var fullName: String? = null
}

class BorrowerPaid {
    @SerializedName("_id")
    @Expose
    var id: String? = null

    @SerializedName("fullName")
    @Expose
    var fullName: String? = null
}