package com.gurudev.junotes.Admin.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gurudev.junotes.Admin.Activities.Notes.Admin_ShowSubjectActivity
import com.gurudev.junotes.R
import com.gurudev.junotes.ResponseModel.Notes.Years
import com.gurudev.junotes.databinding.HomescreenLayoutBinding

class ShowYearAdapter(val context: Context, val yearList: List<Years>) : RecyclerView.Adapter<ShowYearAdapter.onViewHolder>() {

    class onViewHolder(val binding : HomescreenLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): onViewHolder {
        val view = HomescreenLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return onViewHolder(view)
    }

    override fun getItemCount(): Int {
        return yearList.size
    }

    override fun onBindViewHolder(holder: onViewHolder, position: Int) {
        holder.binding.apply {
            text.text = yearList[position].yearName
            icon.setImageResource(R.drawable.logo)
            layout.setOnClickListener{
                context.startActivity(Intent(context,Admin_ShowSubjectActivity::class.java)
                    .putExtra("yearId",yearList[position].id)
                )
            }
        }
    }
}