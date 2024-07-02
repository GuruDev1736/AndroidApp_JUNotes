package com.gurudev.junotes.Activities.ui.profile

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gurudev.junotes.Constants.CustomProgressDialog
import com.gurudev.junotes.Constants.SPref
import com.gurudev.junotes.R
import com.gurudev.junotes.ViewModel.ProfileViewModel
import com.gurudev.junotes.databinding.ChangePasswordLayoutBinding
import com.gurudev.junotes.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private lateinit var viewModel : ProfileViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.changePassword.setOnClickListener{
            showPasswordDialog()
        }


        return root
    }

    private fun showPasswordDialog() {
        val dialog = Dialog(requireContext(),R.style.TransparentDialog)
        val binding = ChangePasswordLayoutBinding.inflate(LayoutInflater.from(requireContext()))
        dialog.setContentView(binding.root)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)

        val layoutParams = binding.root.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.setMargins(50, 0, 50, 0)
        binding.root.layoutParams = layoutParams


        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        binding.back.setOnClickListener{
            dialog.dismiss()
        }

        binding.change.setOnClickListener{
            val lastPassword = binding.lastPassword.text.toString()
            val newPassword = binding.newPassword.text.toString()

            if (lastPassword.isEmpty())
            {
                binding.lastPassword.error = "Please enter last password"
                return@setOnClickListener
            }
            if (newPassword.isEmpty()){
                binding.newPassword.error = "Please enter new password"
                return@setOnClickListener
            }




        }


        dialog.show()
    }

    override fun onStart() {
        super.onStart()

        val progress = CustomProgressDialog(requireContext())
        progress.show()

        val token = SPref.get(requireContext(),SPref.token)
        val userId = SPref.get(requireContext(),SPref.userId).toInt()
        viewModel.getUserById(token,userId)
        viewModel.observeUser().observe(viewLifecycleOwner){data ->
            progress.dismiss()
            binding.userName.text = data!!.CONTENT.fullName
            binding.userId.text = "User ID : "+data.CONTENT.id.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}