package com.gurudev.junotes.Adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.gurudev.junotes.R
import com.gurudev.junotes.ResponseModel.Profile.CONTENTX
import com.gurudev.junotes.databinding.FragmentSupportHistoryBinding
import com.gurudev.junotes.databinding.SupportLayoutBinding

class SupportHistoryAdapter(val context: Context , val list : List<CONTENTX>) : RecyclerView.Adapter<SupportHistoryAdapter.onViewHolder>() {

    class onViewHolder(val binding: SupportLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): onViewHolder {
        val view = SupportLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return onViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: onViewHolder, position: Int) {
        val data = list.get(position)
        holder.binding.apply {
            issueId.text = data.id.toString()
            issue.text = data.message
            status.text =  data.isSolved.toString()
            if (data.isSolved)
            {
                status.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green)))
            }
            else
            {
                status.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red)))
            }

        }
    }

}