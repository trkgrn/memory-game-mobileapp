package com.trkgrn.memorygame.util

import android.content.Context
import android.net.Uri
import com.trkgrn.memorygame.data.model.MemoryCard
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.nio.file.Files
import java.nio.file.Path

class TxtUtil {

    companion object{
        fun writeTxt(memoryCards:ArrayList<MemoryCard>,context:Context){
            var text = "file.txt"

            val newFile = File(context.filesDir,text)
            newFile.delete()
            newFile.createNewFile()
            val file = FileWriter(newFile)
            file.write("Pozisyon,Ad,Puan,Ev Adı,Ev Katsayısı")
            memoryCards.forEachIndexed { index, memoryCard ->
               var str = "\n"+ index.toString()+","+memoryCard.cardName+","+memoryCard.cardPoint+","+
                       memoryCard.cardHomeName+","+memoryCard.cardHomePoint
                file.write(str)
            }
            file.close()

        }



    }

}