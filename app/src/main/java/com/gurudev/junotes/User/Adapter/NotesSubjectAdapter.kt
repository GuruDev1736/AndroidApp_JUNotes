package com.gurudev.junotes.User.Adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.gurudev.junotes.Constants.Constant
import com.gurudev.junotes.Model.HomeScreenModel
import com.gurudev.junotes.R
import com.gurudev.junotes.ResponseModel.Notes.CONTENT
import com.gurudev.junotes.User.Activities.Notes.NotesActivity
import com.gurudev.junotes.databinding.HomescreenLayoutBinding

class NotesSubjectAdapter(
    private val context: Context,
    private val buttonList: List<CONTENT>
) : RecyclerView.Adapter<NotesSubjectAdapter.ButtonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonViewHolder {
        val view = HomescreenLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ButtonViewHolder(view)
    }

    override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {
        val buttonItem = buttonList[position]
        holder.bind(buttonItem, context)
    }

    override fun getItemCount(): Int = buttonList.size

    class ButtonViewHolder(val binding : HomescreenLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(buttonItem: CONTENT, context: Context) {
            binding.icon.setImageResource(R.drawable.elearning)
            binding.text.text = buttonItem.subName

            binding.layout.setOnClickListener {
               val intent = Intent(context, NotesActivity::class.java)
                intent.putExtra("id",buttonItem.id)
                context.startActivity(intent)
            }
        }
    }
}
