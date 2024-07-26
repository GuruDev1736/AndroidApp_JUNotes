package com.gurudev.junotes.User.Activities.Notes

import android.os.Bundle
import android.util.Log
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gurudev.junotes.R
import com.gurudev.junotes.databinding.ActivityShowNoteBinding

class ShowNote : AppCompatActivity() {
    private lateinit var binding : ActivityShowNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val intent = intent
        val url = intent.getStringExtra("link").toString()
        val title = intent.getStringExtra("title").toString()

        binding.actionBar.toolbar.title = title
        binding.actionBar.toolbar.setNavigationOnClickListener {
            this@ShowNote.onBackPressedDispatcher.onBackPressed()
        }


        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient = WebViewClient()
        binding. webView.loadUrl(url)
        disableFileDownload()
    }

    private fun disableFileDownload() {
        val script = """
            javascript:(function() {
                var elements = document.getElementsByClassName('ndfHFb-c4YZDc-Wrql6b');
                for (var i = 0; i < elements.length; i++) {
                    elements[i].style.display = 'none';
                }
            })();
        """.trimIndent()

        // Execute JavaScript to disable download button
        binding.webView.evaluateJavascript(script, null)
    }
}