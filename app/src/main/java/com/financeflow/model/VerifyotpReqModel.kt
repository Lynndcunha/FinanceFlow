package com.financeflow.model

data class VerifyotpReqModel (var email: String, var password: String)

data class VerifyOnlyotpReqModel (var email: String, var otp: String)