package com.gurudev.junotes.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfRenderer
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gurudev.junotes.Activities.Notes.ShowNote
import com.gurudev.junotes.ResponseModel.Notes.CONTENTX
import com.gurudev.junotes.databinding.NotesLayoutBinding
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NotesAdapter(val context: Context , val list: List<CONTENTX> ) :
    RecyclerView.Adapter<NotesAdapter.onViewHolder>() {

    class onViewHolder(val binding : NotesLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): onViewHolder {
        val view = NotesLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return onViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: onViewHolder, position: Int) {

        val data = list[position]


        holder.binding.apply {
            title.text = data.title
            uploadedBy.text = data.user.fullName
            date.text = timestampToDateTime(data.date)
            layout.setOnClickListener{
                context.startActivity(Intent(context,ShowNote::class.java).putExtra("link",data.url)
                    .putExtra("title",data.title))
            }
        }
    }

    fun timestampToDateTime(timestamp: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

}