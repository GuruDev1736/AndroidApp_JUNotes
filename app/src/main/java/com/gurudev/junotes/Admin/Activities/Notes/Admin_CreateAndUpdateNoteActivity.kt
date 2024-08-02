package com.gurudev.junotes.Admin.Activities.Notes

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.provider.OpenableColumns
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.storage.FirebaseStorage
import com.gurudev.junotes.Constants.Constant
import com.gurudev.junotes.Constants.CustomProgressDialog
import com.gurudev.junotes.Constants.SPref
import com.gurudev.junotes.Constants.UrlInputFilter
import com.gurudev.junotes.Constants.Validation
import com.gurudev.junotes.Model.YearData
import com.gurudev.junotes.R
import com.gurudev.junotes.RequestModel.CreateNoteRequestModel
import com.gurudev.junotes.RequestModel.UpdateNoteRequestModel
import com.gurudev.junotes.ViewModel.NotesViewModel
import com.gurudev.junotes.databinding.ActivityAdminCreateAndUpdateNoteBinding
import java.io.ByteArrayOutputStream
import java.io.InputStream

class Admin_CreateAndUpdateNoteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAdminCreateAndUpdateNoteBinding
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var viewModel : NotesViewModel
    private var imageUri: Uri? = null
    private lateinit var storage : FirebaseStorage


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminCreateAndUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        storage = FirebaseStorage.getInstance()

        viewModel = ViewModelProvider(this)[NotesViewModel::class.java]

        val token = SPref.get(this@Admin_CreateAndUpdateNoteActivity,SPref.token)

        binding.actionBar.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.gallery.setOnClickListener{
            openGallery()
        }

        val code = intent.getIntExtra("code",-1)

        if (code.equals(0))
        {
            binding.actionBar.toolbar.title = "Create Note"

            binding.submit.setOnClickListener{
                val title = binding.title.text.toString()
                val notesUrl = binding.notesUrl.text.toString()
                val subjectId = intent.getIntExtra("subjectId",-1)
                val userId = SPref.get(this@Admin_CreateAndUpdateNoteActivity,SPref.userId)

                if (valid(title,notesUrl,subjectId,userId,imageUri))
                {
                    CreateNote(token,title,notesUrl,subjectId,userId,imageUri)
                }
            }
        }

        if(code.equals(1))
        {
            binding.actionBar.toolbar.title = "Update Note"
            binding.submit.text = "Update"
            binding.title.setText(intent.getStringExtra("title"))
            binding.notesUrl.setText(intent.getStringExtra("notesUrl"))
            binding.imageLayout.visibility = View.GONE
            binding.label.visibility = View.GONE
            binding.submit.setOnClickListener{
                val title = binding.title.text.toString()
                val notesUrl = binding.notesUrl.text.toString()
                val subjectId = intent.getIntExtra("subjectId",-1)
                if(title.isEmpty())
                {
                    binding.title.error = "Title is required"
                    binding.title.requestFocus()
                    return@setOnClickListener
                }
                if(notesUrl.isEmpty()) {
                    binding.notesUrl.error = "Notes Url is required"
                    binding.notesUrl.requestFocus()
                    return@setOnClickListener
                }

                updateNote(token,title,notesUrl,subjectId)
            }
        }
    }

    private fun updateNote(token: String, title: String, notesUrl: String, subjectId: Int) {

        val noteId = intent.getIntExtra("noteId",-1)
        val imageUrl = intent.getStringExtra("imageUrl").toString()
        val userId = SPref.get(this@Admin_CreateAndUpdateNoteActivity,SPref.userId)

        val progress = CustomProgressDialog(this@Admin_CreateAndUpdateNoteActivity)
        progress.show()

        val model = UpdateNoteRequestModel(subjectId.toString(),title,notesUrl,userId,imageUrl)
        viewModel.observeUpdateNote().removeObservers(this@Admin_CreateAndUpdateNoteActivity)
        viewModel.observeError().removeObservers(this@Admin_CreateAndUpdateNoteActivity)

        viewModel.observeUpdateNote().observe(this@Admin_CreateAndUpdateNoteActivity) {
            Constant.success(this@Admin_CreateAndUpdateNoteActivity, it!!.MSG)
            progress.dismiss()
            finish()
        }

        viewModel.observeError().observe(this@Admin_CreateAndUpdateNoteActivity) {
            Constant.error(this@Admin_CreateAndUpdateNoteActivity, it)
            progress.dismiss()
        }

        viewModel.updateNote(token, model,noteId)

    }

    private fun CreateNote(token: String, title: String, notesUrl: String, subjectId: Int, userId: String, imageUri: Uri?) {
        val progress = CustomProgressDialog(this@Admin_CreateAndUpdateNoteActivity)
        progress.show()

        val storageReference = storage.reference.child("NotesPreviewImage").child(System.currentTimeMillis().toString()).putFile(imageUri!!)
        storageReference.addOnSuccessListener { taskSnapshot ->
            taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                val downloadUrl = uri.toString()

                val model = CreateNoteRequestModel(downloadUrl, subjectId.toString(), title, notesUrl, userId)
                viewModel.observeCreateNote().removeObservers(this@Admin_CreateAndUpdateNoteActivity)
                viewModel.observeError().removeObservers(this@Admin_CreateAndUpdateNoteActivity)

                viewModel.observeCreateNote().observe(this@Admin_CreateAndUpdateNoteActivity) {
                    Constant.success(this@Admin_CreateAndUpdateNoteActivity, it!!.MSG)
                    progress.dismiss()
                    finish()
                }

                viewModel.observeError().observe(this@Admin_CreateAndUpdateNoteActivity) {
                    Constant.error(this@Admin_CreateAndUpdateNoteActivity, it)
                    progress.dismiss()
                }

                viewModel.createNote(token, model)
            }.addOnFailureListener {
                Constant.error(this@Admin_CreateAndUpdateNoteActivity, "Failed to get download URL")
                progress.dismiss()
            }
        }.addOnFailureListener {
            Constant.error(this@Admin_CreateAndUpdateNoteActivity, "Something went wrong while uploading the image")
            progress.dismiss()
        }
    }


    private fun valid(title: String, notesUrl: String, subjectId: Int, userId: String, imageUri: Uri?): Boolean {

        if (title.isEmpty()) {
            binding.title.error = "Title is required"
            binding.title.requestFocus()
            return false
        }
        if (notesUrl.isEmpty()) {
            binding.notesUrl.error = "Notes Url is required"
            binding.notesUrl.requestFocus()
            return false
        }
        if (subjectId==-1)
        {
            Constant.error(this@Admin_CreateAndUpdateNoteActivity,"Please select a subject")
            return false
        }
        if (userId.isEmpty())
        {
            Constant.error(this@Admin_CreateAndUpdateNoteActivity,"Please select a user")
            return false
        }
        if (imageUri==null)
        {
            Constant.error(this@Admin_CreateAndUpdateNoteActivity,"Please select an image")
            return false
        }
        return true
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
            imageUri?.let {
                val imageName = getFileName(it)
                binding.fileName.text = imageName
            }
        }
    }


    private fun getFileName(uri: Uri): String {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor = contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val columnIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (columnIndex >= 0) {
                        result = it.getString(columnIndex)
                    }
                }
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result?.lastIndexOf('/')
            if (cut != null && cut != -1) {
                result = result?.substring(cut + 1)
            }
        }
        return result ?: "Unknown"
    }
}