package com.trkgrn.memorygame.data.firestore

import android.content.Context
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.trkgrn.memorygame.data.model.MemoryCard

class FireStoreHelper {
    private lateinit var firestore: FirebaseFirestore

    companion object {
        private var instance: FireStoreHelper? = null
        fun getInstance(): FireStoreHelper {
            if (instance == null) {
                instance = FireStoreHelper()
            }
            return instance as FireStoreHelper
        }
    }


    fun getAllCards(): ArrayList<MemoryCard>? {

            firestore = Firebase.firestore


        var cardList = ArrayList<MemoryCard>()

        firestore.collection("MemoryCard").addSnapshotListener { value, error ->
            if (error != null) {
                println("FireBase Hatasi")
            } else {
                if (value != null) {
                    if (!value.isEmpty) {
                        val documents = value.documents
                        for (document in documents) {

                            val card_home_name = document.get("card_home_name")
                            val card_name = document.get("card_name")
                            val card_point = document.get("card_point")
                            val card_img = document.get("card_img")

                            val card = MemoryCard(
                                card_name as String,
                                card_home_name as String,
                                card_img as String,
                                card_point as Number
                            )
                            cardList.add(card)
                            println(card_name as String)

                        }

                    }
                }
            }

        }
        return cardList
    }


}