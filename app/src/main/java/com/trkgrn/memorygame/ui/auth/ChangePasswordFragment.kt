package com.trkgrn.memorygame.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.trkgrn.memorygame.MainActivity
import com.trkgrn.memorygame.R
import com.trkgrn.memorygame.databinding.FragmentChangePasswordBinding


class ChangePasswordFragment : Fragment() {

    private lateinit var binding: FragmentChangePasswordBinding
    private lateinit var auth: FirebaseAuth
    private var currentUser:FirebaseUser? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangePasswordBinding.inflate(layoutInflater)
        setHasOptionsMenu(true)
        println("Create")
        auth = Firebase.auth
        val user = auth.currentUser
        if (user!=null)
            currentUser = user

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.changePasswordButton.setOnClickListener {
            changePassword()
        }

        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.gameSettings)
        }

        println("Created")

    }

    fun changePassword(){
        val currentPassword = binding.currentPassword.text.toString()
        val newPassword = binding.newPassword.text.toString()
        val newConfirmPassword = binding.newConfirmPassword.text.toString()

        if (currentPassword.isBlank() || newPassword.isBlank() || newConfirmPassword.isBlank()) {
            Toast.makeText(context, "T??m gerekli alanlar?? doldurunuz! ", Toast.LENGTH_LONG).show()
        }else if (!newPassword.equals(newConfirmPassword)) {
            Toast.makeText(context, "??ifreler e??le??miyor! ", Toast.LENGTH_LONG).show()
        }else{
            val credential = EmailAuthProvider.getCredential(currentUser!!.email!!,currentPassword)

            currentUser!!.reauthenticate(credential).addOnSuccessListener {
                currentUser!!.updatePassword(newPassword).addOnSuccessListener {
                    Toast.makeText(context, "??ifre ba??ar??yla de??i??tirildi! ", Toast.LENGTH_LONG).show()
                }.addOnFailureListener {
                    Toast.makeText(context, it.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener {
                println("HATA: "+it.localizedMessage)
                Toast.makeText(context, "Mevcut ??ifreyi yanl???? girdiniz! ", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        var myMenu = menu!!
        myMenu.findItem(R.id.menuLogin).isVisible = false
        myMenu.findItem(R.id.menuRegister).isVisible = false
        myMenu.findItem(R.id.menuChangePassword).isVisible = true
        myMenu.findItem(R.id.menuLogout).isVisible = true
        if (MainActivity.isMuted)
            myMenu.findItem(R.id.audioMute).setIcon(R.drawable.ic_baseline_volume_off_24)
        else
            myMenu.findItem(R.id.audioMute).setIcon(R.drawable.ic_baseline_volume_up_24)

    }



}