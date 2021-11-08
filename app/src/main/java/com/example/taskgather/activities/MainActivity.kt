package com.example.taskgather.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.taskgather.databinding.ActivityMainBinding
import com.example.taskgather.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var auth: FirebaseAuth
    lateinit var user : UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        checkUserLoginOrNot()

        fetchUserDetails()

        binding.noteLayout.setOnClickListener {
            redirectToActivities(NoteActivity())
        }

        binding.imageLayout.setOnClickListener {
            redirectToActivities(ImageActivity())
        }

        binding.menuAddNotes.setOnClickListener {
            redirectToActivities(NoteActivity())
            binding.fabMenu.close(true)
        }

        binding.menuAddImages.setOnClickListener {
            redirectToActivities(ImageActivity())
            binding.fabMenu.close(true)
        }
    }

    private fun fetchUserDetails() {
        val userRef : DatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(auth.currentUser!!.uid)
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                user = snapshot.getValue(UserModel::class.java)!!
                binding.tvName.text = user.userName
                binding.tvEmail.text = user.userEmail
                binding.tvImageCount.text = user.totalImages.toString()
                binding.tvNoteCount.text = user.totalNotes.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun checkUserLoginOrNot() {
        if (auth.uid == null){
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }else{
            checkUserExists()
        }
    }

    private fun checkUserExists() {
        val userRef : DatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().uid.toString())
        //val ref = DatabaseLocations.getUserReference(FirebaseAuth.getInstance().uid.toString())
        userRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value == null){
                    redirectToActivities(LoginActivity())
                    Log.d("MainActivity", "User Not Exists")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun redirectToActivities(activity: AppCompatActivity) {
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
    }
}