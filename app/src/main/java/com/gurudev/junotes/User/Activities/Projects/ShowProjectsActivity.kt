package com.gurudev.junotes.User.Activities.Projects

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gurudev.junotes.User.Adapter.ProjectAdapter
import com.gurudev.junotes.Constants.Constant
import com.gurudev.junotes.Constants.CustomProgressDialog
import com.gurudev.junotes.Constants.SPref
import com.gurudev.junotes.ViewModel.ProjectViewModel
import com.gurudev.junotes.databinding.FragmentShowProjectsBinding

class ShowProjectsActivity : AppCompatActivity() {

    private lateinit var binding: FragmentShowProjectsBinding
    private lateinit var viewModel: ProjectViewModel
    private lateinit var adapter: ProjectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentShowProjectsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.actionBar.toolbar.title = "Projects"
        binding.actionBar.toolbar.setNavigationOnClickListener {
            this@ShowProjectsActivity.onBackPressedDispatcher.onBackPressed()
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this@ShowProjectsActivity)
        adapter = ProjectAdapter(this@ShowProjectsActivity, emptyList()) // Initialize with empty list
        binding.recyclerView.adapter = adapter

        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                adapter.filter(s.toString())
            }
        })

        viewModel = ViewModelProvider(this).get(ProjectViewModel::class.java)

        val progress = CustomProgressDialog(this@ShowProjectsActivity)
        progress.show()

        val token = SPref.get(this@ShowProjectsActivity, SPref.token)
        viewModel.showAllProjects(token)

        viewModel.observeProject().observe(this@ShowProjectsActivity) { data ->
            data?.let {
                adapter.updateProjects(it.CONTENT)
            }
            progress.dismiss() // Dismiss progress dialog after data is set
        }

        viewModel.observeErrorMessage().observe(this@ShowProjectsActivity) { errorMessage ->
            Constant.error(this@ShowProjectsActivity, errorMessage)
            progress.dismiss() // Dismiss progress dialog in case of error
        }
    }
}
