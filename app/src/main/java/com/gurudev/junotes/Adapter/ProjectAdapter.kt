package com.gurudev.junotes.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gurudev.junotes.Activities.Projects.ProjectDetailActivity
import com.gurudev.junotes.ResponseModel.Notes.CONTENTX
import com.gurudev.junotes.ResponseModel.Projects.CONTENT
import com.gurudev.junotes.databinding.ProjectLayoutBinding
import java.util.Locale

class ProjectAdapter(val context: Context , var list : List<CONTENT>) : RecyclerView.Adapter<ProjectAdapter.onViewHolder>() {

    private var filteredList: List<CONTENT> = list

    class onViewHolder(val binding : ProjectLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): onViewHolder {
        val view = ProjectLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return onViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun onBindViewHolder(holder: onViewHolder, position: Int) {
        val data = filteredList[position]
        holder.binding.apply {
            title.text = data.projectTitle
            language.text = data.language
            description.text = data.projectDescription
            details.setOnClickListener{
               context.startActivity(Intent(context, ProjectDetailActivity::class.java).putExtra("id",data.id))
            }
        }
    }

    fun filter(text: String) {
        val searchText = text.lowercase(Locale.getDefault())
        filteredList = if (searchText.isEmpty()) {
            list
        } else {
            list.filter { item ->
                item.projectTitle.lowercase(Locale.getDefault()).contains(searchText) ||
                        item.language.lowercase(Locale.getDefault()).contains(searchText)
            }
        }
        notifyDataSetChanged()
    }

    fun updateProjects(newList: List<CONTENT>) {
        list = newList
        filteredList = newList
        notifyDataSetChanged()
    }
}