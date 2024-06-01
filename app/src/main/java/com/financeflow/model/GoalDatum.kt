package com.financeflow.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class GoalDatum : Serializable  {
    @SerializedName("_id")
    @Expose
    var id: String? = null

    @SerializedName("userId")
    @Expose
    var userId: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("amount")
    @Expose
    var amount: Int? = null

    @SerializedName("date")
    @Expose
    var date: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("createdAt")
    @Expose
    var createdAt: String? = null

    @SerializedName("updatedAt")
    @Expose
    var updatedAt: String? = null

    @SerializedName("__v")
    @Expose
    var v: Int? = null

    @SerializedName("savedAmount")
    @Expose
    var savedAmount: List<SavedAmount>? = null
}