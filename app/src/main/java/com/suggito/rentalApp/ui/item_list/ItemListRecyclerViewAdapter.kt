package com.suggito.rentalApp.ui.item_list

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.suggito.rentalApp.Items
import com.suggito.rentalApp.R
import kotlinx.android.synthetic.main.fragment_item_list_cell.view.*


class ItemListRecyclerViewAdapter(items: MutableList<Items>) : RecyclerView.Adapter<ItemListRecyclerViewAdapter.ViewHolder>() {
    private val rItems: List<Items> = items


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_item_list_cell, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = rItems[position]
        val viewModel = ItemListViewModel()

        val ref = viewModel.getItemImage(item.url)
        //holder.itemImageCell
        holder.itemNameCell?.text = item.name
    }

    override fun getItemCount(): Int = rItems.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemImageCell: ImageView? = null
        var itemNameCell: TextView? = null


        init {
            itemImageCell = view.itemImageCell
            itemNameCell = view.itemNameTextCell
        }
    }
}