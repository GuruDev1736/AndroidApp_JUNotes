package com.gurudev.junotes.Admin.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.gurudev.junotes.Admin.Activities.Notes.Admin_CreateAndUpdateSubject
import com.gurudev.junotes.Admin.Activities.Notes.Admin_ShowNotesActivity
import com.gurudev.junotes.Constants.Constant
import com.gurudev.junotes.Constants.CustomProgressDialog
import com.gurudev.junotes.Constants.SPref
import com.gurudev.junotes.R
import com.gurudev.junotes.ResponseModel.Notes.CONTENT
import com.gurudev.junotes.ResponseModel.Notes.Subject
import com.gurudev.junotes.ViewModel.NotesViewModel
import com.gurudev.junotes.databinding.HomescreenLayoutBinding

class ShowSubjectAdapter(private val context : Context , private val subjectList : MutableList<CONTENT>) : RecyclerView.Adapter<ShowSubjectAdapter.onViewHolder>() {

    class onViewHolder(val binding : HomescreenLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    private lateinit var viewModel : NotesViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): onViewHolder {
        val binding = HomescreenLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return onViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return subjectList.size
    }

    override fun onBindViewHolder(holder: onViewHolder, position: Int) {

        viewModel = ViewModelProvider(context as AppCompatActivity)[NotesViewModel::class.java]

        holder.binding.apply {
            val subject = subjectList[position]
            text.text = subject.subName
            icon.setImageResource(R.drawable.elearning)
            buttons.visibility = View.VISIBLE
            layout.setOnClickListener{
                context.startActivity(Intent(context,Admin_ShowNotesActivity::class.java)
                    .putExtra("subjectId",subject.id)
                )
            }

            update.setOnClickListener{
                context.startActivity(Intent(context,Admin_CreateAndUpdateSubject::class.java)
                    .putExtra("code",1)
                    .putExtra("subjectId",subject.id)
                    .putExtra("subjectName",subject.subName)
                    .putExtra("subjectDescription",subject.subDescription)
                    .putExtra("yearId",subject.yearId)
                )
            }

            delete.setOnClickListener{
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Delete Confirmation")
                builder.setMessage("Are you sure you want to delete this Subject?")
                builder.setPositiveButton("Yes") { dialog, which ->
                    deleteSubject(subject.id)
                }

                builder.setNegativeButton("No") { dialog, which ->
                    dialog.dismiss()
                }

                val dialog: AlertDialog = builder.create()
                dialog.show()
            }

        }
    }

    private fun deleteSubject(id: Int) {

        val token = SPref.get(context, SPref.token)
        val progress = CustomProgressDialog(context)
        progress.show()

        viewModel.observeDeleteSubject().removeObservers(context as AppCompatActivity)
        viewModel.observeError().removeObservers(context as AppCompatActivity)

        viewModel.observeDeleteSubject().observe(context as AppCompatActivity) { data ->
            Constant.success(context, data!!.MSG)
            val position = subjectList.indexOfFirst { it.id == id }
            if (position != -1) {
                subjectList.removeAt(position)
                notifyItemRemoved(position)
            }
            progress.dismiss()
        }

        viewModel.observeError().observe(context as AppCompatActivity) {
            Constant.error(context, it)
            progress.dismiss()
        }

        viewModel.deleteSubject(token, id)


    }
}