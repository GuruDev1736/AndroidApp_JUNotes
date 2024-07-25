package com.gurudev.junotes.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gurudev.junotes.Constants.SPref
import com.gurudev.junotes.ResponseModel.Notes.GetNotesResponseModel
import com.gurudev.junotes.ResponseModel.Profile.GetSupportHistoryResponseModel
import com.gurudev.junotes.ResponseModel.Profile.SupportResponseModel
import com.gurudev.junotes.Retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SupportViewModel : ViewModel() {

    private var SupportResponseLiveData  = MutableLiveData<GetSupportHistoryResponseModel?>()
    private var errorMessageLiveData = MutableLiveData<String>()

    fun support(token : String , userId : Int) {
        try {
            RetrofitInstance.api.getSupportHistory(token,userId).enqueue(object :
                Callback<GetSupportHistoryResponseModel> {
                override fun onResponse(
                    call: Call<GetSupportHistoryResponseModel>,
                    response: Response<GetSupportHistoryResponseModel>
                ) {
                    if (response.isSuccessful)
                    {
                        val data = response.body()
                        if (data!=null)
                        {
                            if (data.STS=="200")
                            {
                                SupportResponseLiveData.value = data
                            }
                            if (data.STS =="500")
                            {
                                errorMessageLiveData.value = data.MSG
                            }
                        }
                    }
                    else
                    {
                        errorMessageLiveData.value = "Something went wrong"
                    }
                }

                override fun onFailure(call: Call<GetSupportHistoryResponseModel>, t: Throwable) {
                    errorMessageLiveData.value = "Something went wrong"
                }

            })

        }catch (e : Exception)
        {
            e.printStackTrace()
        }

    }



    fun observeSupport() : MutableLiveData<GetSupportHistoryResponseModel?> {
        return SupportResponseLiveData
    }

    fun observeErrorMessage() : MutableLiveData<String> {
        return errorMessageLiveData
    }


}