package com.gurudev.junotes.Activities.Notes

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
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

    private lateinit var binding: FragmentNotes2Binding
    private lateinit var viewModel: NotesViewModel
    private lateinit var adapter: NotesAdapter

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
        adapter = NotesAdapter(this@NotesActivity, emptyList())
        binding.recyclerView.adapter = adapter

        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                adapter.filter(s.toString())
            }
        })
    }

    override fun onStart() {
        super.onStart()

        val progress = CustomProgressDialog(this@NotesActivity)
        progress.show()

        val token = SPref.get(this@NotesActivity, SPref.token)
        val intent = intent
        val subjectId = intent.getIntExtra("id", -1)
        viewModel.getNotes(token, subjectId)

        viewModel.observeNotes().observe(this@NotesActivity) { data ->
            data?.CONTENT?.let {
                adapter.updateNotes(it)
                progress.dismiss()
            }
        }

        viewModel.observeError().observe(this@NotesActivity) {
            progress.dismiss()
            Constant.error(this@NotesActivity, it)
        }
    }
}
