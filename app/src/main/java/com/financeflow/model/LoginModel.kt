package com.financeflow.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class LoginModel {
    @SerializedName("data")
    @Expose
    var data: LoginData? = null

    @SerializedName("status")
    @Expose
     var status: Boolean? = null

    @SerializedName("message")
    @Expose
     var message: String? = null
}