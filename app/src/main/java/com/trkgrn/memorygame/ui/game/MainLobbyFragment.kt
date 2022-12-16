package com.trkgrn.memorygame.ui.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.trkgrn.memorygame.R
import com.trkgrn.memorygame.databinding.FragmentMainLobbyBinding


class MainLobbyFragment : Fragment() {

    private lateinit var binding:FragmentMainLobbyBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentMainLobbyBinding.inflate(layoutInflater)
        val view = binding.root
        val rootView = inflater.inflate(R.layout.fragment_main_lobby, container, false)
        binding.initButton.setOnClickListener{
            it.findNavController().navigate(R.id.loginFragment)
        }

        binding.registerButton.setOnClickListener{
            it.findNavController().navigate(R.id.registerFragment)
        }
        return view
    }
}