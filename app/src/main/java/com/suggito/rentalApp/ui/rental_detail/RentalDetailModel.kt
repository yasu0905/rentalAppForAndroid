package com.suggito.rentalApp.ui.rental_detail

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.suggito.rentalApp.Items
import com.suggito.rentalApp.Rentals
import java.util.*

class RentalDetailModel {
    companion object {
        const val ITEM = "Item"
        const val RENTAL = "rentals"
    }
    private val firestore: FirebaseFirestore = Firebase.firestore
    private val storage = Firebase.storage

    fun getItemImage(fileName: String): StorageReference {
        val imageRef = storage.reference.child("item_icon").child(fileName)
        return imageRef
    }

    fun rentalDateCheck(id: String): CollectionReference {
        return firestore.collection(ITEM).document(id).collection(RENTAL)
    }

    fun addRentalData(item: Items, rental: Rentals): Task<Void> {
        val ref = firestore.collection(ITEM).document(item.id)
        val subRef = ref.collection(RENTAL)

        return firestore.runBatch {
            //検索用番組名・ユーザ名の追加
            item.search_channel.add(rental.channel)
            item.search_userName.add(rental.userName)
            ref.set(item)
//            ref.update(
//                "search_channel", FieldValue.arrayUnion(rental.channel),
//                "search_userName", FieldValue.arrayUnion(rental.userName)
//            )

            //レンタル情報の追加
            subRef.add(rental)
        }
    }

    fun getRentalDocuments(itemId: String): Task<QuerySnapshot> {
        return firestore.collection(ITEM).document(itemId).collection(RENTAL).get()
    }

    fun returnRentalData(item: Items, rental: Rentals): Task<Void> {
        val ref = firestore.collection(ITEM).document(item.id)
        val subRef = ref.collection(RENTAL).document(rental.id)

        return firestore.runBatch {
            //検索用番組名・ユーザ名の削除
            item.search_channel.remove(rental.channel)
            item.search_userName.remove(rental.userName)
            ref.set(item)
//            ref.update(
//                "search_channel", FieldValue.arrayRemove(rental.channel),
//                "search_userName", FieldValue.arrayRemove(rental.userName)
//            )


            //レンタル情報の削除
            subRef.delete()
        }
    }
}