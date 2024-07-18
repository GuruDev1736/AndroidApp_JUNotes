package com.gurudev.junotes.Activities.Notes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gurudev.junotes.Adapter.NotesAdapter
import com.gurudev.junotes.Constants.Constant
import com.gurudev.junotes.Constants.CustomProgressDialog
import com.gurudev.junotes.Constants.SPref
import com.gurudev.junotes.R
import com.gurudev.junotes.ViewModel.NotesViewModel
import com.gurudev.junotes.databinding.FragmentNotes2Binding


class NotesActivity : AppCompatActivity() {

    private lateinit var binding : FragmentNotes2Binding
    private lateinit var viewModel : NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentNotes2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.actionBar.toolbar.title = "Notes"
        binding.actionBar.toolbar.setNavigationOnClickListener {
            this@NotesActivity.onBackPressedDispatcher.onBackPressed()
        }

        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        binding.recyclerView.layoutManager = LinearLayoutManager(this@NotesActivity)

        viewModel.observeNotes()

    }

    override fun onStart() {
        super.onStart()

        val progress = CustomProgressDialog(this@NotesActivity)
        progress.show()

        val token = SPref.get(this@NotesActivity,SPref.token)
        val intent = intent
        val subjectId = intent.getIntExtra("id",-1)
        viewModel.getNotes(token,subjectId)


        viewModel.observeNotes().observe(this@NotesActivity){data->
            binding.recyclerView.adapter = NotesAdapter(this@NotesActivity,data!!.CONTENT)
            progress.dismiss()
        }

        viewModel.observeError().observe(this@NotesActivity){
            progress.dismiss()
            Constant.error(this@NotesActivity,it)
        }

    }
}