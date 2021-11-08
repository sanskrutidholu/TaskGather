package com.example.taskgather.utils

import com.example.taskgather.models.ImageModel
import com.example.taskgather.models.NoteModel
import com.example.taskgather.models.UserModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FirebaseOperations {

    private val database = FirebaseDatabase.getInstance()//"https://note-app-2bf2f-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private var db : DatabaseReference = database.reference

    fun registerUser (userId:String,name:String,email:String) {
        val userDetails = UserModel(userId, name, email)
        val userRef : DatabaseReference = database.getReference("Users")
        userRef.child(userId).setValue(userDetails)
    }

    fun saveTextNote(userId:String ,title: String, desc:String, time: Long) {
        val key = db.push().key.toString()
        val data = NoteModel(userId,key,title,desc,time)
        val noteRef : DatabaseReference = database.getReference("TextNotes").child(key)
        noteRef.setValue(data)
    }

    fun updateTextNote(userId:String, id: String, title: String,desc:String, time:Long) {
        val updatedUser = NoteModel(userId,id,title,desc,time)
        val noteRef : DatabaseReference = database.getReference("TextNotes").child(id)
        noteRef.setValue(updatedUser)
    }

    fun deleteSingleNote(noteId: String) {
        database.getReference("TextNotes").child(noteId).removeValue()
    }

    fun uploadImageToDatabase(userId:String,id:String, caption:String, url:String, time:Long ) {
        val imageData = ImageModel(userId,id,caption,url,time)
        val imageRef : DatabaseReference = database.getReference("ImageNotes").child(id)
        imageRef.setValue(imageData)
    }

    fun deleteSingleImage(imageId : String) {
        database.getReference("ImageNotes").child(imageId).removeValue()
    }



}