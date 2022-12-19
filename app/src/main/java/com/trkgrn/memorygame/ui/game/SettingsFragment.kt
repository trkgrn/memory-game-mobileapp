package com.trkgrn.memorygame.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.trkgrn.memorygame.R
import com.trkgrn.memorygame.data.model.MemoryCard
import com.trkgrn.memorygame.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    lateinit var radioGroupGameMode: RadioGroup
    lateinit var radioGroupPlayerMode: RadioGroup
    lateinit var startGameButton: Button

    companion object{
        var difficulties: BiMap<String, Int> = HashBiMap.create()
        const val EASY = "Kolay"
        const val MID = "Orta"
        const val HARD = "Zor"

        var playerModes : BiMap<String, Int> = HashBiMap.create()
        const val SINGLE_PLAYER = "Tek Oyunculu"
        const val TWO_PLAYER = "İki Oyunculu"
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        val view = binding.root
        radioGroupGameMode = binding.gridSizeSpinner
        radioGroupPlayerMode = binding.difficultyRadiogroup
        startGameButton = binding.startGameButton
        setHasOptionsMenu(true);
        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            println("Çıkışa götür")
        }

        initializeHashMaps()
        return view
    }

    private fun initializeHashMaps() {
        difficulties[EASY] = 2
        difficulties[MID] = 4
        difficulties[HARD] = 6

        playerModes[SINGLE_PLAYER] = 1
        playerModes[TWO_PLAYER] = 2
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startGameButton.setOnClickListener {

            val selectedItem = radioGroupGameMode.checkedRadioButtonId
            val checkedRadioButtonId = radioGroupPlayerMode.checkedRadioButtonId
            val gridSize = getDifficultyFromSelectedId(selectedItem)!!
            val playerMode = getPlayerModeFromSelectedRadioButtonId(checkedRadioButtonId)!!

            println("GridSize: " + gridSize.toString())
            println("pMode: "+ playerMode.toString())

            var bundle:Bundle = Bundle()
            bundle.putInt("gridSize",gridSize)
            bundle.putInt("playerMode",playerMode)

            findNavController().navigate(R.id.memoryGame,bundle)

        }

    }

    private fun getDifficultyFromSelectedId(selectedItem:Int) = when (selectedItem){

        R.id.two_radiobutton -> difficulties[EASY]
        R.id.four_radiobutton -> difficulties[MID]
        R.id.six_radiobutton-> difficulties[HARD]
        else -> difficulties[MID]
    }

    private fun getPlayerModeFromSelectedRadioButtonId(selectedDifficulty:Int) = when (selectedDifficulty){
        R.id.onePlayer_radiobutton -> playerModes[SINGLE_PLAYER]
        R.id.twoPlayer_radiobutton -> playerModes[TWO_PLAYER]
        else -> playerModes[SINGLE_PLAYER]
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        var myMenu = menu!!
        myMenu.findItem(R.id.menuLogin).isVisible = false
        myMenu.findItem(R.id.menuRegister).isVisible = false
        myMenu.findItem(R.id.menuChangePassword).isVisible = true
        myMenu.findItem(R.id.menuLogout).isVisible = true
    }

}