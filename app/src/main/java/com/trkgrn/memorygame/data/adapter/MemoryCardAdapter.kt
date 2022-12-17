package com.trkgrn.memorygame.data.adapter

import android.app.Activity
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.trkgrn.memorygame.R
import com.trkgrn.memorygame.data.model.MemoryCard
import com.trkgrn.memorygame.ui.game.GameScreenFragment

class MemoryCardAdapter(context: GameScreenFragment, arrayListCards: ArrayList<MemoryCard>) : BaseAdapter(){

    var context = context
    var memoryCards = arrayListCards

    override fun getCount(): Int {
        return memoryCards.size
    }

    override fun getItem(p0: Int): Any {
        return memoryCards.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var myView = convertView
        var holder: ViewHolder

        if (myView == null) {

            val mInflater = (context as Fragment).layoutInflater
            myView = mInflater.inflate(R.layout.memory_card_hidden_item, parent, false)
            holder = ViewHolder()

            holder.mImageView = myView!!.findViewById<ImageView>(R.id.card_item) as ImageView
            if (!memoryCards.get(position).isHidden || memoryCards.get(position).isMatch){
                holder.mImageView!!.setImageBitmap(memoryCards.get(position).imageBitmap)
            }
            else
                holder.mImageView!!.setImageResource(R.drawable.card_front_side)

            myView.setTag(holder)
        } else {
            holder = myView.getTag() as ViewHolder

        }

        return myView

    }




    class ViewHolder {

        var mImageView: ImageView? = null
    }


}