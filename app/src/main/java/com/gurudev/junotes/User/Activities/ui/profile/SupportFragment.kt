package com.gurudev.junotes.User.Activities.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gurudev.junotes.Constants.Constant
import com.gurudev.junotes.Constants.CustomProgressDialog
import com.gurudev.junotes.Constants.SPref
import com.gurudev.junotes.R
import com.gurudev.junotes.RequestModel.SupportRequestModel
import com.gurudev.junotes.Retrofit.RetrofitInstance
import com.gurudev.junotes.ViewModel.ProfileViewModel
import com.gurudev.junotes.databinding.FragmentSupportBinding


class SupportFragment : Fragment() {

    private lateinit var binding: FragmentSupportBinding
    private lateinit var viewModel : ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSupportBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)


        binding.sendBtn.setOnClickListener{

            val progress = CustomProgressDialog(requireContext())

            val name = binding.name.text.toString()
            val email = binding.email.text.toString()
            val msg = binding.msg.text.toString()

            if (valid(name,email,msg))
            {
                progress.show()
                val token = SPref.get(requireContext(), SPref.token)
                val userId = SPref.get(requireContext(), SPref.userId)
                val model = SupportRequestModel(email,msg,name,userId)

                viewModel.observeSupport().removeObservers(viewLifecycleOwner)
                viewModel.observeErrorMessage().removeObservers(viewLifecycleOwner)

                viewModel.observeSupport().observe(viewLifecycleOwner){data->
                    Constant.success(requireContext(),data!!.MSG)
                    progress.dismiss()
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }

                viewModel.observeErrorMessage().observe(viewLifecycleOwner){
                    Constant.error(requireContext(),it)
                    progress.dismiss()
                }
                viewModel.support(token,model)
            }

        }



        return binding.root
    }

    private fun valid(name: String, email: String, msg: String): Boolean {

        if (name.isEmpty())
        {
            binding.name.error = "Please enter name"
            binding.name.requestFocus()
            return false
        }
        if (email.isEmpty()) {
            binding.email.error = "Please enter email"
            binding.email.requestFocus()
            return false
        }
        if (msg.isEmpty()) {
            binding.msg.error = "Please enter message"
            binding.msg.requestFocus()
            return false
        }
        return true
    }

    override fun onStart() {
        super.onStart()
        val progress = CustomProgressDialog(requireContext())
        progress.show()

        val token = SPref.get(requireContext(), SPref.token)
        val userId = SPref.get(requireContext(), SPref.userId).toInt()

        viewModel.observeUser().removeObservers(viewLifecycleOwner)
        viewModel.observeErrorMessage().removeObservers(viewLifecycleOwner)

        viewModel.observeUser().observe(viewLifecycleOwner){data->
            progress.dismiss()

            binding.name.setText(data!!.CONTENT.fullName)
            binding.email.setText(data.CONTENT.email)

        }

        viewModel.observeErrorMessage().observe(viewLifecycleOwner){
            Constant.error(requireContext(),it)
            progress.dismiss()
        }

        viewModel.getUserById(token,userId)
    }

}