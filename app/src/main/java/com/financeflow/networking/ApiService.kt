package com.financeflow.networking


import com.financeflow.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    @POST("auth/login")
    fun LOGIN(@Body loginReqModel: LoginReqModel ): Call<LoginModel>

    @POST("auth/register")
    fun SIGNUP(@Body signupReqModel: SignupReqModel ): Call<SignupModel>

    @POST("auth/forgot-password")
    fun FORGET(@Body forgetReqModel: ForgetReqModel ): Call<ForgetpassModel>

    @POST("auth/reset-password")
    fun VERIFYOTP(@Body verifyotpReqModel: VerifyotpReqModel ): Call<VerifyotpModel>

    @POST("auth/verify-otp")
    fun VERIFYONLYOTP(@Body verifyotpReqModel: VerifyOnlyotpReqModel ): Call<VerifyOnlyotpModel>





}

