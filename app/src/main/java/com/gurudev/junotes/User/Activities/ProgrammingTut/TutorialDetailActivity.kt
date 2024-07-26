package com.gurudev.junotes.User.Activities.ProgrammingTut

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.gurudev.junotes.R
import com.gurudev.junotes.databinding.ActivityTutorialDetailBinding

class TutorialDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTutorialDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTutorialDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.actionBar.toolbar.title = "Tutorial Details"
        binding.actionBar.toolbar.setNavigationOnClickListener {
            this@TutorialDetailActivity.onBackPressedDispatcher.onBackPressed()
        }

        val logo  =  intent.getStringExtra("logo")
        Glide.with(this@TutorialDetailActivity)
            .load(logo).placeholder(R.drawable.user).into(binding.createrImg)

        binding.createrName.text = intent.getStringExtra("name")
        binding.title.text = intent.getStringExtra("title")
        binding.date.text = intent.getStringExtra("date")
        binding.language.text = intent.getStringExtra("language")
        binding.videoLink.text = intent.getStringExtra("video")
        binding.notesLink.text = intent.getStringExtra("note")


    }
}