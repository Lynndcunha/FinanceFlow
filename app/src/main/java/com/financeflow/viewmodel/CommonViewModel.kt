
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.financeflow.model.*
import com.financeflow.networking.RetrofitBuilder
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class CommonViewModel (application: Application) : AndroidViewModel(application) {

     var loginmodel : MutableLiveData<Resource<LoginModel>> = MutableLiveData()
     var signupmodel : MutableLiveData<Resource<SignupModel>> = MutableLiveData()
    var forgetmodel : MutableLiveData<Resource<ForgetpassModel>> = MutableLiveData()
    var verifymodel : MutableLiveData<Resource<VerifyotpModel>> = MutableLiveData()
    var verifyonlymodel : MutableLiveData<Resource<VerifyOnlyotpModel>> = MutableLiveData()
    var budgetmodel : MutableLiveData<Resource<BudgetModel>> = MutableLiveData()
    var budgetlistmodel : MutableLiveData<Resource<BudgetListModel>> = MutableLiveData()
    var updatebudgetmodel : MutableLiveData<Resource<updateBudgetModel>> = MutableLiveData()
    var incomemodel : MutableLiveData<Resource<IncomeModel>> = MutableLiveData()
    var incomelistmodel : MutableLiveData<Resource<IncomeListesponseModel>> = MutableLiveData()
    var updateincomemodel : MutableLiveData<Resource<UpdateIncomeModel>> = MutableLiveData()






    fun Login(loginReqModel: LoginReqModel){

        loginmodel.postValue(Resource.loading(null))

        val call: Call<LoginModel> = RetrofitBuilder.apiService.LOGIN(loginReqModel)
        call.enqueue(object : Callback<LoginModel> {

            override fun onResponse(call: Call<LoginModel>?, response: Response<LoginModel>?) {

                Log.d("RESP:",response?.code().toString())

                if(response!!.isSuccessful) {

                    if(response.body()!!.status == true) {
                        loginmodel.postValue(Resource.success(response?.body()) as Resource<LoginModel>?)

                    }

                    if(response.body()!!.status == false) {
                        loginmodel.postValue(Resource.error(null, response.body()!!.message.toString()))
                    }

                    }
                else{
                    if(response.code().equals(500)){
                        loginmodel.postValue(Resource.error(null,"Internal Server Error"))

                    }
                    else {
                            loginmodel.postValue(Resource.error(null, "something went wrong"))
                    }
                }
            }

            override fun onFailure(call: Call<LoginModel>?, t: Throwable?) {

                 loginmodel.postValue(Resource.error(null,"something went wrong"))
            }

        })

    }

    fun Signup(signupReqModel: SignupReqModel){

        signupmodel.postValue(Resource.loading(null))

        val call: Call<SignupModel> = RetrofitBuilder.apiService.SIGNUP(signupReqModel)
        call.enqueue(object : Callback<SignupModel> {

            override fun onResponse(call: Call<SignupModel>?, response: Response<SignupModel>?) {

                Log.d("RESP:",response?.code().toString())

                if(response!!.isSuccessful) {

                    if(response.body()!!.status == true) {
                        signupmodel.postValue(Resource.success(response?.body()) as Resource<SignupModel>?)

                    }

                    if(response.body()!!.status == false) {
                        signupmodel.postValue(Resource.error(null, response.body()!!.message.toString()))
                    }

                }
                else{
                    if(response.code().equals(500)){
                        signupmodel.postValue(Resource.error(null,"Internal Server Error"))

                    }
                    else {

                        signupmodel.postValue(Resource.error(null, "something went wrong"))
                    }
                }
            }

            override fun onFailure(call: Call<SignupModel>?, t: Throwable?) {

                signupmodel.postValue(Resource.error(null,"something went wrong"))
            }

        })

    }

    fun SaveBudget(signupReqModel: BudgetReqModel){

        budgetmodel.postValue(Resource.loading(null))

        val call: Call<BudgetModel> = RetrofitBuilder.apiService.SAVEBUDGET(signupReqModel)
        call.enqueue(object : Callback<BudgetModel> {

            override fun onResponse(call: Call<BudgetModel>?, response: Response<BudgetModel>?) {

                Log.d("RESP:",response?.code().toString())

                if(response!!.isSuccessful) {

                    if(response.body()!!.status == true) {
                        budgetmodel.postValue(Resource.success(response?.body()) as Resource<BudgetModel>?)

                    }

                    if(response.body()!!.status == false) {
                        budgetmodel.postValue(Resource.error(null, "Error"))
                    }

                }
                else{
                    if(response.code().equals(500)){
                        budgetmodel.postValue(Resource.error(null,"Internal Server Error"))

                    }
                    else {

                        budgetmodel.postValue(Resource.error(null, "something went wrong"))
                    }
                }
            }

            override fun onFailure(call: Call<BudgetModel>?, t: Throwable?) {

                budgetmodel.postValue(Resource.error(null,"something went wrong"))
            }

        })

    }

    fun UpdateBudget(signupReqModel: UpdateBudgetReqModel){

        updatebudgetmodel.postValue(Resource.loading(null))

        val call: Call<updateBudgetModel> = RetrofitBuilder.apiService.UPDATEBUDGET(signupReqModel)
        call.enqueue(object : Callback<updateBudgetModel> {

            override fun onResponse(call: Call<updateBudgetModel>?, response: Response<updateBudgetModel>?) {

                Log.d("RESP:",response?.code().toString())

                if(response!!.isSuccessful) {

                    if(response.body()!!.status == true) {
                        updatebudgetmodel.postValue(Resource.success(response?.body()) as Resource<updateBudgetModel>?)

                    }

                    if(response.body()!!.status == false) {
                        updatebudgetmodel.postValue(Resource.error(null, "Error"))
                    }

                }
                else{
                    if(response.code().equals(500)){
                        updatebudgetmodel.postValue(Resource.error(null,"Internal Server Error"))

                    }
                    else {

                        updatebudgetmodel.postValue(Resource.error(null, "something went wrong"))
                    }
                }
            }

            override fun onFailure(call: Call<updateBudgetModel>?, t: Throwable?) {

                updatebudgetmodel.postValue(Resource.error(null,"something went wrong"))
            }

        })

    }

    fun FetchBudget(userid: String){

        budgetlistmodel.postValue(Resource.loading(null))

        val call: Call<BudgetListModel> = RetrofitBuilder.apiService.GETBUDGET(userid)
        call.enqueue(object : Callback<BudgetListModel> {

            override fun onResponse(call: Call<BudgetListModel>?, response: Response<BudgetListModel>?) {

                Log.d("RES",response.toString());

                if(response!!.isSuccessful) {

                    if(response.body()!!.status == true) {
                        budgetlistmodel.postValue(Resource.success(response?.body()) as Resource<BudgetListModel>?)

                    }

                    if(response.body()!!.status == false) {
                        budgetlistmodel.postValue(Resource.error(null, "No data found"))
                    }

                }
                else{
                    if(response.code().equals(500)){
                        budgetlistmodel.postValue(Resource.error(null,"Internal Server Error"))

                    }
                    else {

                        budgetlistmodel.postValue(Resource.error(null, "something went wrong"))
                    }
                }
            }

            override fun onFailure(call: Call<BudgetListModel>?, t: Throwable?) {

                budgetlistmodel.postValue(Resource.error(null,"something went wrong"))
            }

        })

    }

    fun FetchIncome(userid: String){

        incomelistmodel.postValue(Resource.loading(null))

        val call: Call<IncomeListesponseModel> = RetrofitBuilder.apiService.GETINCOME(userid)
        call.enqueue(object : Callback<IncomeListesponseModel> {

            override fun onResponse(call: Call<IncomeListesponseModel>?, response: Response<IncomeListesponseModel>?) {

                Log.d("RES",response.toString());

                if(response!!.isSuccessful) {

                    if(response.body()!!.status == true) {
                        incomelistmodel.postValue(Resource.success(response?.body()) as Resource<IncomeListesponseModel>?)

                    }

                    if(response.body()!!.status == false) {
                        incomelistmodel.postValue(Resource.error(null, "No data found"))
                    }

                }
                else{
                    if(response.code().equals(500)){
                        incomelistmodel.postValue(Resource.error(null,"Internal Server Error"))

                    }
                    else {

                        incomelistmodel.postValue(Resource.error(null, "something went wrong"))
                    }
                }
            }

            override fun onFailure(call: Call<IncomeListesponseModel>?, t: Throwable?) {

                incomelistmodel.postValue(Resource.error(null,"something went wrong"))
            }

        })

    }


    fun SaveIncome(signupReqModel: IncomeReqModel){

        incomemodel.postValue(Resource.loading(null))

        val call: Call<IncomeModel> = RetrofitBuilder.apiService.SAVEINCOME(signupReqModel)
        call.enqueue(object : Callback<IncomeModel> {

            override fun onResponse(call: Call<IncomeModel>?, response: Response<IncomeModel>?) {

                Log.d("RESP:",response?.code().toString())

                if(response!!.isSuccessful) {

                    if(response.body()!!.status == true) {
                        incomemodel.postValue(Resource.success(response?.body()) as Resource<IncomeModel>?)

                    }

                    if(response.body()!!.status == false) {
                        incomemodel.postValue(Resource.error(null, "Error"))
                    }

                }
                else{
                    if(response.code().equals(500)){
                        incomemodel.postValue(Resource.error(null,"Internal Server Error"))

                    }
                    else {

                        incomemodel.postValue(Resource.error(null, "something went wrong"))
                    }
                }
            }

            override fun onFailure(call: Call<IncomeModel>?, t: Throwable?) {

                incomemodel.postValue(Resource.error(null,"something went wrong"))
            }

        })

    }


    fun UpdateIncome(signupReqModel: UpdateIncomeReqModel){

        updateincomemodel.postValue(Resource.loading(null))

        val call: Call<UpdateIncomeModel> = RetrofitBuilder.apiService.UPDATEINCOME(signupReqModel)
        call.enqueue(object : Callback<UpdateIncomeModel> {

            override fun onResponse(call: Call<UpdateIncomeModel>?, response: Response<UpdateIncomeModel>?) {

                Log.d("RESP:",response?.code().toString())

                if(response!!.isSuccessful) {

                    if(response.body()!!.status == true) {
                        updateincomemodel.postValue(Resource.success(response?.body()) as Resource<UpdateIncomeModel>?)

                    }

                    if(response.body()!!.status == false) {
                        updateincomemodel.postValue(Resource.error(null, "Error"))
                    }

                }
                else{
                    if(response.code().equals(500)){
                        updateincomemodel.postValue(Resource.error(null,"Internal Server Error"))

                    }
                    else {

                        updateincomemodel.postValue(Resource.error(null, "something went wrong"))
                    }
                }
            }

            override fun onFailure(call: Call<UpdateIncomeModel>?, t: Throwable?) {

                updateincomemodel.postValue(Resource.error(null,"something went wrong"))
            }

        })

    }


    fun Forgetpassword(forgetReqModel: ForgetReqModel){

        forgetmodel.postValue(Resource.loading(null))

        val call: Call<ForgetpassModel> = RetrofitBuilder.apiService.FORGET(forgetReqModel)
        call.enqueue(object : Callback<ForgetpassModel> {

            override fun onResponse(call: Call<ForgetpassModel>?, response: Response<ForgetpassModel>?) {


                if(response!!.isSuccessful) {

                    if(response.body()!!.status == true) {
                        forgetmodel.postValue(Resource.success(response?.body()) as Resource<ForgetpassModel>?)

                    }

                    if(response.body()!!.status == false) {
                        forgetmodel.postValue(Resource.error(null, response.body()!!.message.toString()))
                    }

                }
                else{
                    if(response.code().equals(500)){
                        forgetmodel.postValue(Resource.error(null,"Internal Server Error"))

                    }
                    else {

                        forgetmodel.postValue(Resource.error(null, "something went wrong"))
                    }
                }
            }

            override fun onFailure(call: Call<ForgetpassModel>?, t: Throwable?) {

                forgetmodel.postValue(Resource.error(null,"something went wrong"))
            }

        })

    }

    fun VerifyOnlyOtp(verifyotpReqModel: VerifyOnlyotpReqModel){

        verifyonlymodel.postValue(Resource.loading(null))

        val call: Call<VerifyOnlyotpModel> = RetrofitBuilder.apiService.VERIFYONLYOTP(verifyotpReqModel)
        call.enqueue(object : Callback<VerifyOnlyotpModel> {

            override fun onResponse(call: Call<VerifyOnlyotpModel>?, response: Response<VerifyOnlyotpModel>?) {


                if(response!!.isSuccessful) {

                    if(response.body()!!.status == true) {
                        verifyonlymodel.postValue(Resource.success(response?.body()) as Resource<VerifyOnlyotpModel>?)

                    }

                    if(response.body()!!.status == false) {
                        verifyonlymodel.postValue(Resource.error(null, response.body()!!.message.toString()))
                    }

                }
                else{
                    if(response.code().equals(500)){
                        verifyonlymodel.postValue(Resource.error(null,"Internal Server Error"))

                    }
                    else {

                        verifyonlymodel.postValue(Resource.error(null, "something went wrong"))
                    }
                }
            }

            override fun onFailure(call: Call<VerifyOnlyotpModel>?, t: Throwable?) {

                verifyonlymodel.postValue(Resource.error(null,"something went wrong"))
            }

        })

    }

    fun VerifyOtp(verifyotpReqModel: VerifyotpReqModel){

        verifymodel.postValue(Resource.loading(null))

        val call: Call<VerifyotpModel> = RetrofitBuilder.apiService.VERIFYOTP(verifyotpReqModel)
        call.enqueue(object : Callback<VerifyotpModel> {

            override fun onResponse(call: Call<VerifyotpModel>?, response: Response<VerifyotpModel>?) {


                if(response!!.isSuccessful) {

                    if(response.body()!!.status == true) {
                        verifymodel.postValue(Resource.success(response?.body()) as Resource<VerifyotpModel>?)

                    }

                    if(response.body()!!.status == false) {
                        verifymodel.postValue(Resource.error(null, response.body()!!.message.toString()))
                    }

                }
                else{
                    if(response.code().equals(500)){
                        verifymodel.postValue(Resource.error(null,"Internal Server Error"))

                    }
                    else {

                        verifymodel.postValue(Resource.error(null, "something went wrong"))
                    }
                }
            }

            override fun onFailure(call: Call<VerifyotpModel>?, t: Throwable?) {

                verifymodel.postValue(Resource.error(null,"something went wrong"))
            }

        })

    }


    fun getLogin(): LiveData<Resource<LoginModel>> {
        return loginmodel
    }


    fun getSignup(): LiveData<Resource<SignupModel>> {
        return signupmodel
    }

    fun getBudget(): LiveData<Resource<BudgetModel>> {
        return budgetmodel
    }

    fun getIncome(): LiveData<Resource<IncomeModel>> {
        return incomemodel
    }

    fun getUpdateBudget(): LiveData<Resource<updateBudgetModel>> {
        return updatebudgetmodel
    }
    fun getBudgetList(): LiveData<Resource<BudgetListModel>> {
        return budgetlistmodel
    }

    fun getUpdateIncomet(): LiveData<Resource<UpdateIncomeModel>> {
        return updateincomemodel
    }
    fun getIncomeList(): LiveData<Resource<IncomeListesponseModel>> {
        return incomelistmodel
    }
    fun getForget(): LiveData<Resource<ForgetpassModel>> {
        return forgetmodel
    }
    fun getOtpVerify(): LiveData<Resource<VerifyotpModel>> {
        return verifymodel
    }

    fun getOnlyOtpVerify(): LiveData<Resource<VerifyOnlyotpModel>> {
        return verifyonlymodel
    }

    fun clearlogin() {
         loginmodel.postValue(null)
    }


}
