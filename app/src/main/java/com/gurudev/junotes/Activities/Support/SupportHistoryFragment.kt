package com.gurudev.junotes.Activities.Support

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gurudev.junotes.Adapter.SupportHistoryAdapter
import com.gurudev.junotes.Constants.Constant
import com.gurudev.junotes.Constants.CustomProgressDialog
import com.gurudev.junotes.Constants.SPref
import com.gurudev.junotes.R
import com.gurudev.junotes.ViewModel.SupportViewModel
import com.gurudev.junotes.databinding.FragmentSupportHistoryBinding

class SupportHistoryFragment : Fragment() {

    private lateinit var binding : FragmentSupportHistoryBinding
    private lateinit var viewModel : SupportViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSupportHistoryBinding.inflate(layoutInflater)

        binding.actionBar.toolbar.title = "Support History"
        binding.actionBar.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        val progress = CustomProgressDialog(requireContext())
        progress.show()

        viewModel = ViewModelProvider(this).get(SupportViewModel::class.java)

        val token = SPref.get(requireContext(),SPref.token)
        val userId = SPref.get(requireContext(),SPref.userId).toInt()
        viewModel.support(token,userId)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.observeSupport().observe(viewLifecycleOwner){
            progress.dismiss()
            binding.recyclerView.adapter = SupportHistoryAdapter(requireContext(),it!!.CONTENT)
        }

        viewModel.observeErrorMessage().observe(viewLifecycleOwner){
            progress.dismiss()
            Constant.error(requireContext(),it)
        }
        return binding.root
    }
}