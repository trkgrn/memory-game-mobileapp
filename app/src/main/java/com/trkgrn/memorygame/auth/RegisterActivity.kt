package com.trkgrn.memorygame.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.trkgrn.memorygame.databinding.ActivityRegisterBinding
import com.trkgrn.memorygame.game.MainLobbyActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = Firebase.auth
    }

    fun signUp(view: View) {

        val email = binding.mailAddressText.text.toString()
        val password = binding.passwordText.text.toString()
        val confirmPassword = binding.confirmPasswordText.text.toString()

        if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            Toast.makeText(this, "Tüm gerekli alanları doldurunuz! ", Toast.LENGTH_LONG).show()
        } else if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Şifreler eşleşmiyor! ", Toast.LENGTH_LONG).show()
        } else {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    val intent = Intent(this@RegisterActivity, MainLobbyActivity::class.java)
                    startActivity(intent)
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(this@RegisterActivity, it.localizedMessage, Toast.LENGTH_LONG)
                        .show()
                }
            println(email)
            println(password)
        }

    }

    fun signIn(view: View) {
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}