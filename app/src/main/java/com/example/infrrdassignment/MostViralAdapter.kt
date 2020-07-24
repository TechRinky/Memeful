package com.example.infrrdassignment

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_most_viral.view.*


/**
 * This class is responsible to show all the memes in list
 */
class MostViralAdapter(mostViralMemeList: ArrayList<MemesModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mostViralMemeList = ArrayList<MemesModel>()
    private var context: Context? = null

    init {
        this.mostViralMemeList = mostViralMemeList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
        val view = inflater.inflate(R.layout.row_most_viral, parent, false);
        return MostViralViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mostViralMemeList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MostViralViewHolder) {
            val heightInDp = Utility.pxToDp(mostViralMemeList.get(position).height, context!!)
            holder.memeIv.getLayoutParams().height = heightInDp
            Picasso.with(context).load(mostViralMemeList.get(position).link).into(holder.memeIv);
            if (!TextUtils.isEmpty(mostViralMemeList.get(position).description)) {
                holder.descriptionTv.visibility = View.VISIBLE
                holder.descriptionTv.setText(mostViralMemeList.get(position).description)
            } else {
                holder.descriptionTv.visibility = View.GONE
            }
            val pointsString = context!!.getString(R.string.points)
            val spaceVal = " "
            val pointsValue = mostViralMemeList.get(position).points.toString() + spaceVal + pointsString
            holder.pointsTv.setText(pointsValue)

        }
    }

    /**
     * This class represents the viewholder for recylerview
     */
    inner class MostViralViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val memeIv: ImageView = itemView.meme_iv
        val descriptionTv: TextView = itemView.description_tv
        val pointsTv: TextView = itemView.points_tv
    }
}