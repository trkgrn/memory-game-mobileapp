package com.trkgrn.memorygame.ui.game

import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.trkgrn.memorygame.R
import com.trkgrn.memorygame.databinding.FragmentGameScreenBinding

class GameScreenFragment : Fragment() {

    private lateinit var shuffleButton: Button
//    lateinit var gameSettings: GameSettings
//    lateinit var mAdapter: MemoryCardAdapter
    private lateinit var binding: FragmentGameScreenBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentGameScreenBinding.inflate(layoutInflater)
        val view = binding.root
        val rootView = inflater.inflate(R.layout.fragment_game_screen, container, false)
        shuffleButton = binding.shuffleButton
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        var memoryGameLogic: MemoryGameLogic? = null
//        var memoryCardList = arrayListOf<MemoryCard>()
//        val bundle = arguments
//        if (bundle != null){
//            memoryCardList = bundle.getParcelableArrayList<MemoryCard>(getString(R.string.bundle_card_array))!!
//            gameSettings = bundle.getParcelable(getString(R.string.bundle_settings))!!
//            memoryGameLogic = MemoryGameLogic(memoryCardList, gameSettings, this)
//        }
//        mAdapter = MemoryCardAdapter(memoryCardList, context, memoryGameLogic!!)
//        binding.gridMainCards.adapter = mAdapter
//
//        shuffleButton.setOnClickListener {
//            memoryGameLogic.shuffleCards()
//        }
//
//        memoryGameLogic.startGame()
    }

    fun setGameFinished(bundle: Bundle){
        findNavController().navigate(R.id.finishedGameFragment, bundle)
    }

//    fun setScreenNotTouchable(){
//        val handler = Handler()
//        activity?.window?.setFlags(
//            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
//        );
//        handler.postDelayed(Runnable {
//            mAdapter.notifyDataSetChanged()
//            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//        }, gameSettings.speed.toLong())
//    }

    fun updatePointsView(nPoints: Int){
        binding.showPoints.text = nPoints.toString()
    }

    fun updateTimeView(minutes:Long, seconds:Long){
        binding.showChronometer.text = String.format("%d:%02d", minutes, seconds)
    }

    fun showBase64Image(base64: String, imageView: ImageView) {
        val imageBytes = Base64.decode(base64, Base64.DEFAULT)
        val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        imageView.setImageBitmap(image)
    }

}