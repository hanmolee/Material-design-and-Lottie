package com.material.hanmo.material_design_strudy.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.support.v4.content.ContextCompat
import android.support.v7.graphics.Palette
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.material.hanmo.material_design_strudy.PlaceData
import com.material.hanmo.material_design_strudy.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_places.view.*

/**
 * Created by hanmo on 2018. 3. 5..
 */
class TravelListAdapter(private var context: Context) : RecyclerView.Adapter<TravelListAdapter.ViewHolder>() {

    lateinit var itemClickListener : OnItemClickListener

    override fun getItemCount(): Int {
        return PlaceData.placeList().size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_places, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = PlaceData.placeList()[position]
        holder.itemView.placeName.text = place.name
        Picasso.with(context).load(place.getImageResourceId(context)).into(holder.itemView.placeImage)

        val photo = BitmapFactory.decodeResource(context.resources, place.getImageResourceId(context))
        Palette.from(photo).generate{
            val bgColor = it.getMutedColor(ContextCompat.getColor(context, android.R.color.black))
            holder.itemView.placeNameHolder.setBackgroundColor(bgColor)
        }
    }

    fun setOnItemClickListener(itemClickListener : OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(view : View, position: Int)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.placeHolder.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            itemClickListener.onItemClick(itemView, adapterPosition)
        }
    }
}