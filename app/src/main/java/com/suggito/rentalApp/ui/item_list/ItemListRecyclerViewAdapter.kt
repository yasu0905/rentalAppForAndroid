package com.suggito.rentalApp.ui.item_list

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.suggito.rentalApp.GlideApp
import com.suggito.rentalApp.Items
import com.suggito.rentalApp.R
import kotlinx.android.synthetic.main.fragment_item_list_cell.view.*


class ItemListRecyclerViewAdapter(items: MutableList<Items>, parentLifecycleOwner: LifecycleOwner) : RecyclerView.Adapter<ItemListRecyclerViewAdapter.ViewHolder>() {
    private val rItems: List<Items> = items
    private val lifecycleOwner: LifecycleOwner = parentLifecycleOwner

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_item_list_cell, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = rItems[position]
        val viewModel = ItemListViewModel()
        val ref = viewModel.getItemImage(item.url)
        GlideApp.with(holder.itemView.context).load(ref).centerCrop().into(holder.itemImageCell!!)
        holder.itemNameCell?.text = item.name


        holder.itemView.setOnClickListener {
            val id = item.id
            viewModel.getItemHasRentalData(id).observe(lifecycleOwner, Observer { rentals ->
                if (rentals == null) {
                    return@Observer
                }
//                if (rentals.isEmpty()) {
//                    Toast.makeText(it.context, "検索に該当するデータがありませんでした。", Toast.LENGTH_LONG).show()
//                    return@Observer
//                }

                val action = ItemListFragmentDirections.actionNavItemListToNavRentalList(rentals.toTypedArray(), item)
                it.findNavController().navigate(action)
            })
        }
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