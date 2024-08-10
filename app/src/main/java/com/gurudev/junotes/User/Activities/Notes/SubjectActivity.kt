package com.gurudev.junotes.User.Activities.Notes

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.gurudev.junotes.User.Adapter.NotesSubjectAdapter
import com.gurudev.junotes.Constants.Constant
import com.gurudev.junotes.Constants.CustomProgressDialog
import com.gurudev.junotes.Constants.SPref
import com.gurudev.junotes.R
import com.gurudev.junotes.ViewModel.NotesViewModel
import com.gurudev.junotes.databinding.FragmentNotesBinding

class SubjectActivity : AppCompatActivity() {

    private lateinit var viewModel: NotesViewModel
    private lateinit var binding : FragmentNotesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
        val progress = CustomProgressDialog(this@SubjectActivity)
        progress.show()

        binding.actionBar.toolbar.title = "Subjects"
        binding.actionBar.toolbar.setNavigationOnClickListener {
            this@SubjectActivity.onBackPressedDispatcher.onBackPressed()
        }

        binding.recyclerView.layoutManager = GridLayoutManager(this@SubjectActivity, 2)

        val token = SPref.get(this@SubjectActivity, SPref.token)
        val year = SPref.get(this@SubjectActivity, SPref.yearId).toInt()

        viewModel.getSubjects(token,year)

        viewModel.observeSubject().observe(this@SubjectActivity){ data->

            if (data!!.CONTENT.isEmpty()) {
                binding.note.visibility = View.VISIBLE
            }
            else
            {
                binding.note.visibility = View.GONE
            }

            binding.recyclerView.adapter = NotesSubjectAdapter(this@SubjectActivity, data.CONTENT)
            progress.dismiss()
        }

        viewModel.observeError().observe(this@SubjectActivity)
        {
            progress.dismiss()
            Constant.error(this@SubjectActivity,it)
        }
    }

}