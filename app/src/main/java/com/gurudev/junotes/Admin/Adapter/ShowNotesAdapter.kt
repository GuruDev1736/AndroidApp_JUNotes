package com.gurudev.junotes.Admin.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gurudev.junotes.ResponseModel.Notes.CONTENTX
import com.gurudev.junotes.databinding.HomescreenLayoutBinding

class ShowNotesAdapter(private val context : Context, private val notesList : List<CONTENTX>) : RecyclerView.Adapter<ShowNotesAdapter.onViewHolder>() {

    class onViewHolder(val binding : HomescreenLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): onViewHolder {
        val binding = HomescreenLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return onViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(holder: onViewHolder, position: Int) {
        holder.binding.apply {

        }
    }

}