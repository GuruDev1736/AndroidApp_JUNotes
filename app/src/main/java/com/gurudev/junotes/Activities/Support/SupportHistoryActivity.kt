package com.gurudev.junotes.Activities.Support

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gurudev.junotes.Adapter.SupportHistoryAdapter
import com.gurudev.junotes.Constants.Constant
import com.gurudev.junotes.Constants.CustomProgressDialog
import com.gurudev.junotes.Constants.SPref
import com.gurudev.junotes.R
import com.gurudev.junotes.ViewModel.SupportViewModel
import com.gurudev.junotes.databinding.FragmentSupportHistoryBinding

class SupportHistoryActivity : AppCompatActivity() {

    private lateinit var binding : FragmentSupportHistoryBinding
    private lateinit var viewModel : SupportViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSupportHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.actionBar.toolbar.title = "Support History"
        binding.actionBar.toolbar.setNavigationOnClickListener {
            this@SupportHistoryActivity.onBackPressedDispatcher.onBackPressed()
        }

        val progress = CustomProgressDialog(this@SupportHistoryActivity)
        progress.show()

        viewModel = ViewModelProvider(this).get(SupportViewModel::class.java)

        val token = SPref.get(this@SupportHistoryActivity,SPref.token)
        val userId = SPref.get(this@SupportHistoryActivity,SPref.userId).toInt()
        viewModel.support(token,userId)

        binding.recyclerView.layoutManager = LinearLayoutManager(this@SupportHistoryActivity)

        viewModel.observeSupport().observe(this@SupportHistoryActivity){
            progress.dismiss()
            binding.recyclerView.adapter = SupportHistoryAdapter(this@SupportHistoryActivity,it!!.CONTENT)
        }

        viewModel.observeErrorMessage().observe(this@SupportHistoryActivity){
            progress.dismiss()
            Constant.error(this@SupportHistoryActivity,it)
        }

    }
}