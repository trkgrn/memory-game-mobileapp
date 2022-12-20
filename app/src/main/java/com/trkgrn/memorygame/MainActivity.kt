package com.trkgrn.memorygame

import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.trkgrn.memorygame.databinding.ActivityMainBinding
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    companion object {
        var isMuted = false
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    private var user: FirebaseUser? = null
    private var isUserLogged = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        auth = Firebase.auth
        setContentView(view)


        val currentUser = auth.currentUser
        user = currentUser
        if (currentUser != null) {
            isUserLogged = true
            findNavController(R.id.nav_host_fragment).navigate(R.id.gameSettings)

        } else {
            isUserLogged = false
            findNavController(R.id.nav_host_fragment).navigate(R.id.mainLobbyFragment)

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var itemId = item.itemId
        if (itemId.equals(R.id.menuLogout)) {
            auth.signOut()
            user = null
            isUserLogged = false
            findNavController(R.id.nav_host_fragment).navigate(R.id.mainLobbyFragment)
        } else if (itemId.equals(R.id.menuLogin)) {
            findNavController(R.id.nav_host_fragment).navigate(R.id.loginFragment)
        } else if (itemId.equals(R.id.menuRegister)) {
            findNavController(R.id.nav_host_fragment).navigate(R.id.registerFragment)
        } else if (itemId.equals(R.id.menuChangePassword)) {
            findNavController(R.id.nav_host_fragment).navigate(R.id.changePasswordFragment)
        }else if (itemId.equals(R.id.audioMute)){
            isMuted = isMuted.not()
            var audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
            audioManager.setStreamMute(AudioManager.STREAM_NOTIFICATION,isMuted)
            audioManager.setStreamMute(AudioManager.STREAM_MUSIC,isMuted)

            if (isMuted)
                item.setIcon(R.drawable.ic_baseline_volume_off_24)
            else
                item.setIcon(R.drawable.ic_baseline_volume_up_24)
        }
        return super.onOptionsItemSelected(item)
    }


}