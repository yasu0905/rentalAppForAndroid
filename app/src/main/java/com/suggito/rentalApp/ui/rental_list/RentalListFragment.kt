package com.suggito.rentalApp.ui.rental_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.suggito.rentalApp.Items
import com.suggito.rentalApp.R
import com.suggito.rentalApp.Rentals
import kotlinx.android.synthetic.main.fragment_rental_list.*
import kotlinx.android.synthetic.main.fragment_rental_list.view.*

/**
 * create an instance of this fragment.
 */
class RentalListFragment : Fragment() {
    private lateinit var adapter: RentalListRecyclerViewAdapter
    private lateinit var layoutManeger: RecyclerView.LayoutManager
    private lateinit var item: Items
    private lateinit var rentals: MutableList<Rentals>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            val args = RentalListFragmentArgs.fromBundle(it)
            rentals = args.rentals.toMutableList()
            item = args.item
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_rental_list, container, false)

        //if (view is RecyclerView) {
        val itemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        view.rentalListRCView.addItemDecoration(itemDecoration)
        layoutManeger = LinearLayoutManager(this.context)
        view.rentalListRCView.layoutManager = layoutManeger
        adapter = RentalListRecyclerViewAdapter(item, rentals)
        view.rentalListRCView.adapter = adapter
        //}
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rentalDataAddBtn.setOnClickListener {
            val newRental = Rentals()
            val action = RentalListFragmentDirections.actionNavRentalListToNavRentalDetail(item, newRental)
            findNavController().navigate(action)
        }
    }

}