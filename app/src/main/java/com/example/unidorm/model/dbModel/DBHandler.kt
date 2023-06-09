package com.example.unidorm.model.dbModel


import android.net.Uri

import android.widget.ArrayAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID
import java.util.*
import android.content.Context
import android.graphics.Bitmap
import android.provider.MediaStore
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unidorm.fragments.contract.navigator
import com.google.android.gms.nearby.messages.Message
import com.google.firebase.messaging.Constants
import com.google.firebase.messaging.FirebaseMessaging

import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject
import java.io.ByteArrayOutputStream


class DBHandler {

    private lateinit var dbRef: DatabaseReference

    private lateinit var picName: String

    fun publishNotification(str: String, discr: String) {
        dbRef = FirebaseDatabase.getInstance().getReference("Notification")
        val notifId = dbRef.push().key!!
        val notif = NotificationModel(notifId, str, discr)
        dbRef.child(notifId).setValue(notif)
    }

        //
      /*  val message = Message.builder()
            .setNotification(Notification(title, body))
            .setToken(token)
            .build()

        try {
            val response = FirebaseMessaging.getInstance().send(message)
            println("Successfully sent notification: $response")
        } catch (e: Exception) {
            println("Failed to send notification: ${e.message}")
        }*/

    fun readNotificationList(adapter: ArrayAdapter<String>, notifList: MutableList<NotificationModel>) {
        dbRef = FirebaseDatabase.getInstance().getReference("Notification")


        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = ArrayList<String>()

                for (childSnapshot in snapshot.children) {
                    val notif = childSnapshot.getValue(NotificationModel::class.java)

                    if(notif!=null){
                        notifList.add(notif)
                        items.add(notif.notifText.toString())
                    }
                }

                adapter.clear()
                adapter.addAll(items)
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun readAddressInfo(adapter: ArrayAdapter<String>, dormList: MutableList<DormitoryModel>) {
        dbRef = FirebaseDatabase.getInstance().getReference("Dormitory")


        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = ArrayList<String>()

                for (childSnapshot in snapshot.children) {
                    val dorm = childSnapshot.getValue(DormitoryModel::class.java)

                    if(dorm!=null){
                        dormList.add(dorm)
                        items.add(dorm.dormName.toString())
                    }
                }

                adapter.clear()
                adapter.addAll(items)
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }


    fun publishItem(item: ShopItemModel) {
        dbRef = FirebaseDatabase.getInstance().getReference("ShopItem")
        val itemId = dbRef.push().key!!
        item.itemId = itemId
        if (picName != null)
            item.Picture = picName
        else item.Picture = " "

        dbRef.child(itemId).setValue(item)
    }

    fun uploadImageToFirebaseStorage(context: Context, uri: Uri) {
        try {
            // Получаем ссылку на Firebase Storage
            val storageRef = FirebaseStorage.getInstance().reference

            // Генерируем уникальное имя для файла
            picName = UUID.randomUUID().toString()

            // Создаем ссылку на файл в Firebase Storage
            val imageRef = storageRef.child("images/$picName.jpg")

            // Получаем ContentResolver из контекста
            val contentResolver = context.contentResolver

            // Получаем Bitmap из Uri
            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)

            // Создаем ByteArrayOutputStream для сжатия изображения в формат JPEG
            val baos = ByteArrayOutputStream()

            // Сжимаем картинку и сохраняем в ByteArrayOutputStream
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos)

            // Получаем байты из ByteArrayOutputStream
            val imageData = baos.toByteArray()

            // Загружаем картинку в Firebase Storage
            val uploadTask = imageRef.putBytes(imageData)
            uploadTask.addOnSuccessListener { taskSnapshot ->
                // Загрузка успешно завершена

                val downloadUrl = taskSnapshot.metadata?.reference?.downloadUrl
                Log.d("Firebase", "Image uploaded successfully. Download URL: $downloadUrl")
                // Здесь вы можете выполнить необходимые действия с загруженным изображением,
                // например, сохранить ссылку на изображение в базе данных или выполнить другую логику.
            }.addOnFailureListener { exception ->
                // Произошла ошибка при загрузке
                Log.e("Firebase", "Image upload failed: ${exception.message}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun readItemDB(id:String) {
        dbRef = FirebaseDatabase.getInstance().getReference("ShopItem")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val shItemList = mutableListOf<ShopItemModel>()



                for (snapshot in dataSnapshot.children) {
                    val shItem = snapshot.getValue(ShopItemModel::class.java)



                    if (shItem != null && id==shItem.itemId) {

                        shItemList.add(shItem)

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}