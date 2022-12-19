package com.trkgrn.memorygame.ui.game

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ImageView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.trkgrn.memorygame.R
import com.trkgrn.memorygame.data.adapter.MemoryCardAdapter
import com.trkgrn.memorygame.data.model.MemoryCard
import com.trkgrn.memorygame.databinding.FragmentGameScreenBinding
import java.util.stream.IntStream

class GameScreenFragment : Fragment() {

    private lateinit var binding: FragmentGameScreenBinding
    private val viewModel: GameScreenViewModel by viewModels()

    private var gridNumColumns = 4
    private var playerMode = 1
    private var totalTime = 0L

    private var allCards = ArrayList<MemoryCard>()
    private var gameCards = ArrayList<MemoryCard>()

    private var curView: ImageView? = null

    private var selectedCardFirst: Int? = null
    private var selectedCardSecond: Int? = null
    private var selectedFirstView: ImageView? = null
    private var selectedSecondView: ImageView? = null

    private var isGameFinished = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentGameScreenBinding.inflate(layoutInflater)
        val view = binding.root

        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            println("Tıklandı")
        }

        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gridView = binding.gridMainCards

        viewModel.cardList.observe(viewLifecycleOwner) {
            allCards = it
            println("Card size:" + allCards.size)

            loadSettings()
            loadGameCards(gridView)
            startGame()

            gridView.setOnItemClickListener { adapterView, convertView, position, id ->

                var remainingTime = binding.showChronometer.text.toString().toInt()
                var lastTime = totalTime.toString().toInt() - remainingTime

                if (gameCards.get(position).isMatch || position == selectedCardFirst) {

                } else {
                    if (selectedCardFirst == null) { // ilk kart seçiliyorsa
                        selectedCardFirst = position
                        curView = convertView.findViewById<ImageView>(R.id.card_item)
                        selectedFirstView = curView
                        curView!!.setImageBitmap(gameCards.get(position).imageBitmap)
                        gameCards.get(position).isHidden = false
                    } else if (selectedCardFirst != null && selectedCardSecond == null) { // 2. kart seçiliyorsa
                        selectedCardSecond = position
                        curView = convertView.findViewById<ImageView>(R.id.card_item)
                        selectedSecondView = curView
                        curView!!.setImageBitmap(gameCards.get(position).imageBitmap)
                        gameCards.get(position).isHidden = false
                    }

                    if (selectedCardFirst != null && selectedCardSecond != null) { // 2 kart seçildiyse
                        val name1 = gameCards.get(selectedCardFirst!!).cardName
                        val name2 = gameCards.get(selectedCardSecond!!).cardName

                        if (name1.equals(name2)) { // Kartlar Eşleşiyorsa
                            var score = binding.score1.text.toString().toLong()
                            gameCards.get(selectedCardFirst!!).isMatch = true
                            gameCards.get(selectedCardSecond!!).isMatch = true
                            score += trueMatchPoint(
                                gameCards.get(selectedCardFirst!!),
                                remainingTime
                            )
                            binding.score1.text = score.toString()
                            selectedCardFirst = null
                            selectedCardSecond = null

                            var falseMatchCount: Int = 0
                            gameCards.forEach {
                                if (!it.isMatch)
                                    falseMatchCount++
                            }
                            if (falseMatchCount == 0) {
                                println("Cards:"+gameCards.size + " Falses:"+falseMatchCount)
                                onFinishGame()
                            }

                        } else { // Kartlar eşleşmiyorsa
                            object : CountDownTimer(300, 100) {
                                override fun onTick(p0: Long) {}
                                override fun onFinish() {
                                    var score = binding.score1.text.toString().toLong()
                                    var point = falseMatchPoint(
                                        gameCards.get(selectedCardFirst!!),
                                        gameCards.get(selectedCardSecond!!),
                                        lastTime
                                    )
                                    score = score - point
                                    binding.score1.text = score.toString()
                                    selectedFirstView!!.setImageResource(R.drawable.card_front_side)
                                    selectedSecondView!!.setImageResource(R.drawable.card_front_side)
                                    gameCards.get(selectedCardFirst!!).isHidden = true
                                    gameCards.get(selectedCardSecond!!).isHidden = true
                                    selectedCardFirst = null
                                    selectedCardSecond = null
                                }
                            }.start()

                        }
                    }
                }

            }

        }

    }

    fun loadSettings() {
        val bundle = arguments
        if (bundle != null) {
            gridNumColumns = bundle.get("gridSize") as Int
            playerMode = bundle.get("playerMode") as Int
        }
        if (playerMode == 1) {
            binding.showChronometer.text = "45"
            totalTime = 45L
            binding.score2.visibility = View.GONE
            binding.score2.visibility = View.GONE
        } else {
            binding.showChronometer.text = "60"
            totalTime = 60L
            binding.score2Textview.visibility = View.VISIBLE
            binding.score2.visibility = View.VISIBLE
        }
    }

    fun loadGameCards(gridView: GridView) {
        /*
    2x2 -> 2  -> 1 1 0 0
    4x4 -> 8  -> 2 2 2 2
    6x6 -> 18 -> 5 5 4 4
    * */
        var ravenclawList = allCards.filter { it.cardHomeName.equals("RAVENCLAW") }.shuffled()
        var gryffindorList = allCards.filter { it.cardHomeName.equals("GRYFFİNDOR") }.shuffled()
        var slytherinList = allCards.filter { it.cardHomeName.equals("SLYTHERİN") }.shuffled()
        var hufflepuffList = allCards.filter { it.cardHomeName.equals("HUFFLEPUFF") }.shuffled()
        var card1: MemoryCard? = null
        var card2: MemoryCard? = null
        var card3: MemoryCard? = null
        var card4: MemoryCard? = null

        if (gridNumColumns == 2) {
            card1 = ravenclawList.random()
            card2 = gryffindorList.random()
            IntStream.range(0, 2).forEach {
                gameCards.add(MemoryCard(card1!!))
                gameCards.add(MemoryCard(card2!!))
            }
        } else if (gridNumColumns == 4) {
            IntStream.range(0, 2).forEach {
                card1 = ravenclawList.get(it)
                card2 = gryffindorList.get(it)
                card3 = slytherinList.get(it)
                card4 = hufflepuffList.get(it)
                IntStream.range(0, 2).forEach {
                    gameCards.add(MemoryCard(card1!!))
                    gameCards.add(MemoryCard(card2!!))
                    gameCards.add(MemoryCard(card3!!))
                    gameCards.add(MemoryCard(card4!!))
                }
            }

        } else if (gridNumColumns == 6) {
            IntStream.range(0, 5).forEach {
                card1 = ravenclawList.get(it)
                card2 = gryffindorList.get(it)
                if (it != 4) {
                    card3 = slytherinList.get(it)
                    card4 = hufflepuffList.get(it)
                }
                IntStream.range(0, 2).forEach {iter->
                    gameCards.add(MemoryCard(card1!!))
                    gameCards.add(MemoryCard(card2!!))
                    if (it != 4) {
                        gameCards.add(MemoryCard(card3!!))
                        gameCards.add(MemoryCard(card4!!))
                    }

                }
            }
        }

        gameCards.shuffle()
        gridView.numColumns = gridNumColumns
        val memoryCardAdapter = MemoryCardAdapter(this@GameScreenFragment, gameCards)
        gridView.adapter = memoryCardAdapter
    }

    fun startGame() {
        object : CountDownTimer(1000 * totalTime, 1000) {
            override fun onTick(p0: Long) {
                if (isGameFinished)
                    cancel()

                var remainingTime = binding.showChronometer.text.toString().toInt()
                remainingTime--
                binding.showChronometer.text = remainingTime.toString()
            }

            override fun onFinish() {
                onFinishGame()
            }
        }.start()
    }

    fun onFinishGame() {
        isGameFinished = true
        val dialogBuilder = AlertDialog.Builder(context)
        val score = binding.score1.text.toString().toInt()

        dialogBuilder.setMessage("Skor: " + score)
            .setCancelable(false)
            .setPositiveButton("Ayarlara Dön", DialogInterface.OnClickListener { dialog, id ->
                arguments?.putParcelableArrayList("cardList",allCards)
                println("HashCode-GS:"+allCards.hashCode())
                findNavController().navigate(R.id.gameSettings,arguments)
            })
            .setNegativeButton("Yeniden Başla", DialogInterface.OnClickListener { dialog, id ->
                arguments?.putParcelableArrayList("cardList",allCards)
                findNavController().navigate(R.id.memoryGame, arguments)
            })

        val alert = dialogBuilder.create()
        alert.setTitle("Oyun Bitti")
        alert.show()
    }

    fun trueMatchPoint(card: MemoryCard, remainingTime: Int): Long {
        return (2 * card.cardPoint * card.cardHomePoint * remainingTime) / 10
    }

    fun falseMatchPoint(card1: MemoryCard, card2: MemoryCard, lastTime: Int): Long {
        var score: Long = 0
        var point1 = card1.cardPoint
        var point2 = card2.cardPoint
        var hPoint1 = card1.cardHomePoint
        var hPoint2 = card2.cardHomePoint

        if (card1.cardHomeName.equals(card2.cardHomeName)) { // Aynı evden ise
            score = ((point1 + point2) / hPoint1) * (lastTime / 10)
        } else {
            score = (hPoint1 * hPoint2 * ((point1 + point2) / 2)) * (lastTime / 10)
        }

        return score
    }


}

