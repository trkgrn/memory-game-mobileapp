package com.trkgrn.memorygame.data.adapter

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.trkgrn.memorygame.R
import com.trkgrn.memorygame.data.model.MemoryCard
import com.trkgrn.memorygame.ui.game.GameScreenFragment
import kotlin.math.roundToInt

class MemoryCardAdapter(context: GameScreenFragment, arrayListCards: ArrayList<MemoryCard>,gridSize:Int) : BaseAdapter(){

    var context = context
    var memoryCards = arrayListCards
    val Int.dp: Int get() = (this * Resources.getSystem().displayMetrics.density).roundToInt()
    var gridSize = gridSize

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

            setSize(holder.mImageView!!)

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


    fun setSize(imageView: ImageView){
        var resources= context.resources
        if (gridSize==2){
            imageView.layoutParams.width = resources.getInteger(R.integer.easy_width).dp
            imageView.layoutParams.height = resources.getInteger(R.integer.easy_height).dp
        }else if(gridSize==4){
            imageView.layoutParams.width = resources.getInteger(R.integer.mid_width).dp
            imageView.layoutParams.height = resources.getInteger(R.integer.mid_height).dp
        }else if (gridSize==6){
            imageView.layoutParams.width = resources.getInteger(R.integer.hard_width).dp
            imageView.layoutParams.height = resources.getInteger(R.integer.hard_height).dp
        }
    }




    class ViewHolder {

        var mImageView: ImageView? = null
    }


}