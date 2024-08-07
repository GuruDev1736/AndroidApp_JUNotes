package com.gurudev.junotes.Retrofit

import com.gurudev.junotes.RequestModel.ChangeEmailRequestModel
import com.gurudev.junotes.RequestModel.CreateNoteRequestModel
import com.gurudev.junotes.RequestModel.CreateSubjectRequestModel
import com.gurudev.junotes.RequestModel.CreateYearRequestModel
import com.gurudev.junotes.RequestModel.LoginRequestModel
import com.gurudev.junotes.RequestModel.RegisterRequestModel
import com.gurudev.junotes.RequestModel.SupportRequestModel
import com.gurudev.junotes.RequestModel.UpdateNoteRequestModel
import com.gurudev.junotes.RequestModel.changePasswordRequestModel
import com.gurudev.junotes.ResponseModel.Login.LoginResponseModel
import com.gurudev.junotes.ResponseModel.Notes.CreateNoteResponseModel
import com.gurudev.junotes.ResponseModel.Notes.CreateSubjectResponseModel
import com.gurudev.junotes.ResponseModel.Notes.CreateYearResponseModel
import com.gurudev.junotes.ResponseModel.Notes.DeleteNoteResponseModel
import com.gurudev.junotes.ResponseModel.Notes.DeleteYearResponseModel
import com.gurudev.junotes.ResponseModel.Notes.GetNotesResponseModel
import com.gurudev.junotes.ResponseModel.Notes.GetYearResponseModel
import com.gurudev.junotes.ResponseModel.Notes.getAllSubjectResponseModel
import com.gurudev.junotes.ResponseModel.Profile.ChangePasswordResponseModel
import com.gurudev.junotes.ResponseModel.Profile.GetSupportHistoryResponseModel
import com.gurudev.junotes.ResponseModel.Profile.SupportResponseModel
import com.gurudev.junotes.ResponseModel.Profile.changeEmailResponseModel
import com.gurudev.junotes.ResponseModel.Projects.GetProjectByIdResponseModel
import com.gurudev.junotes.ResponseModel.Projects.showAllProjectsResponseModel
import com.gurudev.junotes.ResponseModel.Register.RegisterResponseModel
import com.gurudev.junotes.ResponseModel.Register.YearResponseModel
import com.gurudev.junotes.ResponseModel.Tut.GetAllLanguageResponseModel
import com.gurudev.junotes.ResponseModel.Tut.GetAllTutResponseModel
import com.gurudev.junotes.ResponseModel.Tut.GetLangByIdResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @POST("auth/login")
    fun login(@Body loginRequest: LoginRequestModel) : Call<LoginResponseModel>

    @GET("year/")
    fun getAllYears() : Call<YearResponseModel>

    @POST("auth/register")
    fun register(@Body registerRequest: RegisterRequestModel) : Call<RegisterResponseModel>

    @GET("subject/{id}")
    fun getAllSubjects(@Header("Authorization") token : String, @Path("id") id : Int) : Call<getAllSubjectResponseModel>

    @GET("user/{id}")
    fun getUserById(@Header("Authorization") token : String, @Path("id") id : Int) : Call<RegisterResponseModel>

    @POST("auth/changePassword")
    fun changePassword(@Body model : changePasswordRequestModel) : Call<ChangePasswordResponseModel>

    @POST("auth/changeEmail")
    fun changeEmail(@Body model: ChangeEmailRequestModel):Call<changeEmailResponseModel>

    @POST("support/")
    fun getSupport(@Header("Authorization") token : String , @Body model : SupportRequestModel) : Call<SupportResponseModel>

    @GET("note/subject/{id}")
    fun getNotesBySubject(@Header("Authorization") token : String, @Path("id") id : Int) : Call<GetNotesResponseModel>

    @GET("support/user/{id}")
    fun getSupportHistory(@Header("Authorization") token : String, @Path("id") id : Int) : Call<GetSupportHistoryResponseModel>

    @GET("project/")
    fun getAllProjects(@Header("Authorization") token : String) : Call<showAllProjectsResponseModel>

    @GET("project/{id}")
    fun getProjectById(@Header("Authorization") token : String , @Path("id") id : Int) : Call<GetProjectByIdResponseModel>

    @GET("tut/lang/{id}")
    fun getAllTut(@Header("Authorization") token : String , @Path("id") id : Int) : Call<GetAllTutResponseModel>

    @GET("language/")
    fun getAllLang(@Header("Authorization") token : String) : Call<GetAllLanguageResponseModel>

    @GET("language/{id}")
    fun getLangById(@Header("Authorization") token : String , @Path("id") id : Int) : Call<GetLangByIdResponseModel>


    // Admin Services

    @DELETE("note/{id}")
    fun deleteNote(@Header("Authorization") token : String , @Path("id") id : Int) : Call<DeleteNoteResponseModel>

    @POST("year/")
    fun createYear(@Body model : CreateYearRequestModel) : Call<CreateYearResponseModel>

    @GET("year/")
    fun getAllYear() : Call<GetYearResponseModel>

    @DELETE("year/{id}")
    fun deleteYear(@Header("Authorization") token : String , @Path("id") id : Int) : Call<DeleteYearResponseModel>

    @PUT("year/{id}")
    fun updateYear(@Header("Authorization") token : String , @Path("id") id : Int , @Body model : CreateYearRequestModel) : Call<CreateYearResponseModel>

    @POST("subject/")
    fun createSubject(@Header("Authorization") token : String , @Body model : CreateSubjectRequestModel) : Call<CreateSubjectResponseModel>

    @PUT("subject/{id}")
    fun updateSubject(@Header("Authorization") token : String , @Body model : CreateSubjectRequestModel , @Path("id") id: Int) : Call<CreateSubjectResponseModel>

    @DELETE("subject/{id}")
    fun deleteSubject(@Header("Authorization") token : String , @Path("id") id : Int) : Call<DeleteYearResponseModel>

    @POST("note/")
    fun createNote(@Header("Authorization") token : String , @Body model : CreateNoteRequestModel) : Call<CreateNoteResponseModel>

    @PUT("note/{id}")
    fun updateNote(@Header("Authorization") token : String , @Body model : UpdateNoteRequestModel , @Path("id") id: Int) : Call<CreateNoteResponseModel>



}