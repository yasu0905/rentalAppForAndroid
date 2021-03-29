package com.suggito.rentalApp.ui.item_list

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.addCallback
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.suggito.rentalApp.Items
import com.suggito.rentalApp.MainActivity
import com.suggito.rentalApp.R
import kotlinx.android.synthetic.main.fragment_item_list.*
import kotlinx.android.synthetic.main.fragment_item_list.view.*

/**
 * A fragment representing a list of Items.
 */
class ItemListFragment : Fragment() {
    private lateinit var adapter: ItemListRecyclerViewAdapter
    private lateinit var layoutManeger: RecyclerView.LayoutManager
    private lateinit var items: MutableList<Items>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            val args = ItemListFragmentArgs.fromBundle(it)
            items = args.items.toMutableList()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //キーボード閉じる
        closeKeyboard()

        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            val itemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
            view.addItemDecoration(itemDecoration)
            layoutManeger = LinearLayoutManager(this.context)
            view.layoutManager = layoutManeger
            adapter = ItemListRecyclerViewAdapter(items, viewLifecycleOwner)
            view.itemListRCView.adapter = adapter
        }
        return view

    }

    private fun closeKeyboard() {
        val v = activity?.currentFocus
        if (v != null) {
            val manager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }
}