package com.financeflow.model


data class UserData(
    val id: String,
    val fullname: String,
    val email: String,
    var isChecked: Boolean
)