package com.gurudev.junotes.Authenthication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.gurudev.junotes.Activities.Activity_Home
import com.gurudev.junotes.Constants.Constant
import com.gurudev.junotes.Constants.CustomProgressDialog
import com.gurudev.junotes.Constants.SPref
import com.gurudev.junotes.R
import com.gurudev.junotes.Retrofit.RetrofitInstance
import com.gurudev.junotes.ViewModel.AuthViewModel
import com.gurudev.junotes.databinding.ActivityLoginBinding

class Activity_Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel : AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val progress = CustomProgressDialog(this@Activity_Login)

        binding.email.setText(SPref.get(this@Activity_Login,SPref.email))
        binding.password.setText(SPref.get(this@Activity_Login,SPref.password))

        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        binding.loginBtn.setOnClickListener{
            val username = binding.email.text.toString()
            val password = binding.password.text.toString()

            if (valid())
            {
                progress.show()
                viewModel.login(username,password)
                SPref.set(this@Activity_Login,SPref.email,username)
                SPref.set(this@Activity_Login,SPref.password,password)
            }
        }

        binding.registerPageBtn.setOnClickListener{
            startActivity(Intent(this@Activity_Login,Register::class.java))
        }

        viewModel.observeLogin().observe(this) { loginResponse ->
            val intent = Intent(this@Activity_Login, Activity_Home::class.java)
            startActivity(intent)
            finish()
            SPref.set(this@Activity_Login,SPref.token,loginResponse!!.CONTENT.token)
            Constant.success(this@Activity_Login, loginResponse.MSG)
            progress.dismiss()
        }

        viewModel.observeMessage().observe(this){message->
            Constant.error(this@Activity_Login,message)
            progress.dismiss()
        }
    }

    private fun valid(): Boolean {

        if (binding.email.text.toString().isEmpty())
        {
            binding.email.error = "Email is required"
            binding.email.requestFocus()
            return false
        }
        if (binding.password.text.toString().isEmpty())
        {
            binding.password.error = "Password is required"
            binding.password.requestFocus()
            return false
        }
        return true
    }
}