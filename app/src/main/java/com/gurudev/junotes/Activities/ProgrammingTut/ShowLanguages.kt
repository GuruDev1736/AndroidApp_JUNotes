package com.gurudev.junotes.Activities.ProgrammingTut

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.gurudev.junotes.Adapter.LanguageAdapter
import com.gurudev.junotes.Constants.Constant
import com.gurudev.junotes.Constants.CustomProgressDialog
import com.gurudev.junotes.Constants.SPref
import com.gurudev.junotes.R
import com.gurudev.junotes.ResponseModel.Tut.GetAllLanguageResponseModel
import com.gurudev.junotes.Retrofit.RetrofitInstance
import com.gurudev.junotes.ViewModel.TutorialViewModel
import com.gurudev.junotes.databinding.ActivityShowTutorialsBinding
import retrofit2.Callback

class ShowLanguages : AppCompatActivity() {

    private lateinit var binding : ActivityShowTutorialsBinding
    private lateinit var viewModel : TutorialViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowTutorialsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this).get(TutorialViewModel::class.java)


        binding.actionBar.toolbar.title = "Languages"
        binding.actionBar.toolbar.setNavigationOnClickListener {
            this@ShowLanguages.onBackPressedDispatcher.onBackPressed()
        }

        val progress = CustomProgressDialog(this@ShowLanguages)
        progress.show()

        val token = SPref.get(this@ShowLanguages,SPref.token)
        viewModel.getAllLang(token)
        binding.recyclerView.layoutManager = GridLayoutManager(this@ShowLanguages,2)

        viewModel.observeLanguage().observe(this@ShowLanguages){data->
            progress.dismiss()
            binding.recyclerView.adapter = LanguageAdapter(this@ShowLanguages, data!!.CONTENT)
        }

        viewModel.observeErrorMessage().observe(this@ShowLanguages){
            Constant.error(this@ShowLanguages, it)
            progress.dismiss()
        }



    }
}