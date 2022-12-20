package com.trkgrn.memorygame.ui.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.addCallback
import androidx.navigation.findNavController
import com.trkgrn.memorygame.MainActivity
import com.trkgrn.memorygame.R
import com.trkgrn.memorygame.databinding.FragmentMainLobbyBinding


class MainLobbyFragment : Fragment() {

    private lateinit var binding:FragmentMainLobbyBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentMainLobbyBinding.inflate(layoutInflater)
        setHasOptionsMenu(true)
        val view = binding.root
        val rootView = inflater.inflate(R.layout.fragment_main_lobby, container, false)
        binding.initButton.setOnClickListener{
            it.findNavController().navigate(R.id.loginFragment)
        }

        binding.registerButton.setOnClickListener{
            it.findNavController().navigate(R.id.registerFragment)
        }

        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
        }
        return view
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