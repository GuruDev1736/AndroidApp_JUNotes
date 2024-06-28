package com.gurudev.junotes.Retrofit

import com.gurudev.junotes.RequestModel.LoginRequestModel
import com.gurudev.junotes.RequestModel.RegisterRequestModel
import com.gurudev.junotes.ResponseModel.Login.LoginResponseModel
import com.gurudev.junotes.ResponseModel.Register.RegisterResponseModel
import com.gurudev.junotes.ResponseModel.Register.YearResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("auth/login")
    fun login(@Body loginRequest: LoginRequestModel) : Call<LoginResponseModel>

    @GET("year/")
    fun getAllYears() : Call<YearResponseModel>

    @POST("auth/register")
    fun register(@Body registerRequest: RegisterRequestModel) : Call<RegisterResponseModel>


}