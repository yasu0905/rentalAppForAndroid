package com.suggito.rentalApp.ui.rental_detail

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.suggito.rentalApp.*
import kotlinx.android.synthetic.main.fragment_rental_detail.*
import kotlinx.android.synthetic.main.fragment_rental_detail.view.*
import java.lang.Exception
import java.lang.NullPointerException
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [RentalDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RentalDetailFragment : Fragment() {
    private val TAG = "RentalDetailFragment"
    private lateinit var viewModel: RentalDetailViewModel
    private lateinit var item: Items
    private lateinit var rental: Rentals
    private lateinit var loginUser: LoginUser
//    private var _binding: NavHeaderMainBinding? = null
//    private val binding get() = _binding!!
    private val dateConvert = DateConvert()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val args = RentalDetailFragmentArgs.fromBundle(it)
            item = args.item
            rental = args.rental
        }
        //メインのアクティビティからユーザー情報を取得する
        val main = activity as MainActivity
        loginUser = main.loginUser


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(RentalDetailViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_rental_detail, container, false)
        return view
    }

    @SuppressLint("ResourceAsColor", "ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //貸出期間のテキストはキーボード非表示にする
        rentalDateDetailEditText.keyListener = null
        rentalTimeDetailEditText.keyListener = null
        returnDateDetailEditText.keyListener = null
        returnTimeDetailEditText.keyListener = null

        itemNameDetailText.text = item.name
        val userName = loginUser.userName//getHeaderUserName(view)
        val ref = viewModel.getItemImage(item.url)
        GlideApp.with(this).load(ref).into(itemImageDetailImage)
        if (rental.id.isEmpty()) {
            //新規
            userNameDetailText.text = userName
            statusImage.setImageResource(R.drawable.ok)
            statusText.text = "レンタル可"
        } else {
            //既存
            //レンタル情報を各ビューに反映
            userNameDetailText.text = rental.userName
            channelDetailEditText.setText(rental.channel)
            rentalDateDetailEditText.setText(dateConvert.dateToString(rental.rentalDate, "yyyy年MM月dd日"))
            rentalTimeDetailEditText.setText(dateConvert.dateToString(rental.rentalDate, "HH:mm"))
            returnDateDetailEditText.setText(dateConvert.dateToString(rental.returnDate, "yyyy年MM月dd日"))
            returnTimeDetailEditText.setText(dateConvert.dateToString(rental.returnDate, "HH:mm"))
            remarkDetailText.setText(rental.remark)
            //EditText非活性
            setTextDisable(view)
            //レンタル状況の更新
            statusImage.setImageResource(R.drawable.ng)
            statusText.text = "レンタル中"

            if (rental.userName == userName) {
                rentalBtn.setText(R.string.rental_btn_text_return)
                rentalBtn.setBackgroundColor(R.color.colorReturn)
            } else {
                rentalBtn.visibility = View.INVISIBLE   //非表示
            }
        }

        rentalDateDetailEditText.setOnClickListener {
            showDatePicker(it.rentalDateDetailEditText)
        }


        rentalDateDetailEditText.addTextChangedListener {
            if (!checkDatetimeText()) {
                return@addTextChangedListener
            }
            checkDateRange()
        }

        returnDateDetailEditText.setOnClickListener {
            showDatePicker(it.returnDateDetailEditText)
        }

        returnDateDetailEditText.addTextChangedListener {
            if (!checkDatetimeText()) {
                return@addTextChangedListener
            }
            checkDateRange()
        }

        rentalTimeDetailEditText.setOnClickListener {
            showTimePicker(it.rentalTimeDetailEditText)
        }

        rentalTimeDetailEditText.addTextChangedListener {
            if (!checkDatetimeText()) {
                return@addTextChangedListener
            }
            checkDateRange()
        }

        returnTimeDetailEditText.setOnClickListener {
            showTimePicker(it.returnTimeDetailEditText)
        }

        returnTimeDetailEditText.addTextChangedListener {
            if (!checkDatetimeText()) {
                return@addTextChangedListener
            }
            checkDateRange()
        }

        rentalBtn.setOnClickListener {
            if (rental.id.isNullOrEmpty()) {
                //新規登録
                val rentalData = Rentals(
                    "",
                    itemNameDetailText.text.toString(),
                    channelDetailEditText.text.toString(),
                    loginUser.id,
                    loginUser.userName,
                    convertTextToData(rentalDateDetailEditText, rentalTimeDetailEditText),
                    convertTextToData(returnDateDetailEditText, returnTimeDetailEditText),
                    remarkDetailText.text.toString()
                )

                viewModel.addRentalData(item, rentalData).observe(viewLifecycleOwner, Observer {
                    if (it == null) {
                        return@Observer
                    }
                    if (!it) {
                        Toast.makeText(this.context, "登録に失敗しました。", Toast.LENGTH_LONG).show()
                        return@Observer
                    }
                    findNavController().popBackStack(R.id.nav_item_list, false)//popBackStack()
                })
            } else {
                //更新
                viewModel.returnRentalData(item, rental).observe(viewLifecycleOwner, Observer {
                    if (it == null) {
                        return@Observer
                    }
                    if (!it) {
                        Toast.makeText(this.context, "返却に失敗しました。", Toast.LENGTH_LONG).show()
                        return@Observer
                    }
                    findNavController().popBackStack(R.id.nav_item_list, false)//popBackStack()
                })
            }
        }

    }

    private fun checkDateRange() {

        try {
            val dateStringRental = "${rentalDateDetailEditText.text} ${rentalTimeDetailEditText.text}"
            val dateStringReturn = "${returnDateDetailEditText.text} ${returnTimeDetailEditText.text}"
            val searchRentalDate = dateConvert.stringToDate(dateStringRental, "yyyy/MM/dd HH:mm")
            val searchReturnDate = dateConvert.stringToDate(dateStringReturn, "yyyy/MM/dd HH:mm")

            viewModel.rentalDateCheck(item.id, searchRentalDate!!, searchReturnDate!!).addOnSuccessListener { snapshot ->
                var result: Boolean = true
                if (snapshot.toObjects(Rentals::class.java) != null) {
                    for (rental in snapshot.toObjects(Rentals::class.java)) {
                        if ((searchRentalDate >= rental.rentalDate && searchRentalDate >= rental.returnDate) ||
                            (searchReturnDate <= rental.rentalDate && searchReturnDate <= rental.returnDate)
                        ) {
                            println("空きあり")
                        } else {
                            println("空きなし")
                            result = false
                            break
                        }
                    }
                    //レンタル状況の更新
                    changeRentalStatus(result)
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, exception.localizedMessage)

            }
        } catch (e: NullPointerException) {
            Log.e(TAG, e.localizedMessage)
        } catch (e: Exception) {
            Log.e(TAG, e.localizedMessage)
        }
    }

    private fun changeRentalStatus(result: Boolean) {
        if (result) {
            statusImage.setImageResource(R.drawable.ok)
            statusText.text = "レンタル可"
            rentalBtn.visibility = View.VISIBLE   //表示
        } else {
            statusImage.setImageResource(R.drawable.ng)
            statusText.text = "レンタル不可"
            rentalBtn.visibility = View.INVISIBLE   //非表示
        }
    }

    private fun setTextDisable(view: View) {
        view.channelDetailEditText.isEnabled = false
        view.rentalDateDetailEditText.isEnabled = false
        view.rentalTimeDetailEditText.isEnabled = false
        view.returnDateDetailEditText.isEnabled = false
        view.returnTimeDetailEditText.isEnabled = false
        view.remarkDetailText.isEnabled = false
    }

    private fun checkDatetimeText(): Boolean {
        var result = true
        if (rentalDateDetailEditText.text.isNullOrEmpty()) {
            result = false
        }
        if (rentalTimeDetailEditText.text.isNullOrEmpty()) {
            result = false
        }
        if (returnDateDetailEditText.text.isNullOrEmpty()) {
            result = false
        }
        if (returnTimeDetailEditText.text.isNullOrEmpty()) {
            result = false
        }
        return result
    }

    private fun showDatePicker(targetText: EditText) {
        val calendar = Calendar.getInstance()
        val datePickerDialog = this.context?.let {
            DatePickerDialog(
                it,
                DatePickerDialog.OnDateSetListener() {view, year, month, dayOfMonth ->
                    val m = "${month + 1}".padStart(2, '0')
                    val d = "${dayOfMonth}".padStart(2, '0')
                    targetText.setText("${year}/${m}/${d}")
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
        }
        datePickerDialog?.show()
    }

    private fun showTimePicker(targetText: EditText) {
        val calendar = Calendar.getInstance()
        val timePickerDialog = this.context?.let {
            TimePickerDialog(
                it,
                TimePickerDialog.OnTimeSetListener() {view, hour, minute ->
                    val h = "${hour}".padStart(2, '0')
                    val m = "${minute}".padStart(2, '0')
                    targetText.setText("${h}:${m}")
                },

                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), true)
        }
        timePickerDialog?.show()
    }

    private fun convertTextToData(dateText: EditText, timeText: EditText): Date {
        val dateString = "${dateText.text} ${timeText.text}"
        return dateConvert.stringToDate(dateString, "yyyy/MM/dd HH:mm")!!
    }
}