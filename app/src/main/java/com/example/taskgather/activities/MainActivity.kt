package com.example.taskgather.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.taskgather.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.menuAddNotes.setOnClickListener {
            val intent = Intent(this, NoteActivity::class.java)
            startActivity(intent)
            binding.fabMenu.close(true)
        }

        binding.menuAddImages.setOnClickListener {
            val intent = Intent(this, ImageActivity::class.java)
            startActivity(intent)
            binding.fabMenu.close(true)
        }
    }
}