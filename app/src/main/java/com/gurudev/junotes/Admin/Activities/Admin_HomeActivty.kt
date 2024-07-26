package com.gurudev.junotes.Admin.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.gurudev.junotes.User.Adapter.HomeScreenAdapter
import com.gurudev.junotes.Model.HomeScreenModel
import com.gurudev.junotes.R
import com.gurudev.junotes.databinding.ActivityAdminHomeActivtyBinding

class Admin_HomeActivty : AppCompatActivity() {

    private lateinit var binding : ActivityAdminHomeActivtyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminHomeActivtyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.recyclerView.layoutManager = GridLayoutManager(this@Admin_HomeActivty, 2)
        val buttonList = listOf(
            HomeScreenModel(R.drawable.notes,"Create Notes"),
            HomeScreenModel(R.drawable.projects,"Create Projects"),
            HomeScreenModel(R.drawable.code,"Create Tutorials"),
            HomeScreenModel(R.drawable.feedback,"Issue Tracking")
        )

        binding.recyclerView.adapter = HomeScreenAdapter(this@Admin_HomeActivty, buttonList)
    }
}