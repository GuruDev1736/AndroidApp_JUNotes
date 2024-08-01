package com.gurudev.junotes.Admin.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gurudev.junotes.Admin.Activities.Notes.Admin_ShowNotesActivity
import com.gurudev.junotes.R
import com.gurudev.junotes.ResponseModel.Notes.CONTENT
import com.gurudev.junotes.ResponseModel.Notes.Subject
import com.gurudev.junotes.databinding.HomescreenLayoutBinding

class ShowSubjectAdapter(private val context : Context , private val subjectList : List<CONTENT>) : RecyclerView.Adapter<ShowSubjectAdapter.onViewHolder>() {

    class onViewHolder(val binding : HomescreenLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): onViewHolder {
        val binding = HomescreenLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return onViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return subjectList.size
    }

    override fun onBindViewHolder(holder: onViewHolder, position: Int) {
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

            }

            delete.setOnClickListener{

            }
        }
    }
}