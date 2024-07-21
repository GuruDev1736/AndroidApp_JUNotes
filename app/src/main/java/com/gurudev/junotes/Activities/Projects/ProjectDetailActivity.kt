package com.gurudev.junotes.Activities.Projects

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.gurudev.junotes.Constants.Constant
import com.gurudev.junotes.Constants.CustomProgressDialog
import com.gurudev.junotes.Constants.SPref
import com.gurudev.junotes.ViewModel.ProjectViewModel
import com.gurudev.junotes.databinding.FragmentProjectDetailBinding


class ProjectDetailActivity : AppCompatActivity() {

    private lateinit var binding : FragmentProjectDetailBinding
    private lateinit var viewModel : ProjectViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentProjectDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.actionBar.toolbar.title = "Project Details"
        binding.actionBar.toolbar.setNavigationOnClickListener {
            this@ProjectDetailActivity.onBackPressedDispatcher.onBackPressed()
        }

        val progress = CustomProgressDialog(this@ProjectDetailActivity)
        progress.show()
        viewModel = ViewModelProvider(this).get(ProjectViewModel::class.java)

        val token = SPref.get(this@ProjectDetailActivity, SPref.token)
        val intent = intent
        val id = intent.getIntExtra("id",0)


        viewModel.getProjectById(token,id)
        viewModel.observeProjectById().observe(this@ProjectDetailActivity){
            progress.dismiss()
            binding.apply {
                title.text = it!!.CONTENT.projectTitle
                description.text = it.CONTENT.projectDescription
                features.text = it.CONTENT.feature
                technologyUsed.text = it.CONTENT.technologyUsed
                link.text = it.CONTENT.githubUrl
            }

        }

        viewModel.observeErrorMessage().observe(this@ProjectDetailActivity){
            Constant.error(this@ProjectDetailActivity,it)
            progress.dismiss()
        }


    }

}