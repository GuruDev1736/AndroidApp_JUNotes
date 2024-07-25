package com.gurudev.junotes.Activities.ProgrammingTut

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.gurudev.junotes.Adapter.LanguageAdapter
import com.gurudev.junotes.Adapter.TutAdapter
import com.gurudev.junotes.Constants.Constant
import com.gurudev.junotes.Constants.CustomProgressDialog
import com.gurudev.junotes.Constants.SPref
import com.gurudev.junotes.R
import com.gurudev.junotes.ViewModel.TutorialViewModel
import com.gurudev.junotes.databinding.ActivityShowTutorialBinding
import com.gurudev.junotes.databinding.ActivityShowTutorialsBinding

class ShowTutorial : AppCompatActivity() {

    private lateinit var binding : ActivityShowTutorialBinding
    private lateinit var viewModel : TutorialViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowTutorialBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this).get(TutorialViewModel::class.java)


        binding.actionBar.toolbar.title = "Tutorials"
        binding.actionBar.toolbar.setNavigationOnClickListener {
            this.onBackPressedDispatcher.onBackPressed()
        }

        val progress = CustomProgressDialog(this)
        progress.show()

        val id = intent.getIntExtra("id",-1)

        val token = SPref.get(this, SPref.token)
        viewModel.getAllTut(token,id)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.observeTutorial().observe(this){data->
            progress.dismiss()
            binding.recyclerView.adapter = TutAdapter(this, data!!.CONTENT)
        }

        viewModel.observeErrorMessage().observe(this){
            Constant.error(this, it)
            progress.dismiss()
        }





    }
}