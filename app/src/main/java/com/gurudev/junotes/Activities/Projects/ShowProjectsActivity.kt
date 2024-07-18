package com.gurudev.junotes.Activities.Projects

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gurudev.junotes.Adapter.ProjectAdapter
import com.gurudev.junotes.Constants.Constant
import com.gurudev.junotes.Constants.CustomProgressDialog
import com.gurudev.junotes.Constants.SPref
import com.gurudev.junotes.ViewModel.ProjectViewModel
import com.gurudev.junotes.databinding.FragmentShowProjectsBinding

class ShowProjectsActivity : AppCompatActivity() {

    private lateinit var binding : FragmentShowProjectsBinding
    private lateinit var viewModel : ProjectViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentShowProjectsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val progress = CustomProgressDialog(this@ShowProjectsActivity)
        progress.show()

        binding.actionBar.toolbar.setNavigationOnClickListener {
            this@ShowProjectsActivity.onBackPressedDispatcher.onBackPressed()
        }
        binding.actionBar.toolbar.title = "Projects"

        binding.recyclerView.layoutManager = LinearLayoutManager(this@ShowProjectsActivity)

        viewModel = ViewModelProvider(this).get(ProjectViewModel::class.java)

        val token = SPref.get(this@ShowProjectsActivity,SPref.token)
        viewModel.showAllProjects(token)


        viewModel.observeProject().observe(this@ShowProjectsActivity) {data->
            progress.dismiss()
            binding.recyclerView.adapter = ProjectAdapter(this@ShowProjectsActivity,data!!.CONTENT)
        }

        viewModel.observeErrorMessage().observe(this@ShowProjectsActivity) {
            Constant.error(this@ShowProjectsActivity,it)
            progress.dismiss()
        }

    }
}