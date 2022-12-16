package com.trkgrn.memorygame.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.trkgrn.memorygame.databinding.ActivityLoginBinding
import com.trkgrn.memorygame.ui.game.MainLobbyActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = Firebase.auth
    }

    fun signIn(view: View) {
        val mail = binding.editTextTextEmailAddress.text.toString()
        val password = binding.editTextTextPassword.text.toString()

        if (mail.isBlank() || password.isBlank())
            Toast.makeText(this, "E-mail ve şifrenizi giriniz! ", Toast.LENGTH_LONG).show()
        else {
            auth.signInWithEmailAndPassword(mail, password)
                .addOnSuccessListener {
                    val intent = Intent(this@LoginActivity, MainLobbyActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this@LoginActivity, it.localizedMessage, Toast.LENGTH_LONG)
                        .show()
                }
        }
    }

    fun signUp(view: View) {
        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

}