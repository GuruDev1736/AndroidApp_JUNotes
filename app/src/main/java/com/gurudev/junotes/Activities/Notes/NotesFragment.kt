package com.gurudev.junotes.Activities.Notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gurudev.junotes.Adapter.NotesAdapter
import com.gurudev.junotes.Constants.Constant
import com.gurudev.junotes.Constants.CustomProgressDialog
import com.gurudev.junotes.Constants.SPref
import com.gurudev.junotes.R
import com.gurudev.junotes.ViewModel.NotesViewModel
import com.gurudev.junotes.databinding.FragmentNotes2Binding


class NotesFragment : Fragment() {

    private lateinit var binding : FragmentNotes2Binding
    private lateinit var viewModel : NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotes2Binding.inflate(layoutInflater)

        binding.actionBar.toolbar.title = "Notes"
        binding.actionBar.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.observeNotes()


        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val progress = CustomProgressDialog(requireContext())
        progress.show()

        val token = SPref.get(requireContext(),SPref.token)
        val bundle = arguments
        val subjectId = bundle?.getInt("id")!!.toInt()
        viewModel.getNotes(token,subjectId)


        viewModel.observeNotes().observe(viewLifecycleOwner){data->
            binding.recyclerView.adapter = NotesAdapter(requireContext(),data!!.CONTENT)
            progress.dismiss()
        }

        viewModel.observeError().observe(viewLifecycleOwner){
            progress.dismiss()
            Constant.error(requireContext(),it)
        }

    }
}