package com.gurudev.junotes.Activities.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.gurudev.junotes.Adapter.HomeScreenAdapter
import com.gurudev.junotes.Model.HomeScreenModel
import com.gurudev.junotes.R
import com.gurudev.junotes.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        val buttonList = listOf(
            HomeScreenModel(R.drawable.notes,"Notes"),
            HomeScreenModel(R.drawable.projects,"Projects"),
            HomeScreenModel(R.drawable.code,"Programming Tutorials"),
            HomeScreenModel(R.drawable.performance,"Track Performance"),
            HomeScreenModel(R.drawable.aboutus,"About Us"),
            HomeScreenModel(R.drawable.feedback,"Support History")
        )

        binding.recyclerView.adapter = HomeScreenAdapter(requireContext(), buttonList)


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}