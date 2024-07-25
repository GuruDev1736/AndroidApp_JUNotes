package com.gurudev.junotes.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gurudev.junotes.ResponseModel.Tut.GetAllLanguageResponseModel
import com.gurudev.junotes.ResponseModel.Tut.GetAllTutResponseModel
import com.gurudev.junotes.ResponseModel.Tut.GetLangByIdResponseModel
import com.gurudev.junotes.Retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TutorialViewModel : ViewModel() {

    private val languageLiveData = MutableLiveData<GetAllLanguageResponseModel?>()
    private val tutorialLiveData = MutableLiveData<GetAllTutResponseModel?>()
    private val GetLangById = MutableLiveData<GetLangByIdResponseModel?>()
    private val errorMessage = MutableLiveData<String>()


    fun getAllLang(token : String){
        RetrofitInstance.api.getAllLang(token).enqueue(object : Callback<GetAllLanguageResponseModel>{
            override fun onResponse(
                call: Call<GetAllLanguageResponseModel>,
                response: Response<GetAllLanguageResponseModel>
            ) {
                if (response.isSuccessful)
                {
                    val data = response.body()
                    if (data!=null)
                    {
                        if (data.STS=="200")
                        {
                            languageLiveData.postValue(data)
                        }
                        else
                        {
                            errorMessage.postValue(data.MSG)
                        }
                    }
                }
                else
                {
                    errorMessage.postValue("Something went wrong")
                }
            }

            override fun onFailure(call: Call<GetAllLanguageResponseModel>, t: Throwable) {
                errorMessage.postValue("Something went wrong : ${t.message}")
            }

        })
    }


    fun getAllTut(token : String , id : Int){
        RetrofitInstance.api.getAllTut(token,id).enqueue(object : Callback<GetAllTutResponseModel>{
            override fun onResponse(
                call: Call<GetAllTutResponseModel>,
                response: Response<GetAllTutResponseModel>
            ) {
                if (response.isSuccessful)
                {
                    val data = response.body()
                    if (data!=null)
                    {
                        if (data.STS=="200")
                        {
                            tutorialLiveData.postValue(data)
                        }
                        else
                        {
                            errorMessage.postValue(data.MSG)
                        }
                    }
                }
                else
                {
                    errorMessage.postValue("Something went wrong")
                }
            }

            override fun onFailure(call: Call<GetAllTutResponseModel>, t: Throwable) {
                errorMessage.postValue("Something went wrong : ${t.message}")
            }

        })
    }

    fun getLangById(token : String , id : Int){
        RetrofitInstance.api.getLangById(token,id).enqueue(object : Callback<GetLangByIdResponseModel>{
            override fun onResponse(
                call: Call<GetLangByIdResponseModel>,
                response: Response<GetLangByIdResponseModel>
            ) {
                if (response.isSuccessful)
                {
                    val data = response.body()
                    if (data!=null)
                    {
                        if (data.STS=="200")
                        {
                            GetLangById.postValue(data)
                        }
                        else
                        {
                            errorMessage.postValue(data.MSG)
                        }
                    }
                }
                else
                {
                    errorMessage.postValue("Something went wrong")
                }
            }

            override fun onFailure(call: Call<GetLangByIdResponseModel>, t: Throwable) {
                errorMessage.postValue("Something went wrong : ${t.message}")
            }

        })
    }


    fun observeLanguage() : MutableLiveData<GetAllLanguageResponseModel?> {
        return languageLiveData
    }

    fun observeTutorial() : MutableLiveData<GetAllTutResponseModel?> {
        return tutorialLiveData
    }

    fun observeGetLangById() : MutableLiveData<GetLangByIdResponseModel?> {
        return GetLangById
    }

    fun observeErrorMessage() : MutableLiveData<String> {
        return errorMessage
    }
}