package com.gurudev.junotes.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gurudev.junotes.RequestModel.LoginRequestModel
import com.gurudev.junotes.RequestModel.RegisterRequestModel
import com.gurudev.junotes.ResponseModel.Login.LoginResponseModel
import com.gurudev.junotes.ResponseModel.Register.RegisterResponseModel
import com.gurudev.junotes.ResponseModel.Register.YearResponseModel
import com.gurudev.junotes.Retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel : ViewModel() {

    val loginResponse = MutableLiveData<LoginResponseModel?>()
    val errorMessage = MutableLiveData<String>()
    val yearResponse = MutableLiveData<YearResponseModel?>()
    val registerResponse = MutableLiveData<RegisterResponseModel?>()


    fun login(username: String, password: String) {
        try {
            val model = LoginRequestModel(username,password)
            RetrofitInstance.api.login(model).enqueue(object : Callback<LoginResponseModel>{
                override fun onResponse(
                    call: Call<LoginResponseModel>,
                    response: Response<LoginResponseModel>
                ) {
                    if (response.isSuccessful)
                    {
                        val data = response.body()
                        if (data!=null)
                        {
                            if (data.STS =="200")
                            {
                                loginResponse.value = data
                            }
                            if (data.STS=="500")
                            {
                                errorMessage.value = data.MSG
                            }
                        }
                    }
                    else
                    {
                        errorMessage.value = "Something went wrong"
                        Log.d("Error","Something went wrong")

                    }
                }

                override fun onFailure(call: Call<LoginResponseModel>, t: Throwable) {
                    errorMessage.value = "Please check the email and password"
                    Log.d("Error",t.message.toString())
                }

            })
        }
        catch (e : Exception)
        {
            e.printStackTrace()
        }
    }

    fun getYear()
    {
        try {
            RetrofitInstance.api.getAllYears().enqueue(object : Callback<YearResponseModel>{
                override fun onResponse(
                    call: Call<YearResponseModel>,
                    response: Response<YearResponseModel>
                ) {
                    if (response.isSuccessful)
                    {
                        val data = response.body()
                        if (data!=null)
                        {
                            if (data.STS =="200")
                            {
                                yearResponse.value = data
                            }
                            if (data.STS=="500")
                            {
                                errorMessage.value = data.MSG
                            }
                        }
                    }
                    else
                    {
                        errorMessage.value = "Something went wrong"
                    }
                }

                override fun onFailure(call: Call<YearResponseModel>, t: Throwable) {
                    errorMessage.value = "something went wrong"
                }

            })
        }
        catch (e : Exception)
        {
            e.printStackTrace()
        }
    }

    fun register(fullName : String , phoneNo : String , email : String , password : String , year : Int){
        try {
            val model = RegisterRequestModel(email,fullName,password,phoneNo,year)
            RetrofitInstance.api.register(model).enqueue(object : Callback<RegisterResponseModel>{
                override fun onResponse(
                    call: Call<RegisterResponseModel>,
                    response: Response<RegisterResponseModel>
                ) {
                    if (response.isSuccessful)
                    {
                        val data = response.body()
                        if (data!=null)
                        {
                            if (data.STS =="200")
                            {
                                registerResponse.value = data
                            }
                            if (data.STS=="500")
                            {
                                errorMessage.value = data.MSG
                            }
                        }
                    }
                    else
                    {
                        errorMessage.value = "Something went wrong"
                    }
                }

                override fun onFailure(call: Call<RegisterResponseModel>, t: Throwable) {
                    errorMessage.value = "something went wrong"
                }

            })
        }
        catch (e : Exception)
        {
            e.printStackTrace()
        }
    }


    fun observeLogin() : MutableLiveData<LoginResponseModel?> {
        return loginResponse
    }

    fun observeRegister() : MutableLiveData<RegisterResponseModel?> {
        return registerResponse
    }

    fun observeYear() : MutableLiveData<YearResponseModel?> {
        return yearResponse
    }

    fun observeMessage() : MutableLiveData<String> {
        return errorMessage
    }
}