package com.silentium.ui.places

import android.support.v7.widget.RecyclerView
import android.view.View
import com.silentium.data.db.places.PlacesEntity
import kotlinx.android.synthetic.main.item_place_card.view.*

/**
 * @author lusinabrian on 02/12/17.
 * @Notes Place View holder
 */
class PlaceViewHolder(itemView : View, var placesEntityArrList: ArrayList<PlacesEntity>) : RecyclerView.ViewHolder(itemView){

    fun onBind(position : Int){
        val place = placesEntityArrList[position]
        with(itemView){
            name_text_view.text = place.name
            address_text_view.text = place.address
        }
    }
}