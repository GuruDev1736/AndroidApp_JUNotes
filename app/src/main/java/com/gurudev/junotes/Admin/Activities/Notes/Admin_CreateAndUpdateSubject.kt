package com.gurudev.junotes.Admin.Activities.Notes

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.gurudev.junotes.Constants.Constant
import com.gurudev.junotes.Constants.CustomProgressDialog
import com.gurudev.junotes.Constants.SPref
import com.gurudev.junotes.Model.YearData
import com.gurudev.junotes.R
import com.gurudev.junotes.RequestModel.CreateSubjectRequestModel
import com.gurudev.junotes.ViewModel.AuthViewModel
import com.gurudev.junotes.ViewModel.NotesViewModel
import com.gurudev.junotes.databinding.ActivityAdminCreateAndUpdateSubjectBinding

class Admin_CreateAndUpdateSubject : AppCompatActivity() {

    private lateinit var binding : ActivityAdminCreateAndUpdateSubjectBinding
    private var selectedYearId: Int? = null
    private lateinit var viewModel: AuthViewModel
    private lateinit var subjectViewModel : NotesViewModel
    private var yearIdForUpdate: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminCreateAndUpdateSubjectBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.actionBar.toolbar.title = "Create Subject"
        binding.actionBar.toolbar.setNavigationOnClickListener {
            finish()
        }

        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        subjectViewModel = ViewModelProvider(this)[NotesViewModel::class.java]



        val code = intent.getIntExtra("code",-1)
        val token = SPref.get(this@Admin_CreateAndUpdateSubject, SPref.token)


        if(code.equals(0))
        {
            binding.submit.setOnClickListener{
                val subjectName = binding.subjectName.text.toString()
                val subjectDescription = binding.subjectDescription.text.toString()
                val year = selectedYearId

                if(valid(subjectName,subjectDescription))
                {
                    val progress = CustomProgressDialog(this@Admin_CreateAndUpdateSubject)
                    progress.show()
                    val model = CreateSubjectRequestModel(subjectDescription,subjectName,year!!)
                    createSubject(token,model,progress)
                }
            }
        }

        if (code.equals(1))
        {
            binding.actionBar.toolbar.title = "Update Subject"
            binding.submit.text = "Update"
            val subjectName = intent.getStringExtra("subjectName")
            val subjectDescription = intent.getStringExtra("subjectDescription")
            val yearId = intent.getIntExtra("yearId",-1)
            yearIdForUpdate = yearId
            val subjectId = intent.getIntExtra("subjectId",-1)

            binding.subjectName.setText(subjectName)
            binding.subjectDescription.setText(subjectDescription)

            binding.submit.setOnClickListener{
                val subjectName = binding.subjectName.text.toString()
                val subjectDescription = binding.subjectDescription.text.toString()
                val year = selectedYearId

                if(valid(subjectName,subjectDescription))
                {
                    val progress = CustomProgressDialog(this@Admin_CreateAndUpdateSubject)
                    progress.show()
                    val model = CreateSubjectRequestModel(subjectDescription,subjectName,year!!)
                    updateSubject(token,model,progress,subjectId)
                }
            }
        }
    }



    private fun updateSubject(token: String, model: CreateSubjectRequestModel, progress: CustomProgressDialog, subjectId: Int) {

        subjectViewModel.observeUpdateSubject().removeObservers(this@Admin_CreateAndUpdateSubject)
        subjectViewModel.observeError().removeObservers(this@Admin_CreateAndUpdateSubject)

        subjectViewModel.observeUpdateSubject().observe(this@Admin_CreateAndUpdateSubject){data->
            Constant.success(this@Admin_CreateAndUpdateSubject,data!!.MSG)
            progress.dismiss()
            finish()
        }

        subjectViewModel.observeError().observe(this@Admin_CreateAndUpdateSubject){
            Constant.error(this@Admin_CreateAndUpdateSubject,it)
            progress.dismiss()
        }

        subjectViewModel.updateSubject(token,model,subjectId)


    }

    private fun createSubject(token: String, model: CreateSubjectRequestModel, progress: CustomProgressDialog) {

        subjectViewModel.observeCreateSubject().removeObservers(this@Admin_CreateAndUpdateSubject)
        subjectViewModel.observeError().removeObservers(this@Admin_CreateAndUpdateSubject)

        subjectViewModel.observeCreateSubject().observe(this@Admin_CreateAndUpdateSubject){data->
            Constant.success(this@Admin_CreateAndUpdateSubject,data!!.MSG)
            progress.dismiss()
            finish()
        }

        subjectViewModel.observeError().observe(this@Admin_CreateAndUpdateSubject){
            Constant.error(this@Admin_CreateAndUpdateSubject,it)
            progress.dismiss()
        }

        subjectViewModel.createSubject(token,model)
    }

    private fun valid(subjectName: String, subjectDescription: String): Boolean {

        if (subjectName.isEmpty()) {
            binding.subjectName.error = "Subject Name is required"
            binding.subjectName.requestFocus()
            return false
        }
        if (subjectDescription.isEmpty()) {
            binding.subjectDescription.error = "Subject Description is required"
            binding.subjectDescription.requestFocus()
            return false
        }
        if (selectedYearId==null)
        {
            Constant.error(this@Admin_CreateAndUpdateSubject,"Please select a year")
            return false
        }
        return true
    }


    override fun onStart() {
        super.onStart()

        val progress = CustomProgressDialog(this@Admin_CreateAndUpdateSubject)
        progress.show()

        viewModel.getYear()
        viewModel.observeYear().observe(this){
            progress.dismiss()
            val yearList =  it!!.CONTENT.map { data-> YearData(data.id,data.yearName) }

            val adapter = ArrayAdapter(this@Admin_CreateAndUpdateSubject,android.R.layout.simple_spinner_item,yearList.map { it.yearName })
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.year.adapter = adapter

            binding.year.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedYearData = yearList[position]
                    selectedYearId = selectedYearData.id
                    Log.d("Year",selectedYearId.toString())
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    Constant.error(this@Admin_CreateAndUpdateSubject,"Nothing is selected")
                }
            }
            setYearSpinnerSelection(yearList, yearIdForUpdate)
        }
    }


    private fun setYearSpinnerSelection(yearList: List<YearData>, yearId: Int) {
        val position = yearList.indexOfFirst { yearData -> yearData.id == yearId }
        if (position >= 0) {
            binding.year.setSelection(position)
        }
    }
}