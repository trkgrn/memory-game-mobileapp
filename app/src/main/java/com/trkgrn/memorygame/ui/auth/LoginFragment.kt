package com.trkgrn.memorygame.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.trkgrn.memorygame.R
import com.trkgrn.memorygame.data.firestore.FireStoreHelper
import com.trkgrn.memorygame.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private lateinit var binding:FragmentLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = Firebase.auth
        binding = FragmentLoginBinding.inflate(inflater,container,false)

        currentUser = auth.currentUser!!

        if (currentUser!=null)
            findNavController().navigate(R.id.gameSettings)

        return binding.getRoot();
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            login()
        }

        binding.signupButton.setOnClickListener {
            register()
        }


    }


    fun login() {
        val mail = binding.loginEmailAddress.text.toString()
        val password = binding.loginPassword.text.toString()


        if (mail.isBlank() || password.isBlank())
            Toast.makeText(context,"E-mail ve şifrenizi giriniz! ",Toast.LENGTH_LONG).show()
        else {
            auth.signInWithEmailAndPassword(mail, password)
                .addOnSuccessListener {
                    findNavController().navigate(R.id.gameSettings)
                }
                .addOnFailureListener {
                    Toast.makeText(context, it.localizedMessage, Toast.LENGTH_LONG).show()
                }
        }
    }

    fun register(){
        findNavController().navigate(R.id.registerFragment)
    }


}