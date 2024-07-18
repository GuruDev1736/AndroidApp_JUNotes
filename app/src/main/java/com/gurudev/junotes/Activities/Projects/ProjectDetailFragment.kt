package com.gurudev.junotes.Activities.Projects

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.gurudev.junotes.R
import com.gurudev.junotes.databinding.FragmentProjectDetailBinding
import com.gurudev.junotes.databinding.FragmentShowProjectsBinding


class ProjectDetailFragment : AppCompatActivity() {

    private lateinit var binding : FragmentProjectDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentProjectDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

}