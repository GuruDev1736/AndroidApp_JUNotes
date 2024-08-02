package com.gurudev.junotes.Admin.Activities.Notes

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gurudev.junotes.Admin.Adapter.ShowNotesAdapter
import com.gurudev.junotes.Constants.Constant
import com.gurudev.junotes.Constants.CustomProgressDialog
import com.gurudev.junotes.Constants.SPref
import com.gurudev.junotes.R
import com.gurudev.junotes.ViewModel.NotesViewModel
import com.gurudev.junotes.databinding.ActivityAdminGetNotesBinding

class Admin_ShowNotesActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAdminGetNotesBinding
    private lateinit var viewModel : NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminGetNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val progress = CustomProgressDialog(this)
        progress.show()

        binding.actionBar.toolbar.title = "Notes"
        binding.actionBar.toolbar.setNavigationOnClickListener {
            finish()
        }

        viewModel = ViewModelProvider(this)[NotesViewModel::class.java]

        val token = SPref.get(this,SPref.token)
        val subjectId = intent.getIntExtra("subjectId",0)
        viewModel.getNotes(token,subjectId)

        binding.recyclerView.layoutManager = LinearLayoutManager(this@Admin_ShowNotesActivity)


        viewModel.observeNotes().observe(this){
            binding.recyclerView.adapter = ShowNotesAdapter(this,it!!.CONTENT)
            progress.dismiss()
        }

        viewModel.observeError().observe(this){
            progress.dismiss()
            Constant.error(this,it)
        }

        binding.create.setOnClickListener{
            startActivity(
                Intent(this@Admin_ShowNotesActivity, Admin_CreateAndUpdateNoteActivity::class.java)
                .putExtra("code",0)
                .putExtra("subjectId",subjectId)
            )
        }

    }
}