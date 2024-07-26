package com.gurudev.junotes.Admin.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gurudev.junotes.R
import com.gurudev.junotes.ResponseModel.Notes.Subject
import com.gurudev.junotes.databinding.HomescreenLayoutBinding

class ShowSubjectAdapter(private val context : Context , private val subjectList : List<Subject>) : RecyclerView.Adapter<ShowSubjectAdapter.onViewHolder>() {

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
            layout.setOnClickListener{

            }
        }
    }
}