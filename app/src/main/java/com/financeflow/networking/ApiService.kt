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


    @POST("user/create-budget")
    fun SAVEBUDGET(@Body signupReqModel: BudgetReqModel ): Call<BudgetModel>

    @POST("user/update-budget")
    fun UPDATEBUDGET(@Body signupReqModel: UpdateBudgetReqModel ): Call<updateBudgetModel>


    @GET("user/budget-list")
    fun GETBUDGET(@Query("userId") userId:String ): Call<BudgetListModel>


    @POST("user/create-income")
    fun SAVEINCOME(@Body signupReqModel: IncomeReqModel): Call<IncomeModel>


    @GET("user/income-list")
    fun GETINCOME(@Query("userId") userId:String ): Call<IncomeListesponseModel>

    @POST("user/update-income")
    fun UPDATEINCOME(@Body signupReqModel: UpdateIncomeReqModel): Call<UpdateIncomeModel>


    @POST("user/create-goal")
    fun SAVEGOAL(@Body signupReqModel: GoalReqModel): Call<GoalModel>

    @GET("user/list-goal")
    fun GETGOAL(@Query("userId") userId:String ): Call<GoalListesponseModel>


    @POST("user/edit-goal")
    fun UPDATEGOAL(@Body signupReqModel: GoalUpdateReqModel ): Call<goalupdateBudgetModel>



    @POST("user/create-expense")
    fun SAVEEXPENSE(@Body signupReqModel: ExpenseReqModel): Call<ExpenseModel>


    @GET("user/list-expense")
    fun GETEXPENSE(@Query("userId") userId:String ): Call<ExpenseListesponseModel>


    @POST("user/edit-expense")
    fun UPDATEEXPENSE(@Body signupReqModel: ExpenseUpdateReqModel ): Call<expenseupdateBudgetModel>



}

