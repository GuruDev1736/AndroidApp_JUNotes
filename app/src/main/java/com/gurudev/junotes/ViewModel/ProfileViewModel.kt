package com.gurudev.junotes.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gurudev.junotes.Constants.SPref
import com.gurudev.junotes.ResponseModel.Notes.getAllSubjectResponseModel
import com.gurudev.junotes.ResponseModel.Register.RegisterResponseModel
import com.gurudev.junotes.Retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {

    private val UserResponse  = MutableLiveData<RegisterResponseModel?>()
    private val errorMessage = MutableLiveData<String>()

    fun getUserById(token : String , id : Int){
        try {
            RetrofitInstance.api.getUserById(token,id).enqueue(object :
                Callback<RegisterResponseModel> {
                override fun onResponse(
                    call: Call<RegisterResponseModel>,
                    response: Response<RegisterResponseModel>
                ) {
                    if (response.isSuccessful)
                    {
                        val data = response.body()
                        if (data!=null)
                        {
                            if (data.STS=="200")
                            {
                                UserResponse.value = data
                            }
                            if (data.STS =="500")
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
                    errorMessage.value = "Something went wrong"
                }

            })
        }
        catch (e : Exception)
        {
            e.printStackTrace()
        }
    }


    fun observeUser() : MutableLiveData<RegisterResponseModel?>{
        return UserResponse
    }

}