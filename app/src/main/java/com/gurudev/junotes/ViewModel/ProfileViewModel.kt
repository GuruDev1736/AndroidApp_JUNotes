package com.gurudev.junotes.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gurudev.junotes.Constants.SPref
import com.gurudev.junotes.Constants.SingleLiveEvent
import com.gurudev.junotes.RequestModel.ChangeEmailRequestModel
import com.gurudev.junotes.RequestModel.SupportRequestModel
import com.gurudev.junotes.RequestModel.changePasswordRequestModel
import com.gurudev.junotes.ResponseModel.Notes.getAllSubjectResponseModel
import com.gurudev.junotes.ResponseModel.Profile.ChangePasswordResponseModel
import com.gurudev.junotes.ResponseModel.Profile.SupportResponseModel
import com.gurudev.junotes.ResponseModel.Profile.changeEmailResponseModel
import com.gurudev.junotes.ResponseModel.Register.RegisterResponseModel
import com.gurudev.junotes.Retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {

    private val userResponse = SingleLiveEvent<RegisterResponseModel?>()
    private val changePasswordLiveData = SingleLiveEvent<ChangePasswordResponseModel?>()
    private val changeEmailLiveData = SingleLiveEvent<changeEmailResponseModel?>()
    private val supportLiveData = SingleLiveEvent<SupportResponseModel?>()
    private val errorMessage = SingleLiveEvent<String>()

    fun getUserById(token: String, id: Int) {
        try {
            RetrofitInstance.api.getUserById(token, id).enqueue(object : Callback<RegisterResponseModel> {
                override fun onResponse(call: Call<RegisterResponseModel>, response: Response<RegisterResponseModel>) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data != null) {
                            if (data.STS == "200") {
                                userResponse.value = data
                            }
                            if (data.STS == "500") {
                                errorMessage.value = data.MSG
                            }
                        }
                    } else {
                        errorMessage.value = "Something went wrong"
                    }
                }

                override fun onFailure(call: Call<RegisterResponseModel>, t: Throwable) {
                    errorMessage.value = "Something went wrong"
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun changePassword(lastPassword: String, newPassword: String, userId: String) {
        try {
            val model = changePasswordRequestModel(lastPassword, newPassword, userId)
            RetrofitInstance.api.changePassword(model).enqueue(object : Callback<ChangePasswordResponseModel> {
                override fun onResponse(call: Call<ChangePasswordResponseModel>, response: Response<ChangePasswordResponseModel>) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data != null) {
                            if (data.STS == "200") {
                                changePasswordLiveData.value = data
                            }
                            if (data.STS == "500") {
                                errorMessage.value = data.MSG
                            }
                        }
                    } else {
                        errorMessage.value = "Something went wrong"
                    }
                }

                override fun onFailure(call: Call<ChangePasswordResponseModel>, t: Throwable) {
                    errorMessage.value = "Something went wrong"
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    fun changeEmail(eMail: String, userId: String) {
        try {
            val model = ChangeEmailRequestModel(eMail, userId)
            RetrofitInstance.api.changeEmail(model).enqueue(object : Callback<changeEmailResponseModel> {
                override fun onResponse(call: Call<changeEmailResponseModel>, response: Response<changeEmailResponseModel>) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data != null) {
                            if (data.STS == "200") {
                                changeEmailLiveData.value = data
                            }
                            if (data.STS == "500") {
                                errorMessage.value = data.MSG
                            }
                        }
                    } else {
                        errorMessage.value = "Something went wrong"
                    }
                }

                override fun onFailure(call: Call<changeEmailResponseModel>, t: Throwable) {
                    errorMessage.value = "Something went wrong"
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun support(token: String, model: SupportRequestModel){
        try {
            RetrofitInstance.api.getSupport(token,model).enqueue(object : Callback<SupportResponseModel> {
                override fun onResponse(call: Call<SupportResponseModel>, response: Response<SupportResponseModel>) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data != null) {
                            if (data.STS == "200") {
                                supportLiveData.value = data
                            }
                            if (data.STS == "500") {
                                errorMessage.value = data.MSG
                            }
                        }
                    } else {
                        errorMessage.value = "Something went wrong"
                    }
                }

                override fun onFailure(call: Call<SupportResponseModel>, t: Throwable) {
                    errorMessage.value = "Something went wrong"
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun observeSupport(): SingleLiveEvent<SupportResponseModel?> {
        return supportLiveData
    }

    fun observeEmail(): SingleLiveEvent<changeEmailResponseModel?> {
        return changeEmailLiveData
    }
    fun observeUser(): SingleLiveEvent<RegisterResponseModel?> {
        return userResponse
    }

    fun observePassword(): SingleLiveEvent<ChangePasswordResponseModel?> {
        return changePasswordLiveData
    }

    fun observeErrorMessage(): SingleLiveEvent<String> {
        return errorMessage
    }
}
