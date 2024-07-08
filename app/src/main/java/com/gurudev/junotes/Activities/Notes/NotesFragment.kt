package com.gurudev.junotes.Activities.Notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gurudev.junotes.R
import com.gurudev.junotes.databinding.FragmentNotes2Binding


class NotesFragment : Fragment() {

    private lateinit var binding : FragmentNotes2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotes2Binding.inflate(layoutInflater)

        binding.actionBar.toolbar.title = "Notes"
        binding.actionBar.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }


        return binding.root
    }
}