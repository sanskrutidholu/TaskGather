package com.example.taskgather.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.taskgather.databinding.ActivityRegistrationBinding
import com.example.taskgather.utils.FirebaseOperations
import com.google.firebase.auth.FirebaseAuth

class RegistrationActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegistrationBinding
    lateinit var auth: FirebaseAuth

    lateinit var userName: String
    lateinit var userEmail: String
    lateinit var userPassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        actionBar!!.title = "Registration"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        auth = FirebaseAuth.getInstance()

        binding.haveAccount.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.registerBtn.setOnClickListener {
            userName = binding.nameEt.text.toString().trim()
            userEmail = binding.emailEt.text.toString().trim()
            userPassword = binding.passwordEt.text.toString().trim()
            registerUser(userEmail,userPassword)
        }
    }

    fun registerUser(email:String,password:String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    FirebaseOperations().registerUser(auth.currentUser!!.uid,userName,email,0,0)
                    Toast.makeText(this, "User registered...", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    //display some message here
                    Toast.makeText(this, "Registration Error", Toast.LENGTH_LONG).show()
                }

            }

    }
}