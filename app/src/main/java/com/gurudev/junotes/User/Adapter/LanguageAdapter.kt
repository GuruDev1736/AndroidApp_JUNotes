package com.gurudev.junotes.User.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gurudev.junotes.ResponseModel.Tut.Languages
import com.gurudev.junotes.User.Activities.ProgrammingTut.ShowTutorial
import com.gurudev.junotes.databinding.HomescreenLayoutBinding


class LanguageAdapter(private val context : Context, private val languageList : List<Languages>) : RecyclerView.Adapter<LanguageAdapter.onViewHolder>() {

    class onViewHolder(val binding : HomescreenLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): onViewHolder {
        val binding = HomescreenLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return onViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return languageList.size
    }

    override fun onBindViewHolder(holder: onViewHolder, position: Int) {
        holder.binding.apply {
            val language = languageList[position]
            text.text = language.languageName
            layout.setOnClickListener{
                context.startActivity(Intent(context, ShowTutorial::class.java)
                    .putExtra("id",language.id)
                )
            }
        }
    }
}