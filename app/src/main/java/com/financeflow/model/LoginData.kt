package com.financeflow.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class LoginData {
    @SerializedName("token")
    @Expose
    var token: String? = null

    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("fullName")
    @Expose
    var fullName: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null





}