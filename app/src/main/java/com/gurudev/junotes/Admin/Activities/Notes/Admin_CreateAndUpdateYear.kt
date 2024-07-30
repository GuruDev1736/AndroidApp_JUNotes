package com.gurudev.junotes.Admin.Activities.Notes

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.gurudev.junotes.Constants.Constant
import com.gurudev.junotes.Constants.CustomProgressDialog
import com.gurudev.junotes.R
import com.gurudev.junotes.RequestModel.CreateYearRequestModel
import com.gurudev.junotes.ViewModel.NotesViewModel
import com.gurudev.junotes.databinding.ActivityAdminCreateAndUpdateYearBinding

class Admin_CreateAndUpdateYear : AppCompatActivity() {

    private lateinit var binding : ActivityAdminCreateAndUpdateYearBinding
    private lateinit var viewModel : NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminCreateAndUpdateYearBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this)[NotesViewModel::class.java]

        binding.actionBar.toolbar.setNavigationOnClickListener {
            finish()
        }

        val code = intent.getIntExtra("code",-1)
        val yearName = intent.getStringExtra("yearName")
        val yearDescription = intent.getStringExtra("yearDescription")

        if (code.equals(0))
        {
            binding.actionBar.toolbar.title = "Create Year"
        }

        if (code.equals(1))
        {
            binding.actionBar.toolbar.title = "Update Year"
            binding.yearName.setText(yearName)
            binding.yearDescription.setText(yearDescription)
            binding.submit.text = "Update"
        }

        binding.submit.setOnClickListener{

            val yearName = binding.yearName.text.toString()
            val yearDescription = binding.yearDescription.text.toString()

            if (valid(yearName,yearDescription))
            {
                if (code.equals(0))
                {
                    createyear(yearName,yearDescription)
                }

                if (code.equals(1))
                {
                    UpdateYear(yearName,yearDescription)
                }
            }
        }
    }

    private fun UpdateYear(yearName: String, yearDescription: String) {



    }

    private fun createyear(yearName: String, yearDescription: String) {

        val progress = CustomProgressDialog(this)
        progress.show()

        val model = CreateYearRequestModel(yearDescription,yearName)

        viewModel.observeCreateYear().removeObservers(this)
        viewModel.observeError().removeObservers(this)

        viewModel.observeCreateYear().observe(this){
            Constant.success(this,it!!.MSG)
            this.onBackPressedDispatcher.onBackPressed()
            progress.dismiss()
        }

        viewModel.observeError().observe(this){
            Constant.error(this,it)
            progress.dismiss()
        }

        viewModel.createYear(model)
    }

    private fun valid(yearName: String, yearDescription: String): Boolean {

        if (yearName.isEmpty())
        {
            binding.yearName.error = "Please enter year name"
            return false
        }
        if (yearDescription.isEmpty())
        {
            binding.yearDescription.error = "Please enter year description"
            return false
        }
        return true
    }

}