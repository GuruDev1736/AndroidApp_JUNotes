package com.gurudev.junotes.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gurudev.junotes.Constants.SingleLiveEvent
import com.gurudev.junotes.ResponseModel.Projects.GetProjectByIdResponseModel
import com.gurudev.junotes.ResponseModel.Projects.showAllProjectsResponseModel
import com.gurudev.junotes.ResponseModel.Register.RegisterResponseModel
import com.gurudev.junotes.Retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProjectViewModel : ViewModel() {

     private  val showProjectLiveData  = MutableLiveData<showAllProjectsResponseModel?>()
     private  val showProjectByIdLiveData  = MutableLiveData<GetProjectByIdResponseModel?>()
     private  val errorMessage =  MutableLiveData<String>()

     fun showAllProjects(token : String) {
          try {
               RetrofitInstance.api.getAllProjects(token).enqueue(object :
                    Callback<showAllProjectsResponseModel> {
                    override fun onResponse(call: Call<showAllProjectsResponseModel>, response: Response<showAllProjectsResponseModel>) {
                         if (response.isSuccessful) {
                              val data = response.body()
                              if (data != null) {
                                   if (data.STS == "200") {
                                        showProjectLiveData.value = data
                                   }
                                   if (data.STS == "500") {
                                        errorMessage.value = data.MSG
                                   }
                              }
                         } else {
                              errorMessage.value = "Something went wrong"
                         }
                    }

                    override fun onFailure(call: Call<showAllProjectsResponseModel>, t: Throwable) {
                         errorMessage.value = "Something went wrong"
                    }
               })
          } catch (e: Exception) {
               e.printStackTrace()
          }
     }


     fun getProjectById(token : String , id : Int) {
          try {
               RetrofitInstance.api.getProjectById(token,id).enqueue(object :
                    Callback<GetProjectByIdResponseModel> {
                    override fun onResponse(call: Call<GetProjectByIdResponseModel>, response: Response<GetProjectByIdResponseModel>) {
                         if (response.isSuccessful) {
                              val data = response.body()
                              if (data != null) {
                                   if (data.STS == "200") {
                                        showProjectByIdLiveData.value = data
                                   }
                                   if (data.STS == "500") {
                                        errorMessage.value = data.MSG
                                   }
                              }
                         } else {
                              errorMessage.value = "Something went wrong"
                         }
                    }

                    override fun onFailure(call: Call<GetProjectByIdResponseModel>, t: Throwable) {
                         errorMessage.value = "Something went wrong"
                    }
               })
          } catch (e: Exception) {
               e.printStackTrace()
          }
     }





     fun observeProject(): MutableLiveData<showAllProjectsResponseModel?> {
          return showProjectLiveData
     }

     fun observeProjectById(): MutableLiveData<GetProjectByIdResponseModel?> {
          return showProjectByIdLiveData
     }


     fun observeErrorMessage(): MutableLiveData<String> {
          return errorMessage
     }
}