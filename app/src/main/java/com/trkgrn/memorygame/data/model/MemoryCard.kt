package com.trkgrn.memorygame.data.model

import android.graphics.Bitmap
import android.widget.ImageView
import kotlinx.coroutines.channels.ticker

class MemoryCard(
    var cardName: String,
    var cardHomeName: String,
    var cardImgBase64: String,
    var cardPoint: Long,
    var cardHomePoint: Long,
    var imageBitmap: Bitmap?,
    var isHidden: Boolean = true,
    var isMatch: Boolean = false
) {
    constructor(memoryCard: MemoryCard) : this(
        memoryCard.cardName,
        memoryCard.cardHomeName,
        memoryCard.cardImgBase64,
        memoryCard.cardPoint,
        memoryCard.cardHomePoint,
        memoryCard.imageBitmap,
        memoryCard.isHidden,
        memoryCard.isMatch
    ) {
    }


}