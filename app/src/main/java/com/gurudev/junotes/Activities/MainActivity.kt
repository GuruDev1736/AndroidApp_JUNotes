package com.gurudev.junotes.Activities

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.provider.SyncStateContract.Constants
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gurudev.junotes.Constants.Constant
import com.gurudev.junotes.R
import com.gurudev.junotes.databinding.ActivityMainBinding
import com.permissionx.guolindev.PermissionX

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Handler().postDelayed(object : Runnable{
            override fun run() {

                PermissionX.init(this@MainActivity)
                    .permissions(Manifest.permission.POST_NOTIFICATIONS)
                    .request { allGranted, grantedList, deniedList ->
                        if (allGranted) {
                            startActivity(Intent(this@MainActivity,Activity_Home::class.java))
                            finish()
                        } else {
                           Constant.success(this@MainActivity,"Permission Denied")
                        }
                    }
            }

        },2000)


    }
}