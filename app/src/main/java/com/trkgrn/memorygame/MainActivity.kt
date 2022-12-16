package com.trkgrn.memorygame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.trkgrn.memorygame.databinding.ActivityMainBinding
import com.trkgrn.memorygame.ui.auth.LoginActivity

class MainActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    private lateinit var binding:ActivityMainBinding
    private lateinit var firestore: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        auth = Firebase.auth
        firestore = Firebase.firestore
        setContentView(view)
        val navController = findNavController(R.id.nav_host_fragment)
        navController.navigate(R.id.loginFragment)
//        val intent = Intent(this@MainActivity,LoginActivity::class.java)
//        startActivity(intent)
//        finish()

    }
}