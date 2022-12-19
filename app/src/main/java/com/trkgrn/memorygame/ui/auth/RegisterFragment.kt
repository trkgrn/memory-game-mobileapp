package com.trkgrn.memorygame.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.trkgrn.memorygame.R
import com.trkgrn.memorygame.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = Firebase.auth
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setHasOptionsMenu(true)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerButton.setOnClickListener {
            signUp()
        }
    }


    fun signUp() {

        val email = binding.registerEmailAddress.text.toString()
        val password = binding.registerPassword.text.toString()
        val confirmPassword = binding.registerConfirmPassword.text.toString()

        if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            Toast.makeText(context, "Tüm gerekli alanları doldurunuz! ", Toast.LENGTH_LONG).show()
        } else if (!password.equals(confirmPassword)) {
            Toast.makeText(context, "Şifreler eşleşmiyor! ", Toast.LENGTH_LONG).show()
        } else {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    findNavController().navigate(R.id.gameSettings)
                }.addOnFailureListener {
                    Toast.makeText(context, it.localizedMessage, Toast.LENGTH_LONG).show()
                }
            println(email)
            println(password)
        }

    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        var myMenu = menu!!
        myMenu.findItem(R.id.menuLogin).isVisible = true
        myMenu.findItem(R.id.menuRegister).isVisible = true
        myMenu.findItem(R.id.menuChangePassword).isVisible = false
        myMenu.findItem(R.id.menuLogout).isVisible = false

    }


}