package com.gurudev.junotes.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gurudev.junotes.Constants.SPref
import com.gurudev.junotes.Constants.SingleLiveEvent
import com.gurudev.junotes.RequestModel.CreateNoteRequestModel
import com.gurudev.junotes.RequestModel.CreateSubjectRequestModel
import com.gurudev.junotes.RequestModel.CreateYearRequestModel
import com.gurudev.junotes.RequestModel.UpdateNoteRequestModel
import com.gurudev.junotes.ResponseModel.Notes.AdminGetAllSubjectResponseModel
import com.gurudev.junotes.ResponseModel.Notes.CreateNoteResponseModel
import com.gurudev.junotes.ResponseModel.Notes.CreateSubjectResponseModel
import com.gurudev.junotes.ResponseModel.Notes.CreateYearResponseModel
import com.gurudev.junotes.ResponseModel.Notes.DeleteNoteResponseModel
import com.gurudev.junotes.ResponseModel.Notes.DeleteYearResponseModel
import com.gurudev.junotes.ResponseModel.Notes.GetNotesResponseModel
import com.gurudev.junotes.ResponseModel.Notes.GetYearResponseModel
import com.gurudev.junotes.ResponseModel.Notes.getAllSubjectResponseModel
import com.gurudev.junotes.Retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotesViewModel : ViewModel() {
    private val subjectResponse = MutableLiveData<getAllSubjectResponseModel?>()
    private val notesResponse = MutableLiveData<GetNotesResponseModel?>()
    private val deleteNoteResponse = MutableLiveData<DeleteNoteResponseModel?>()
    private val createYearResponse = SingleLiveEvent<CreateYearResponseModel?>()
    private val updateYearResponse = SingleLiveEvent<CreateYearResponseModel?>()
    private val getAllYearResponse = MutableLiveData<GetYearResponseModel?>()
    private val deleteYearResponse = SingleLiveEvent<DeleteYearResponseModel?>()
    private val createSubjectResponse = SingleLiveEvent<CreateSubjectResponseModel?>()
    private val updateSubjectResponse = SingleLiveEvent<CreateSubjectResponseModel?>()
    private val deleteSubjectResponse = SingleLiveEvent<DeleteYearResponseModel?>()
    private val createNoteResponse = MutableLiveData<CreateNoteResponseModel?>()
    private val updateNoteResponse = MutableLiveData<CreateNoteResponseModel?>()

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


    fun deleteNote(token : String , noteId : Int){
        try {
            RetrofitInstance.api.deleteNote(token,noteId).enqueue(object : Callback<DeleteNoteResponseModel>{
                override fun onResponse(
                    call: Call<DeleteNoteResponseModel>,
                    response: Response<DeleteNoteResponseModel>
                ) {
                    if (response.isSuccessful)
                    {
                        val data = response.body()
                        if (data!=null)
                        {
                            if (data.STS=="200")
                            {
                                deleteNoteResponse.value = data
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

                override fun onFailure(call: Call<DeleteNoteResponseModel>, t: Throwable) {
                    error.value = "Something went wrong"
                }
            })

        }catch (e : Exception)
        {
            e.printStackTrace()
        }
    }


    fun createYear(model : CreateYearRequestModel){
        try {
            RetrofitInstance.api.createYear(model).enqueue(object : Callback<CreateYearResponseModel>{
                override fun onResponse(
                    call: Call<CreateYearResponseModel>,
                    response: Response<CreateYearResponseModel>
                ) {
                    if (response.isSuccessful)
                    {
                        val data = response.body()
                        if (data!=null)
                        {
                            if (data.STS=="200")
                            {
                                createYearResponse.value = data
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

                override fun onFailure(call: Call<CreateYearResponseModel>, t: Throwable) {
                    error.value = "Something went wrong"
                }

            })

        }catch (e : Exception)
        {
            e.printStackTrace()
        }
    }


    fun updateYear(token: String,model : CreateYearRequestModel , id : Int){
        try {
            RetrofitInstance.api.updateYear(token,id,model).enqueue(object : Callback<CreateYearResponseModel>{
                override fun onResponse(
                    call: Call<CreateYearResponseModel>,
                    response: Response<CreateYearResponseModel>
                ) {
                    if (response.isSuccessful)
                    {
                        val data = response.body()
                        if (data!=null)
                        {
                            if (data.STS=="200")
                            {
                                updateYearResponse.value = data
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

                override fun onFailure(call: Call<CreateYearResponseModel>, t: Throwable) {
                    error.value = "Something went wrong"
                }

            })

        }catch (e : Exception)
        {
            e.printStackTrace()
        }
    }


    fun getAllYear(){
        try {
            RetrofitInstance.api.getAllYear().enqueue(object : Callback<GetYearResponseModel>{
                override fun onResponse(
                    call: Call<GetYearResponseModel>,
                    response: Response<GetYearResponseModel>
                ) {
                    if (response.isSuccessful)
                    {
                        val data = response.body()
                        if (data!=null)
                        {
                            if (data.STS=="200")
                            {
                                getAllYearResponse.value = data
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

                override fun onFailure(call: Call<GetYearResponseModel>, t: Throwable) {
                    error.value = "Something went wrong"
                }
            })

        }catch (e : Exception)
        {
            e.printStackTrace()
        }
    }

    fun deleteYear(token : String , yearId : Int){
        try {
            RetrofitInstance.api.deleteYear(token,yearId).enqueue(object : Callback<DeleteYearResponseModel>{
                override fun onResponse(
                    call: Call<DeleteYearResponseModel>,
                    response: Response<DeleteYearResponseModel>
                ) {
                    if (response.isSuccessful)
                    {
                        val data = response.body()
                        if (data!=null)
                        {
                            if (data.STS=="200")
                            {
                                deleteYearResponse.value = data
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

                override fun onFailure(call: Call<DeleteYearResponseModel>, t: Throwable) {
                    error.value = "Something went wrong"
                }
            })

        }catch (e : Exception)
        {
            e.printStackTrace()
        }
    }

    fun createSubject(token: String,model : CreateSubjectRequestModel){
        try {
            RetrofitInstance.api.createSubject(token,model).enqueue(object : Callback<CreateSubjectResponseModel>{
                override fun onResponse(
                    call: Call<CreateSubjectResponseModel>,
                    response: Response<CreateSubjectResponseModel>
                ) {
                    if (response.isSuccessful)
                    {
                        val data = response.body()
                        if (data!=null)
                        {
                            if (data.STS=="200")
                            {
                                createSubjectResponse.value = data
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

                override fun onFailure(call: Call<CreateSubjectResponseModel>, t: Throwable) {
                    error.value = "Something went wrong"
                }

            })

        }catch (e : Exception)
        {
            e.printStackTrace()
        }
    }


    fun updateSubject(token: String,model : CreateSubjectRequestModel , subjectId: Int){
        try {
            RetrofitInstance.api.updateSubject(token,model,subjectId).enqueue(object : Callback<CreateSubjectResponseModel>{
                override fun onResponse(
                    call: Call<CreateSubjectResponseModel>,
                    response: Response<CreateSubjectResponseModel>
                ) {
                    if (response.isSuccessful)
                    {
                        val data = response.body()
                        if (data!=null)
                        {
                            if (data.STS=="200")
                            {
                                updateSubjectResponse.value = data
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

                override fun onFailure(call: Call<CreateSubjectResponseModel>, t: Throwable) {
                    error.value = "Something went wrong"
                }

            })

        }catch (e : Exception)
        {
            e.printStackTrace()
        }
    }


    fun deleteSubject(token : String , subjectId : Int){
        try {
            RetrofitInstance.api.deleteSubject(token,subjectId).enqueue(object : Callback<DeleteYearResponseModel>{
                override fun onResponse(
                    call: Call<DeleteYearResponseModel>,
                    response: Response<DeleteYearResponseModel>
                ) {
                    if (response.isSuccessful)
                    {
                        val data = response.body()
                        if (data!=null)
                        {
                            if (data.STS=="200")
                            {
                                deleteSubjectResponse.value = data
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

                override fun onFailure(call: Call<DeleteYearResponseModel>, t: Throwable) {
                    error.value = "Something went wrong"
                }
            })

        }catch (e : Exception)
        {
            e.printStackTrace()
        }
    }


    fun createNote(token : String , model : CreateNoteRequestModel){
        try {
            RetrofitInstance.api.createNote(token,model).enqueue(object : Callback<CreateNoteResponseModel>{
                override fun onResponse(
                    call: Call<CreateNoteResponseModel>,
                    response: Response<CreateNoteResponseModel>
                ) {
                    if (response.isSuccessful)
                    {
                        val data = response.body()
                        if (data!=null)
                        {
                            if (data.STS=="200")
                            {
                                createNoteResponse.value = data
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

                override fun onFailure(call: Call<CreateNoteResponseModel>, t: Throwable) {
                    error.value = "Something went wrong"
                }

            })

        }catch (e : Exception)
        {
            e.printStackTrace()
        }
    }


    fun updateNote(token : String , model : UpdateNoteRequestModel , noteId : Int){
        try {
            RetrofitInstance.api.updateNote(token,model,noteId).enqueue(object : Callback<CreateNoteResponseModel>{
                override fun onResponse(
                    call: Call<CreateNoteResponseModel>,
                    response: Response<CreateNoteResponseModel>
                ) {
                    if (response.isSuccessful)
                    {
                        val data = response.body()
                        if (data!=null)
                        {
                            if (data.STS=="200")
                            {
                                updateNoteResponse.value = data
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

                override fun onFailure(call: Call<CreateNoteResponseModel>, t: Throwable) {
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

    fun observeDeleteNote() : MutableLiveData<DeleteNoteResponseModel?>{
        return deleteNoteResponse
    }

    fun observeCreateYear() : SingleLiveEvent<CreateYearResponseModel?>{
        return createYearResponse
    }

    fun observeGetAllYear() : MutableLiveData<GetYearResponseModel?>{
        return getAllYearResponse
    }

    fun observeDeleteYear() : SingleLiveEvent<DeleteYearResponseModel?> {
        return deleteYearResponse
    }

    fun observeUpdateYear() : SingleLiveEvent<CreateYearResponseModel?>{
        return updateYearResponse
    }

    fun observeCreateSubject() : SingleLiveEvent<CreateSubjectResponseModel?>{
        return createSubjectResponse
    }

    fun observeUpdateSubject() : SingleLiveEvent<CreateSubjectResponseModel?>{
        return updateSubjectResponse
    }

    fun observeDeleteSubject() : SingleLiveEvent<DeleteYearResponseModel?>{
        return deleteSubjectResponse
    }

    fun observeCreateNote() : MutableLiveData<CreateNoteResponseModel?>{
        return createNoteResponse
    }

    fun observeUpdateNote() : MutableLiveData<CreateNoteResponseModel?>{
        return updateNoteResponse
    }

    fun observeError() : MutableLiveData<String>{
        return error
    }
}