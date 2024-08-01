package com.gurudev.junotes.Authenthication

import android.content.Intent
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
import com.gurudev.junotes.Constants.Validation
import com.gurudev.junotes.Model.YearData
import com.gurudev.junotes.R
import com.gurudev.junotes.ViewModel.AuthViewModel
import com.gurudev.junotes.databinding.ActivityLoginBinding
import com.gurudev.junotes.databinding.ActivityRegisterBinding

class Register : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: AuthViewModel
    private var selectedYearId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val progress = CustomProgressDialog(this@Register)

        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        binding.loginBtn.setOnClickListener{
            this@Register.onBackPressedDispatcher.onBackPressed()
        }

        binding.registerPageBtn.setOnClickListener{

            if (Valid())
            {
                progress.show()

                val fullName = binding.fullName.text.toString()
                val phoneNo = binding.phoneNo.text.toString()
                val email = binding.email.text.toString()
                val password = binding.password.text.toString()
                val year = selectedYearId
                viewModel.register(fullName,phoneNo,email,password,year!!)
            }

        }

        viewModel.observeRegister().observe(this){data->
            progress.dismiss()
            Constant.success(this@Register, data!!.MSG)
            this@Register.onBackPressedDispatcher.onBackPressed()
        }

        viewModel.observeMessage().observe(this){
            progress.dismiss()
            Constant.error(this@Register,it)
        }
    }

    private fun Valid(): Boolean {

        if (binding.fullName.text.toString().isEmpty())
        {
            binding.fullName.error = "Full Name is required"
            binding.fullName.requestFocus()
            return false
        }
        if (binding.phoneNo.text.toString().isEmpty())
        {
            binding.phoneNo.error = "Username is required"
            binding.phoneNo.requestFocus()
            return false
        }
        if (Validation.isValidPhoneNumber(binding.phoneNo.text.toString()))
        {
            binding.phoneNo.error = "Invalid Phone Number"
            binding.phoneNo.requestFocus()
            return false
        }
        if (binding.email.text.toString().isEmpty()) {
            binding.email.error = "Email is required"
            binding.email.requestFocus()
            return false
        }
        if (binding.password.text.toString().isEmpty()) {
            binding.password.error = "Password is required"
            binding.password.requestFocus()
            return false
        }

        if (binding.password.text.toString().length < 8)
        {
            binding.password.error = "Password must be at least 8 characters"
            binding.password.requestFocus()
            return false
        }

        return true

    }

    override fun onStart() {
        super.onStart()

        val progress = CustomProgressDialog(this@Register)
        progress.show()

        viewModel.getYear()
        viewModel.observeYear().observe(this){
            progress.dismiss()
            val yearList =  it!!.CONTENT.map { data-> YearData(data.id,data.yearName)}

            val adapter = ArrayAdapter(this@Register,android.R.layout.simple_spinner_item,yearList.map { it.yearName })
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
                    Constant.error(this@Register,"Nothing is selected")
                }
            }

        }
    }
}