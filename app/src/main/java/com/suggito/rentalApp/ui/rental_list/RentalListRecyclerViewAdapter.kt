package com.suggito.rentalApp.ui.rental_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.suggito.rentalApp.DateConvert
import com.suggito.rentalApp.Items
import com.suggito.rentalApp.R
import com.suggito.rentalApp.Rentals
import kotlinx.android.synthetic.main.fragment_item_list_cell.view.*
import kotlinx.android.synthetic.main.fragment_rental_list_cell.view.*

class RentalListRecyclerViewAdapter(item: Items, rentals: MutableList<Rentals>): RecyclerView.Adapter<RentalListRecyclerViewAdapter.ViewHolder>() {
    private val rItem: Items = item
    private val rRentals: List<Rentals> = rentals
    //private val lifecycleOwner: LifecycleOwner = parentLifecycleOwner
    private val dateConvert: DateConvert = DateConvert()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var rentalDateCell: TextView? = null
        var rentalTimeCell: TextView? = null
        var returnDateCell: TextView? = null
        var returnTimeCell: TextView? = null
        var channelCell: TextView? = null
        var userNameCell: TextView? = null

        init {
            rentalDateCell = view.rentalDateTextCell
            rentalTimeCell = view.rentalTimeTextCell
            returnDateCell = view.returnDateTextCell
            returnTimeCell = view.returnTimeTextCell
            channelCell = view.channelTextCell
            userNameCell = view.userNameTextCell
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_rental_list_cell, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = rRentals.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rental = rRentals[position]
        with(holder) {
            rentalDateCell?.text = dateConvert.dateToString(rental.rentalDate).toString()
            rentalTimeCell?.text = dateConvert.dateToString(rental.rentalDate, "HH:mm").toString()
            returnDateCell?.text = dateConvert.dateToString(rental.returnDate).toString()
            returnTimeCell?.text = dateConvert.dateToString(rental.returnDate, "HH:mm").toString()
            channelCell?.text = rental.channel
            userNameCell?.text = rental.userName
        }

        holder.itemView.setOnClickListener {
            val action = RentalListFragmentDirections.actionNavRentalListToNavRentalDetail(rItem, rental)
            it.findNavController().navigate(action)
        }

    }
}