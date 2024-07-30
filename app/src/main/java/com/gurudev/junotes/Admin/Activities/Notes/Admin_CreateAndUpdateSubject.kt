package com.gurudev.junotes.Admin.Activities.Notes

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gurudev.junotes.R
import com.gurudev.junotes.databinding.ActivityAdminCreateAndUpdateSubjectBinding

class Admin_CreateAndUpdateSubject : AppCompatActivity() {

    private lateinit var binding : ActivityAdminCreateAndUpdateSubjectBinding
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





    }
}