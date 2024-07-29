package com.gurudev.junotes.Admin.Activities.Notes

import android.content.Intent
import android.os.Bundle
import android.widget.GridLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.gurudev.junotes.Admin.Adapter.ShowYearAdapter
import com.gurudev.junotes.Constants.Constant
import com.gurudev.junotes.Constants.CustomProgressDialog
import com.gurudev.junotes.R
import com.gurudev.junotes.ViewModel.NotesViewModel
import com.gurudev.junotes.databinding.ActivityAdminShowYearsBinding

class Admin_ShowYearsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAdminShowYearsBinding
    private  lateinit var viewModel : NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminShowYearsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val progress = CustomProgressDialog(this)
        progress.show()

        viewModel = ViewModelProvider(this)[NotesViewModel::class.java]

        binding.actionBar.toolbar.title = "Years"
        binding.actionBar.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.create.setOnClickListener{
            startActivity(Intent(this, Admin_CreateAndUpdateYear::class.java)
                .putExtra("code",0)
            )
        }

        binding.recyclerView.layoutManager = GridLayoutManager(this,2)

        viewModel.getAllYear()
        viewModel.observeGetAllYear().observe(this){
            binding.recyclerView.adapter = ShowYearAdapter(this,it!!.CONTENT)
            progress.dismiss()
        }

        viewModel.observeError().observe(this){
            Constant.error(this,it)
            progress.dismiss()
        }




    }
}