package com.trkgrn.memorygame.data.model

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable

data class MemoryCard(
    var cardName: String?,
    var cardHomeName: String?,
    var cardImgBase64: String?,
    var cardPoint: Long,
    var cardHomePoint: Long,
    var imageBitmap: Bitmap?,
    var isHidden: Boolean = true,
    var isMatch: Boolean = false
) :Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readLong(),
        parcel.readParcelable(Bitmap::class.java.classLoader),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(cardName)
        parcel.writeString(cardHomeName)
        parcel.writeString(cardImgBase64)
        parcel.writeLong(cardPoint)
        parcel.writeLong(cardHomePoint)
        parcel.writeParcelable(imageBitmap, flags)
        parcel.writeByte(if (isHidden) 1 else 0)
        parcel.writeByte(if (isMatch) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MemoryCard> {
        override fun createFromParcel(parcel: Parcel): MemoryCard {
            return MemoryCard(parcel)
        }

        override fun newArray(size: Int): Array<MemoryCard?> {
            return arrayOfNulls(size)
        }
    }

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