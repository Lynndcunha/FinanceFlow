
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
