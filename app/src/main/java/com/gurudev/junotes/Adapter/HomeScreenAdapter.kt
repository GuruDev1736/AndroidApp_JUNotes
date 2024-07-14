package com.gurudev.junotes.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.gurudev.junotes.Model.HomeScreenModel
import com.gurudev.junotes.R
import com.gurudev.junotes.databinding.HomescreenLayoutBinding

class HomeScreenAdapter(
    private val context: Context,
    private val buttonList: List<HomeScreenModel>
) : RecyclerView.Adapter<HomeScreenAdapter.ButtonViewHolder>() {

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

        fun bind(buttonItem: HomeScreenModel, context: Context) {
            binding.icon.setImageResource(buttonItem.icon)
            binding.text.text = buttonItem.text
            binding.layout.setOnClickListener {
                when (buttonItem.text) {
                    "Notes" -> Navigation.findNavController(it).navigate(R.id.notes)
                    "Support History" -> Navigation.findNavController(it).navigate(R.id.supportHistory)
//
                }
            }
        }
    }
}
