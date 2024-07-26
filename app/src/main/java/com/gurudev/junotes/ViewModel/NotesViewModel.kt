package com.gurudev.junotes.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gurudev.junotes.Constants.SPref
import com.gurudev.junotes.ResponseModel.Notes.AdminGetAllSubjectResponseModel
import com.gurudev.junotes.ResponseModel.Notes.GetNotesResponseModel
import com.gurudev.junotes.ResponseModel.Notes.getAllSubjectResponseModel
import com.gurudev.junotes.Retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotesViewModel : ViewModel() {
    private val subjectResponse = MutableLiveData<getAllSubjectResponseModel?>()
    private val notesResponse = MutableLiveData<GetNotesResponseModel?>()
    private val getAllSubjectsResponse = MutableLiveData<AdminGetAllSubjectResponseModel?>()
    private val error = MutableLiveData<String>()

    fun getSubjects(token : String , yearId : Int){
        try {
            RetrofitInstance.api.getAllSubjects(token,yearId).enqueue(object : Callback<getAllSubjectResponseModel>{
                override fun onResponse(
                    call: Call<getAllSubjectResponseModel>,
                    response: Response<getAllSubjectResponseModel>
                ) {
                    if (response.isSuccessful)
                    {
                        val data = response.body()
                        if (data!=null)
                        {
                            if (data.STS=="200")
                            {
                                subjectResponse.value = data
                            }
                            if (data.STS =="500")
                            {
                                error.value = data.MSG
                            }
                        }
                    }
                    else
                    {
                        error.value = "Something went wrong"
                    }
                }

                override fun onFailure(call: Call<getAllSubjectResponseModel>, t: Throwable) {
                    error.value = "Something went wrong"
                }

            })

        }catch (e : Exception)
        {
            e.printStackTrace()
        }
    }


    fun getNotes(token : String , subjectId : Int){
        try {
            RetrofitInstance.api.getNotesBySubject(token,subjectId).enqueue(object : Callback<GetNotesResponseModel>{
                override fun onResponse(
                    call: Call<GetNotesResponseModel>,
                    response: Response<GetNotesResponseModel>
                ) {
                    if (response.isSuccessful)
                    {
                        val data = response.body()
                        if (data!=null)
                        {
                            if (data.STS=="200")
                            {
                                notesResponse.value = data
                            }
                            if (data.STS =="500")
                            {
                                error.value = data.MSG
                            }
                        }
                    }
                    else
                    {
                        error.value = "Something went wrong"
                    }
                }

                override fun onFailure(call: Call<GetNotesResponseModel>, t: Throwable) {
                    error.value = "Something went wrong"
                }

            })

        }catch (e : Exception)
        {
            e.printStackTrace()
        }
    }

    fun getAllSubjects(token : String ){
        try {
            RetrofitInstance.api.AdmingetAllSubjects(token).enqueue(object : Callback<AdminGetAllSubjectResponseModel>{
                override fun onResponse(
                    call: Call<AdminGetAllSubjectResponseModel>,
                    response: Response<AdminGetAllSubjectResponseModel>
                ) {
                    if (response.isSuccessful)
                    {
                        val data = response.body()
                        if (data!=null)
                        {
                            if (data.STS=="200")
                            {
                                getAllSubjectsResponse.value = data
                            }
                            if (data.STS =="500")
                            {
                                error.value = data.MSG
                            }
                        }
                    }
                    else
                    {
                        error.value = "Something went wrong"
                    }
                }

                override fun onFailure(call: Call<AdminGetAllSubjectResponseModel>, t: Throwable) {
                    error.value = "Something went wrong"
                }

            })

        }catch (e : Exception)
        {
            e.printStackTrace()
        }
    }



    fun observeSubject() : MutableLiveData<getAllSubjectResponseModel?>{
        return subjectResponse
    }

    fun observeNotes() : MutableLiveData<GetNotesResponseModel?>{
        return notesResponse
    }

    fun observeGetAllSubjects() : MutableLiveData<AdminGetAllSubjectResponseModel?>{
        return getAllSubjectsResponse
    }

    fun observeError() : MutableLiveData<String>{
        return error
    }
}