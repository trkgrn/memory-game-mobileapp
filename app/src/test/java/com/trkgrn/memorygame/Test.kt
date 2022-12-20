package com.trkgrn.memorygame

import com.trkgrn.memorygame.data.model.MemoryCard
import com.trkgrn.memorygame.util.TxtUtil


class Test {
}

fun main(args: Array<String>){
//    var list = arrayListOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20).filter { it%2==0 }.shuffled()
//    println(list)
//    println(list.random(Random(5)))
//    println(list)

//    var card:MemoryCard = MemoryCard("Abc","Xyz","",1,1,null,)
//    println(card.hashCode())
//    var card2 = MemoryCard(card)
//    println(card2.hashCode())
//    println(card.equals(card2))
//    var card3 = MemoryCard(card)
//    println(card3.hashCode())

//    var x = true
//
//    x = if (x) false else true
//    println(x)
//    x = if (x) false else true
//    println(x)
//    x = if (x) false else true
//    println(x)

    var card1 = MemoryCard("A","B","C",5L,6L,null)
    var card2 = MemoryCard("x","B","sadd",5L,6L,null)
    var cards = arrayListOf<MemoryCard>(card1,card2,card1)

    TxtUtil.writeTxt(cards)



}