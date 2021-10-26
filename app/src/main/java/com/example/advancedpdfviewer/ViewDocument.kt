package com.example.advancedpdfviewer

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.advancedpdfviewer.databinding.ActivitySplashBinding
import com.example.advancedpdfviewer.databinding.ActivityViewDocumentBinding

class ViewDocument : AppCompatActivity() {
    private lateinit var binding: ActivityViewDocumentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewDocumentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val selectedPdf:Uri = Uri.parse(intent.getStringExtra("FileUri"))
        binding.apply {
            pdfView.fromUri(selectedPdf).load()
        }
    }
}