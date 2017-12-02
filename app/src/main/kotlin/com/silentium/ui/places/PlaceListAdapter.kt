package com.silentium.ui.places

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.silentium.R
import com.silentium.data.db.places.PlacesEntity
import org.jetbrains.anko.AnkoLogger

/**
 * Constructor using the context
 *
 * @param context the calling context/activity
 */
class PlaceListAdapter(private val mContext: Context, private var placesEntityArrList: ArrayList<PlacesEntity>) : RecyclerView.Adapter<PlaceViewHolder>(), AnkoLogger {

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item
     *
     * @param parent   The ViewGroup into which the new View will be added
     * @param viewType The view type of the new View
     * @return A new PlaceViewHolder that holds a View with the item_place_card layout
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        // Get the RecyclerView item layout
        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.item_place_card, parent, false)
        return PlaceViewHolder(view, placesEntityArrList)
    }

    /**
     * Binds the data from a particular position in the cursor to the corresponding view holder
     *
     * @param holder   The PlaceViewHolder instance corresponding to the required position
     * @param position The current position that needs to be loaded with data
     */
    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.onBind(position)
    }

    fun addPlaceEntityItem(placesEntity: PlacesEntity){
        this.placesEntityArrList.add(placesEntity)
        notifyDataSetChanged()
    }

    /**
     * Returns the number of items in the cursor
     *
     * @return Number of items in the cursor, or 0 if null
     */
    override fun getItemCount() = placesEntityArrList.size
}
