package com.gurudev.junotes.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gurudev.junotes.Activities.Notes.ShowNote
import com.gurudev.junotes.ResponseModel.Notes.CONTENTX
import com.gurudev.junotes.databinding.NotesLayoutBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NotesAdapter(
    private val context: Context,
    private var list: List<CONTENTX>
) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private var filteredList: List<CONTENTX> = list

    class NoteViewHolder(val binding: NotesLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = NotesLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val data = filteredList[position]
        holder.binding.apply {
            title.text = data.title
            uploadedBy.text = data.user.fullName
            date.text = timestampToDateTime(data.date)
            layout.setOnClickListener {
                context.startActivity(
                    Intent(context, ShowNote::class.java)
                        .putExtra("link", data.url)
                        .putExtra("title", data.title)
                )
            }
        }
    }

    private fun timestampToDateTime(timestamp: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

    fun filter(text: String) {
        val searchText = text.lowercase(Locale.getDefault())
        filteredList = if (searchText.isEmpty()) {
            list
        } else {
            list.filter { item ->
                item.title.lowercase(Locale.getDefault()).contains(searchText) ||
                        item.user.fullName.lowercase(Locale.getDefault()).contains(searchText)
            }
        }
        notifyDataSetChanged()
    }

    fun updateNotes(newList: List<CONTENTX>) {
        list = newList
        filteredList = newList
        notifyDataSetChanged()
    }
}
