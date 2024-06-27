package com.gurudev.junotes.Retrofit

import com.gurudev.junotes.RequestModel.LoginRequestModel
import com.gurudev.junotes.ResponseModel.Login.LoginResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("auth/login")
    fun login(@Body loginRequest: LoginRequestModel) : Call<LoginResponseModel>

}