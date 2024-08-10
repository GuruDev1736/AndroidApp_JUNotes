package com.gurudev.junotes.Admin.Activities.Projects

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gurudev.junotes.R
import com.gurudev.junotes.databinding.ActivityAdminShowProjectsBinding

class Admin_ShowProjects : AppCompatActivity() {

    private lateinit var binding : ActivityAdminShowProjectsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminShowProjectsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.actionBar.toolbar.title = "Projects"
        binding.actionBar.toolbar.setNavigationOnClickListener {
            this@Admin_ShowProjects.onBackPressedDispatcher.onBackPressed()
        }

        binding.create.setOnClickListener{
            startActivity(Intent(this@Admin_ShowProjects,Admin_CreateAndUpdateProjectActivity::class.java))
        }

    }
}