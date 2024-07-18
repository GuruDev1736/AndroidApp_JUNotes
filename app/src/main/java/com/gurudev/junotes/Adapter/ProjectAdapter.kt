package com.gurudev.junotes.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.gurudev.junotes.Activities.Projects.ProjectDetailFragment
import com.gurudev.junotes.R
import com.gurudev.junotes.ResponseModel.Projects.CONTENT
import com.gurudev.junotes.databinding.ProjectLayoutBinding

class ProjectAdapter(val context: Context , val list : List<CONTENT>) : RecyclerView.Adapter<ProjectAdapter.onViewHolder>() {

    class onViewHolder(val binding : ProjectLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): onViewHolder {
        val view = ProjectLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return onViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: onViewHolder, position: Int) {
        val data = list[position]
        holder.binding.apply {
            title.text = data.projectTitle
            language.text = data.language
            description.text = data.projectDescription
            details.setOnClickListener{
               context.startActivity(Intent(context, ProjectDetailFragment::class.java))
            }
        }
    }
}