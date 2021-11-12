package com.example.taskgather.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import com.example.taskgather.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        auth = FirebaseAuth.getInstance()

        Handler().postDelayed({
            val intent = Intent(this,MainActivity()::class.java)
            startActivity(intent)
            finish()
        },3000)
    }

}