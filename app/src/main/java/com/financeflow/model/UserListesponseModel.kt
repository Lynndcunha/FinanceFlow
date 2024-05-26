package com.financeflow.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class UserListesponseModel {
    @SerializedName("status")
    @Expose
    var status: Boolean? = null

    @SerializedName("data")
    @Expose
    var data: List<UserDatum>? = null
}


class UserDatum {
    @SerializedName("_id")
    @Expose
    var id: String? = null

    @SerializedName("fullName")
    @Expose
    var fullName: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("check")
    @Expose
    var ischeck: Boolean? = null
}