package com.financeflow.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class SavedAmount : Serializable {
    @SerializedName("amount")
    @Expose
    var amount: Int? = null

    @SerializedName("date")
    @Expose
    var date: String? = null
}