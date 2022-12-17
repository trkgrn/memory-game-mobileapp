package com.trkgrn.memorygame.ui.game

import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.trkgrn.memorygame.R
import com.trkgrn.memorygame.data.adapter.MemoryCardAdapter
import com.trkgrn.memorygame.data.model.MemoryCard
import com.trkgrn.memorygame.databinding.FragmentGameScreenBinding
import java.util.stream.Collector
import java.util.stream.IntStream
import kotlin.streams.toList

class GameScreenFragment : Fragment() {

    private lateinit var shuffleButton: Button

    //    lateinit var gameSettings: GameSettings
//    lateinit var mAdapter: MemoryCardAdapter
    private lateinit var binding: FragmentGameScreenBinding
    private lateinit var firestore: FirebaseFirestore

    private var gridNumColumns = 4

    private val viewModel: GameScreenViewModel by viewModels()


    private var allCards = ArrayList<MemoryCard>()
    private var currentPos = -1
    private var curView: ImageView? = null
    private var openedCardCount = 0

    private var selectedCardFirst: Int? = null
    private var selectedCardSecond: Int? = null

    private var selectedFirstView: ImageView? = null
    private var selectedSecondView: ImageView? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentGameScreenBinding.inflate(layoutInflater)
        val view = binding.root
        firestore = Firebase.firestore
        shuffleButton = binding.shuffleButton


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.cardList.observe(viewLifecycleOwner) {
            allCards = it

//            var splitCards = allCards.subList(0,18).toList() as ArrayList<MemoryCard>
            var scale = 4
            var size = scale * scale / 2
            var splitCards = ArrayList<MemoryCard>()
            IntStream.range(0, size).forEach {
                var card = allCards.get(it)
                var card1 = MemoryCard(
                    card.cardName,
                    card.cardHomeName,
                    card.cardImgBase64,
                    card.cardPoint,
                    card.imageBitmap,
                    card.isHidden
                )
                var card2 = MemoryCard(
                    card.cardName,
                    card.cardHomeName,
                    card.cardImgBase64,
                    card.cardPoint,
                    card.imageBitmap,
                    card.isHidden
                )

                splitCards.add(card1)
                splitCards.add(card2)
            }
            splitCards.shuffle()

            val gridView = binding.gridMainCards
            gridView.numColumns = scale


            println("Card size:" + allCards.size)

            val memoryCardAdapter = MemoryCardAdapter(this@GameScreenFragment, splitCards)

            gridView.adapter = memoryCardAdapter



            gridView.setOnItemClickListener { adapterView, convertView, position, id ->

                if (splitCards.get(position).isMatch) {

                } else {
                    if (selectedCardFirst == null) {
                        selectedCardFirst = position
                        curView = convertView.findViewById<ImageView>(R.id.card_item)
                        selectedFirstView = curView
                        curView!!.setImageBitmap(splitCards.get(position).imageBitmap)
                        splitCards.get(position).isHidden = false
                    } else if (selectedCardFirst != null && selectedCardSecond == null) {
                        selectedCardSecond = position
                        curView = convertView.findViewById<ImageView>(R.id.card_item)
                        selectedSecondView = curView
                        curView!!.setImageBitmap(splitCards.get(position).imageBitmap)
                        splitCards.get(position).isHidden = false
                    }

                    if (selectedCardFirst != null && selectedCardSecond != null) {
                        val name1 = splitCards.get(selectedCardFirst!!).cardName
                        val name2 = splitCards.get(selectedCardSecond!!).cardName

                        if (name1.equals(name2)) {
                            var score = binding.showPoints.text.toString().toInt()
                            splitCards.get(selectedCardFirst!!).isMatch = true
                            splitCards.get(selectedCardSecond!!).isMatch = true
                            score++
                            binding.showPoints.text = score.toString()
                            selectedCardFirst = null
                            selectedCardSecond = null
                        } else {
                            object : CountDownTimer(500, 100) {
                                override fun onTick(p0: Long) {}
                                override fun onFinish() {
                                    selectedFirstView!!.setImageResource(R.drawable.card_front_side)
                                    selectedSecondView!!.setImageResource(R.drawable.card_front_side)
                                    splitCards.get(selectedCardFirst!!).isHidden = true
                                    splitCards.get(selectedCardSecond!!).isHidden = true
                                    selectedCardFirst = null
                                    selectedCardSecond = null
                                }
                            }.start()

                        }



                    }


                }


//                if (splitCards.get(pos).isHidden) {
//                    curView = convertView.findViewById<ImageView>(R.id.card_item)
//                    curView!!.setImageBitmap(splitCards.get(pos).imageBitmap)
//                    splitCards.get(pos).isHidden = false
//                } else {
//                    curView = convertView.findViewById<ImageView>(R.id.card_item)
//                    curView!!.setImageResource(R.drawable.card_front_side)
//                    splitCards.get(pos).isHidden = true
//                }

            }

            shuffleButton.setOnClickListener {
                splitCards.shuffle()
                val adapter = MemoryCardAdapter(this@GameScreenFragment, splitCards)
                gridView.adapter = adapter
            }

        }


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


    fun setGameFinished(bundle: Bundle) {
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

    fun updatePointsView(nPoints: Int) {
        binding.showPoints.text = nPoints.toString()
    }

    fun updateTimeView(minutes: Long, seconds: Long) {
        binding.showChronometer.text = String.format("%d:%02d", minutes, seconds)
    }

    fun showBase64Image(base64: String, imageView: ImageView) {
        val imageBytes = Base64.decode(base64, Base64.DEFAULT)
        val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        imageView.setImageBitmap(image)
    }

}