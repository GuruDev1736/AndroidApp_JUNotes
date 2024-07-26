package com.gurudev.junotes.User.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gurudev.junotes.Admin.Activities.Notes.Admin_ShowSubjectActivity
import com.gurudev.junotes.Model.HomeScreenModel
import com.gurudev.junotes.User.Activities.Notes.SubjectActivity
import com.gurudev.junotes.User.Activities.ProgrammingTut.ShowLanguages
import com.gurudev.junotes.User.Activities.Projects.ShowProjectsActivity
import com.gurudev.junotes.User.Activities.Support.SupportHistoryActivity
import com.gurudev.junotes.databinding.HomescreenLayoutBinding
import com.gurudev.junotes.User.Activities.TrackProgress.track_performance

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
                    "Notes" -> context.startActivity(Intent(context, SubjectActivity::class.java))
                    "Support History" -> context.startActivity(Intent(context,
                        SupportHistoryActivity::class.java))
                    "Projects" -> context.startActivity(Intent(context, ShowProjectsActivity::class.java))
                    "Track Performance" -> context.startActivity(Intent(context, com.gurudev.junotes.User.Activities.TrackProgress.track_performance::class.java))
                    "Programming Tutorials" -> context.startActivity(Intent(context, ShowLanguages::class.java))
                    "Create Notes" -> context.startActivity(Intent(context, Admin_ShowSubjectActivity::class.java))
                }
            }
        }
    }
}
