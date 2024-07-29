package com.gurudev.junotes.Admin.Adapter

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.gurudev.junotes.Admin.Activities.Notes.Admin_CreateAndUpdateNoteActivity
import com.gurudev.junotes.Constants.Constant
import com.gurudev.junotes.Constants.CustomProgressDialog
import com.gurudev.junotes.Constants.SPref
import com.gurudev.junotes.ResponseModel.Notes.CONTENTX
import com.gurudev.junotes.ResponseModel.Notes.DeleteNoteResponseModel
import com.gurudev.junotes.ViewModel.NotesViewModel
import com.gurudev.junotes.databinding.HomescreenLayoutBinding
import com.gurudev.junotes.databinding.NotesLayoutBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ShowNotesAdapter(private val context : Context ,private val notesList : List<CONTENTX>) : RecyclerView.Adapter<ShowNotesAdapter.onViewHolder>() {

    class onViewHolder(val binding : NotesLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    private lateinit var viewModel : NotesViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): onViewHolder {
        val binding = NotesLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return onViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(holder: onViewHolder, position: Int) {

        viewModel = ViewModelProvider(context as AppCompatActivity)[NotesViewModel::class.java]

        holder.binding.apply {

            val note = notesList[position]
            title.text = note.title
            uploadedBy.text = note.user.fullName
            date.text = timestampToDateTime(note.date)
            buttons.visibility = View.VISIBLE
            update.setOnClickListener{
                context.startActivity(Intent(context,Admin_CreateAndUpdateNoteActivity::class.java)
                    .putExtra("code",1)
                )
            }

            delete.setOnClickListener{
                val alertDialog = AlertDialog.Builder(context)
                alertDialog.setTitle("Delete")
                alertDialog.setMessage("Are you sure you want to delete this note?")
                alertDialog.setPositiveButton("Yes",object : DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        deleteNote(note)
                    }

                })

                alertDialog.setNegativeButton("No",object : DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        p0!!.dismiss()
                    }

                })

                alertDialog.show()

            }
        }
    }

    private fun deleteNote(note : CONTENTX) {
        val progress = CustomProgressDialog(context)
        progress.show()
        val token = SPref.get(context,SPref.token)
        val noteId = note.id
        viewModel.deleteNote(token,noteId)
        viewModel.observeDeleteNote().observe(context as AppCompatActivity){
            Constant.success(context,it!!.MSG)
            context.onBackPressedDispatcher.onBackPressed()
            progress.dismiss()
        }

        viewModel.observeError().observe(context){
            Constant.error(context,it)
            progress.dismiss()
        }
    }

    private fun timestampToDateTime(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd-mm-yyyy HH:mm:ss", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

}