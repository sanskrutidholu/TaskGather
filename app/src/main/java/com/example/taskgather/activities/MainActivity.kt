package com.example.taskgather.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.taskgather.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (FirebaseAuth.getInstance().uid == null){
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }else{
            checkUserExists()
        }

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

    private fun checkUserExists() {
        val userRef : DatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().uid.toString())
        //val ref = DatabaseLocations.getUserReference(FirebaseAuth.getInstance().uid.toString())
        userRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value == null){
                    redirectToRegisterNewUser()
                    Log.d("MainActivity", "User Not Exists")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun redirectToRegisterNewUser() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}