package com.trkgrn.memorygame.ui.game

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.trkgrn.memorygame.R
import com.trkgrn.memorygame.data.firestore.FireStoreHelper
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

        var speedValues : BiMap<String, Double> = HashBiMap.create()
        const val LOW_SPEED = "low"
        const val MID_SPEED = "middle"
        const val FAST_SPEED = "fast"
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        firestore = Firebase.firestore
        val view = binding.root
        val rootView = inflater.inflate(R.layout.fragment_settings, container, false)
        radioGroupGameMode = binding.gridSizeSpinner
        radioGroupPlayerMode = binding.difficultyRadiogroup
        startGameButton = binding.startGameButton

        initializeHashMaps()
        return view
    }

    private fun initializeHashMaps() {
        gridSizes[SMALL_GRID] = 2
        gridSizes[MID_GRID] = 8
        gridSizes[BIG_GRID] = 18

        speedValues[LOW_SPEED] = 2000.0
        speedValues[MID_SPEED] = 1000.0
        speedValues[FAST_SPEED] = 500.0
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startGameButton.setOnClickListener {

            var list = FireStoreHelper.getInstance().getAllCards()
            println(list!!.size)
            Toast.makeText(context,"",Toast.LENGTH_LONG).show()


        }

//        startGameButton.setOnClickListener{
//            val selectedItem = radioGroupGameMode.checkedRadioButtonId
//            val checkedRadioButtonId = radioGroupPlayerMode.checkedRadioButtonId
//            val gridSize = getGridSizeFromSelectedId(selectedItem)!!
//            val jsonReader = JSONReader(context!!, gridSize)
//            val speed = getSpeedFromSelectedRadioButtonId(checkedRadioButtonId)!!
//
//            val memoryCardList = jsonReader.memoryCardList
//            if (memoryCardList.isNullOrEmpty()){
//                val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context!!)
//                    .setPositiveButton("ok", DialogInterface.OnClickListener { dialog, _ ->
//                        dialog.dismiss()
//                        findNavController().navigate(R.id.mainScreen)
//                    })
//                    .setTitle("The file " + jsonReader.FILE_NAME + " could not be read due to an exception")
//                alertDialogBuilder.create().show()
//            }
//            else {
//                val bundle = Bundle()
//                val settings = GameSettings(speed.toInt(), gridSize)
//                bundle.putParcelable(getString(R.string.bundle_settings), settings)
//                bundle.putParcelableArrayList(getString(R.string.bundle_card_array), jsonReader.memoryCardList)
//
//                findNavController().navigate(R.id.memoryGame, bundle)
//            }
//        }
    }

    private fun getGridSizeFromSelectedId(selectedItem:Int) = when (selectedItem){

        R.id.two_radiobutton -> gridSizes[SMALL_GRID]
        R.id.four_radiobutton -> gridSizes[MID_GRID]
        R.id.six_radiobutton-> gridSizes[BIG_GRID]
        else -> gridSizes[MID_GRID]
    }

    private fun getSpeedFromSelectedRadioButtonId(selectedDifficulty:Int) = when (selectedDifficulty){
        R.id.onePlayer_radiobutton -> speedValues[LOW_SPEED]
        R.id.twoPlayer_radiobutton -> speedValues[MID_SPEED]
        else -> speedValues[MID_SPEED]
    }

}