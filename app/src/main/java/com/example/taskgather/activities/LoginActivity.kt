package com.example.taskgather.activities

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.taskgather.databinding.ActivityLoginBinding
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        actionBar!!.title = "Login"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        auth = FirebaseAuth.getInstance()

        binding.notHaveAccount.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }

        binding.forget.setOnClickListener {
            showRecoverPasswordDialog()
        }

        binding.loginBtn.setOnClickListener {
            val email: String = binding.emailEt.text.toString().trim()
            val password: String = binding.passwordEt.text.toString().trim()
            loginUser(email, password)
        }
    }

    private fun showRecoverPasswordDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Recover Password")
        val linearLayout = LinearLayout(this)
        val emailET = EditText(this)
        emailET.hint = "Enter Email "
        emailET.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        emailET.minEms = 16
        linearLayout.addView(emailET)
        linearLayout.setPadding(10, 10, 10, 10)
        builder.setView(linearLayout)
        builder.setPositiveButton("Recover"
        ) { dialogInterface, i ->
            val email = emailET.text.toString().trim { it <= ' ' }
            beginRecovery(email)
        }
        builder.setNegativeButton("Cancel"
        ) { dialogInterface, i -> dialogInterface.dismiss() }
        builder.create().show()
    }

    private fun beginRecovery(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Email Sent", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed...", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener(OnFailureListener { e ->
                Toast.makeText(this, "" + e.message, Toast.LENGTH_SHORT).show()
            })
    }

    fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this
            ) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener(OnFailureListener { e ->
                Toast.makeText(this, "" + e.message, Toast.LENGTH_SHORT).show()
            })
    }
}