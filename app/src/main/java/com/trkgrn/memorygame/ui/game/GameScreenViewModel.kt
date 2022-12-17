package com.trkgrn.memorygame.ui.game

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.trkgrn.memorygame.data.firestore.FireStoreHelper
import com.trkgrn.memorygame.data.model.MemoryCard

class GameScreenViewModel : ViewModel(){

    val cardList = MutableLiveData<ArrayList<MemoryCard>>()

    init {
        Firebase.firestore.collection("MemoryCard").addSnapshotListener { value, error ->
            if (error != null) {
                println("FireBase Hatasi")
            } else {
                if (value != null) {
                    if (!value.isEmpty) {
                        val tempCardList = ArrayList<MemoryCard>()
                        val documents = value.documents

                        for (document in documents) {

                            val card_home_name = document.get("card_home_name")
                            val card_name = document.get("card_name")
                            val card_point = document.get("card_point")
                            val card_img = document.get("card_img")
                            val imageBytes = Base64.decode(card_img as String, Base64.DEFAULT)
                            val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

                            val card = MemoryCard(
                                card_name as String,
                                card_home_name as String,
                                card_img as String,
                                card_point as Number,
                                bitmap,
                                true
                            )

                            tempCardList.add(card)
                        }
                        cardList.value = tempCardList

                    }
                }
            }
        }
    }

}