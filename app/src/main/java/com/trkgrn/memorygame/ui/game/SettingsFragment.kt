package com.trkgrn.memorygame.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.trkgrn.memorygame.R
import com.trkgrn.memorygame.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    lateinit var radioGroupGameMode: RadioGroup
    lateinit var radioGroupPlayerMode: RadioGroup
    lateinit var startGameButton: Button
    lateinit var firestore:FirebaseFirestore

    companion object{
        var gridSizes: BiMap<String, Int> = HashBiMap.create()
        const val SMALL_GRID = "2*2"
        const val MID_GRID = "4*4"
        const val BIG_GRID = "6*6"

        var playerModes : BiMap<String, Int> = HashBiMap.create()
        const val SINGLE_PLAYER = "Tek Oyunculu"
        const val TWO_PLAYER = "İki Oyunculu"
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        firestore = Firebase.firestore
        val view = binding.root
        radioGroupGameMode = binding.gridSizeSpinner
        radioGroupPlayerMode = binding.difficultyRadiogroup
        startGameButton = binding.startGameButton

        initializeHashMaps()
        return view
    }

    private fun initializeHashMaps() {
        gridSizes[SMALL_GRID] = 2
        gridSizes[MID_GRID] = 4
        gridSizes[BIG_GRID] = 6

        playerModes[SINGLE_PLAYER] = 1
        playerModes[TWO_PLAYER] = 2
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startGameButton.setOnClickListener {

            val selectedItem = radioGroupGameMode.checkedRadioButtonId
            val checkedRadioButtonId = radioGroupPlayerMode.checkedRadioButtonId
            val gridSize = getGridSizeFromSelectedId(selectedItem)!!
            val playerMode = getPlayerModeFromSelectedRadioButtonId(checkedRadioButtonId)!!

            println("GridSize: " + gridSize.toString())
            println("pMode: "+ playerMode.toString())

            var bundle:Bundle = Bundle()
            bundle.putInt("gridSize",gridSize)
            bundle.putInt("playerMode",playerMode)

            findNavController().navigate(R.id.memoryGame,bundle)



        }

    }

    private fun getGridSizeFromSelectedId(selectedItem:Int) = when (selectedItem){

        R.id.two_radiobutton -> gridSizes[SMALL_GRID]
        R.id.four_radiobutton -> gridSizes[MID_GRID]
        R.id.six_radiobutton-> gridSizes[BIG_GRID]
        else -> gridSizes[MID_GRID]
    }

    private fun getPlayerModeFromSelectedRadioButtonId(selectedDifficulty:Int) = when (selectedDifficulty){
        R.id.onePlayer_radiobutton -> playerModes[SINGLE_PLAYER]
        R.id.twoPlayer_radiobutton -> playerModes[TWO_PLAYER]
        else -> playerModes[SINGLE_PLAYER]
    }

}