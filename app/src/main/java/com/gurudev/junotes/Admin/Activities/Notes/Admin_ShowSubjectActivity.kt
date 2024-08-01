package com.gurudev.junotes.Admin.Activities.Notes

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.gurudev.junotes.Admin.Adapter.ShowSubjectAdapter
import com.gurudev.junotes.Constants.CustomProgressDialog
import com.gurudev.junotes.Constants.SPref
import com.gurudev.junotes.R
import com.gurudev.junotes.ViewModel.NotesViewModel
import com.gurudev.junotes.databinding.ActivityShowSubjectBinding

class Admin_ShowSubjectActivity : AppCompatActivity() {
    private lateinit var binding : ActivityShowSubjectBinding
    private lateinit var viewModel : NotesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowSubjectBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.actionBar.toolbar.title = "Subjects"
        binding.actionBar.toolbar.setNavigationOnClickListener {
            finish()
        }

        val progress  = CustomProgressDialog(this@Admin_ShowSubjectActivity)
        progress.show()
        viewModel = ViewModelProvider(this)[NotesViewModel::class.java]

        val token = SPref.get(this@Admin_ShowSubjectActivity,SPref.token)
        val yearId = intent.getIntExtra("yearId",0)
        viewModel.getSubjects(token,yearId)

        binding.recyclerView.layoutManager = GridLayoutManager(this@Admin_ShowSubjectActivity,2)

        viewModel.observeSubject().observe(this@Admin_ShowSubjectActivity){
            progress.dismiss()
            binding.recyclerView.adapter = ShowSubjectAdapter(this@Admin_ShowSubjectActivity,it!!.CONTENT)
        }

        binding.create.setOnClickListener{
            startActivity(Intent(this@Admin_ShowSubjectActivity, Admin_CreateAndUpdateSubject::class.java)
                .putExtra("code",0)
            )
        }

    }
}