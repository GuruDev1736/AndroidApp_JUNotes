package com.gurudev.junotes.Admin.Activities.Projects

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gurudev.junotes.R
import com.gurudev.junotes.databinding.ActivityAdminCreateAndUpdateProjectBinding

class Admin_CreateAndUpdateProjectActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAdminCreateAndUpdateProjectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminCreateAndUpdateProjectBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.actionBar.toolbar.title = "Create Project"
        binding.actionBar.toolbar.setNavigationOnClickListener {
            this@Admin_CreateAndUpdateProjectActivity.onBackPressedDispatcher.onBackPressed()
        }



    }
}