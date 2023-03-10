package com.trkgrn.memorygame.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.view.get
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.trkgrn.memorygame.MainActivity
import com.trkgrn.memorygame.R
import com.trkgrn.memorygame.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private lateinit var binding:FragmentLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = Firebase.auth
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            login()
        }

        binding.signupButton.setOnClickListener {
            register()
        }

        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.mainLobbyFragment)
        }

    }


    fun login() {
        val mail = binding.loginEmailAddress.text.toString()
        val password = binding.loginPassword.text.toString()


        if (mail.isBlank() || password.isBlank())
            Toast.makeText(context,"E-mail ve ┼čifrenizi giriniz! ",Toast.LENGTH_LONG).show()
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


    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        var myMenu = menu!!
        myMenu.findItem(R.id.menuLogin).isVisible = true
        myMenu.findItem(R.id.menuRegister).isVisible = true
        myMenu.findItem(R.id.menuChangePassword).isVisible = false
        myMenu.findItem(R.id.menuLogout).isVisible = false
        if (MainActivity.isMuted)
            myMenu.findItem(R.id.audioMute).setIcon(R.drawable.ic_baseline_volume_off_24)
        else
            myMenu.findItem(R.id.audioMute).setIcon(R.drawable.ic_baseline_volume_up_24)

    }

}