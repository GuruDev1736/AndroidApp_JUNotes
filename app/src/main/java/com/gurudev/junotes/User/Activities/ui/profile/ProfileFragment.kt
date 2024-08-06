package com.gurudev.junotes.User.Activities.ui.profile

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.gurudev.junotes.Authenthication.Activity_Login
import com.gurudev.junotes.Constants.Constant
import com.gurudev.junotes.Constants.CustomProgressDialog
import com.gurudev.junotes.Constants.SPref
import com.gurudev.junotes.R
import com.gurudev.junotes.ViewModel.ProfileViewModel
import com.gurudev.junotes.databinding.ChangeEmailLayoutBinding
import com.gurudev.junotes.databinding.ChangePasswordLayoutBinding
import com.gurudev.junotes.databinding.FragmentProfileBinding
import com.gurudev.junotes.databinding.GroupLayoutBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private lateinit var viewModel: ProfileViewModel
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.changePassword.setOnClickListener {
            showPasswordDialog()
        }

        binding.changeEmail.setOnClickListener {
            showEmailDialog()
        }

        binding.logout.setOnClickListener {
            showLogoutDialog()
        }

        binding.support.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.support)
        }

        binding.group.setOnClickListener{
            groupLinkDialog()
        }

        binding.editProfile.visibility = View.INVISIBLE

        return root
    }


    private fun groupLinkDialog() {

        val dialog = Dialog(requireContext(), R.style.TransparentDialog)
        val binding = GroupLayoutBinding.inflate(LayoutInflater.from(requireContext()))
        dialog.setContentView(binding.root)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)

        val layoutParams = binding.root.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.setMargins(50, 0, 50, 0)
        binding.root.layoutParams = layoutParams


        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        binding.telegram.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://t.me/+j9DwY9-xiUs4YjU1")
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            dialog.dismiss()
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.whatsapp.setOnClickListener{
            Constant.success(requireContext(),"We are sorry to say that this feature is not available")
        }



        dialog.show()
    }

        private fun showEmailDialog() {

            val dialog = Dialog(requireContext(), R.style.TransparentDialog)
            val binding = ChangeEmailLayoutBinding.inflate(LayoutInflater.from(requireContext()))
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

            binding.back.setOnClickListener {
                dialog.dismiss()
            }

            val email = SPref.get(requireContext(), SPref.email)
            binding.email.setText(email)

            binding.submit.setOnClickListener {

                val emailId = binding.email.text.toString()
                val userId = SPref.get(requireContext(), SPref.userId)

                if (emailId.isEmpty()) {
                    binding.email.error = "Please enter email"
                    binding.email.requestFocus()
                    return@setOnClickListener
                } else {
                    changeEmail(emailId, userId)
                }
            }
            dialog.show()
        }


    private fun changeEmail(email: String , userId: String) {

        val progress = CustomProgressDialog(requireContext())
        progress.show()

        viewModel.observeEmail().removeObservers(viewLifecycleOwner)
        viewModel.observeErrorMessage().removeObservers(viewLifecycleOwner)

        viewModel.observeEmail().observe(viewLifecycleOwner){data->
            if (data != null) {
                Constant.success(requireContext(),data.MSG)
                progress.dismiss()
                val intent = Intent(requireContext(),Activity_Login::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                SPref.set(requireContext(),SPref.email,email)
            }
        }

        viewModel.observeErrorMessage().observe(viewLifecycleOwner){
            Constant.error(requireContext(),it)
            progress.dismiss()
        }

        viewModel.changeEmail(email,userId)


    }

    private fun showLogoutDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Sign Out")
        builder.setMessage("Are you sure you want to logout?")

        builder.setPositiveButton("Yes") { dialog, _ ->
            // Perform logout operation here
            dialog.dismiss()
            // Example: Go back to login activity
            val intent = Intent(requireContext(), Activity_Login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
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
            val userId = SPref.get(requireContext(),SPref.userId)
            if (lastPassword.isEmpty())
            {
                binding.lastPassword.error = "Please enter last password"
                binding.lastPassword.requestFocus()
                return@setOnClickListener
            }
            if (newPassword.isEmpty()){
                binding.newPassword.error = "Please enter new password"
                binding.newPassword.requestFocus()
                return@setOnClickListener
            }

            changePassword(lastPassword,newPassword,userId)
        }
        dialog.show()
    }

    private fun changePassword(lastPassword: String, newPassword: String, userId: String) {

        val progress = CustomProgressDialog(requireContext())
        progress.show()

        viewModel.observePassword().removeObservers(viewLifecycleOwner)
        viewModel.observeErrorMessage().removeObservers(viewLifecycleOwner)

        viewModel.observePassword().observe(viewLifecycleOwner){data->
            if (data != null) {
                Constant.success(requireContext(),data.MSG)
                progress.dismiss()
            }
        }

        viewModel.observeErrorMessage().observe(viewLifecycleOwner){
            Constant.error(requireContext(),it)
            progress.dismiss()
        }

        viewModel.changePassword(lastPassword,newPassword,userId)
    }



    override fun onStart() {
        super.onStart()

        val progress = CustomProgressDialog(requireContext())
        progress.show()

        val token = SPref.get(requireContext(),SPref.token)
        val userId = SPref.get(requireContext(),SPref.userId).toInt()
        viewModel.getUserById(token,userId)
        viewModel.observeUser().observe(viewLifecycleOwner){data ->
            binding.userName.text = data!!.CONTENT.fullName
            binding.userId.text = "User ID : "+data.CONTENT.id.toString()
            progress.dismiss()
        }

        viewModel.observeErrorMessage().observe(viewLifecycleOwner){
            Constant.error(requireContext(),it)
            progress.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}