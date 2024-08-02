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
import com.gurudev.junotes.Admin.Activities.Notes.Admin_CreateAndUpdateYear
import com.gurudev.junotes.Admin.Activities.Notes.Admin_ShowSubjectActivity
import com.gurudev.junotes.Constants.Constant
import com.gurudev.junotes.Constants.CustomProgressDialog
import com.gurudev.junotes.Constants.SPref
import com.gurudev.junotes.R
import com.gurudev.junotes.ResponseModel.Notes.Years
import com.gurudev.junotes.ViewModel.NotesViewModel
import com.gurudev.junotes.databinding.HomescreenLayoutBinding

class ShowYearAdapter(val context: Context, val yearList: MutableList<Years>) : RecyclerView.Adapter<ShowYearAdapter.onViewHolder>() {

    class onViewHolder(val binding: HomescreenLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    private lateinit var viewModel: NotesViewModel

    init {
        viewModel = ViewModelProvider(context as AppCompatActivity)[NotesViewModel::class.java]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): onViewHolder {
        val view = HomescreenLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return onViewHolder(view)
    }

    override fun getItemCount(): Int {
        return yearList.size
    }

    override fun onBindViewHolder(holder: onViewHolder, position: Int) {
        holder.binding.apply {
            buttons.visibility = View.VISIBLE
            text.text = yearList[position].yearName
            icon.setImageResource(R.drawable.logo)
            layout.setOnClickListener {
                context.startActivity(
                    Intent(context, Admin_ShowSubjectActivity::class.java)
                        .putExtra("yearId", yearList[position].id)
                )
            }

            update.setOnClickListener {
                context.startActivity(
                    Intent(context, Admin_CreateAndUpdateYear::class.java)
                        .putExtra("code", 1)
                        .putExtra("yearId", yearList[position].id)
                        .putExtra("yearName", yearList[position].yearName)
                        .putExtra("yearDescription", yearList[position].yearDescription)
                )
            }

            delete.setOnClickListener {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Delete Confirmation")
                builder.setMessage("Are you sure you want to delete this Year?")
                builder.setPositiveButton("Yes") { dialog, which ->
                    deleteItem(yearList[position].id)
                }

                builder.setNegativeButton("No") { dialog, which ->
                    dialog.dismiss()
                }

                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }
    }

    private fun deleteItem(id: Int) {
        val token = SPref.get(context, SPref.token)
        val progress = CustomProgressDialog(context)
        progress.show()

        viewModel.observeDeleteYear().removeObservers(context as AppCompatActivity)
        viewModel.observeError().removeObservers(context as AppCompatActivity)

        viewModel.observeDeleteYear().observe(context as AppCompatActivity) { data ->
            Constant.success(context, data!!.MSG)
            val position = yearList.indexOfFirst { it.id == id }
            if (position != -1) {
                yearList.removeAt(position)
                notifyItemRemoved(position)
            }
            progress.dismiss()
        }

        viewModel.observeError().observe(context as AppCompatActivity) {
            Constant.error(context, it)
            progress.dismiss()
        }

        viewModel.deleteYear(token, id)
    }
}
