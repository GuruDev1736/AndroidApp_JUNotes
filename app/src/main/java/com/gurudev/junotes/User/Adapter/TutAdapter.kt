package com.gurudev.junotes.User.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.gurudev.junotes.Constants.Constant
import com.gurudev.junotes.Constants.CustomProgressDialog
import com.gurudev.junotes.Constants.SPref
import com.gurudev.junotes.ResponseModel.Tut.CONTENT
import com.gurudev.junotes.User.Activities.ProgrammingTut.TutorialDetailActivity
import com.gurudev.junotes.ViewModel.ProjectViewModel
import com.gurudev.junotes.ViewModel.TutorialViewModel
import com.gurudev.junotes.databinding.ProjectLayoutBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TutAdapter(private val context: Context, private val projectList: List<CONTENT>) : RecyclerView.Adapter<TutAdapter.onViewHolder>() {

    private lateinit var viewModel : TutorialViewModel

    class onViewHolder(val binding : ProjectLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): onViewHolder {
        val view = ProjectLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return onViewHolder(view)
    }

    override fun getItemCount(): Int {
        return projectList.size
    }

    override fun onBindViewHolder(holder: onViewHolder, position: Int) {

        viewModel = ViewModelProvider(context as AppCompatActivity).get(TutorialViewModel::class.java)

        val id = projectList[position].id
        val token = SPref.get(context, SPref.token)
        viewModel.getLangById(token,id)

        val progress = CustomProgressDialog(context)
        progress.show()
        viewModel.observeGetLangById().observe(context){
            progress.dismiss()
            holder.binding.language.text = it!!.CONTENT.languageName
        }

        viewModel.observeErrorMessage().observe(context){
            progress.dismiss()
            Constant.error(context, it)
        }

        holder.binding.apply {
            val project = projectList[position]
            title.text = project.title
            description.text = project.createrName
            date.text = convertTimestampToDate(project.date)
            details.setOnClickListener {
                context.startActivity(Intent(context, TutorialDetailActivity::class.java)
                    .putExtra("logo",project.createrLogo)
                    .putExtra("name",project.createrName)
                    .putExtra("title",project.title)
                    .putExtra("date",date.text.toString())
                    .putExtra("language",language.text.toString())
                    .putExtra("video",project.playlistLink)
                    .putExtra("note",project.notesLink)
                )
            }
        }
    }

    fun convertTimestampToDate(timestamp: Long): String {
        val milliseconds = if (timestamp < 10000000000L) timestamp * 1000 else timestamp
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val date = Date(milliseconds)
        return sdf.format(date)
    }
}