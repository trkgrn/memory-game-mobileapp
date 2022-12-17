package com.trkgrn.memorygame.data.model

import android.graphics.Bitmap
import android.widget.ImageView

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


}