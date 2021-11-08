package com.example.taskgather.utils

import com.google.firebase.database.*

class AllTypesCount {
    private val database = FirebaseDatabase.getInstance()

    fun addNoteCount(userId: String) {
        val userRef : DatabaseReference = database.getReference("Users").child(userId)
        userRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val noteCount = snapshot.child("totalNotes").value as Long
                    snapshot.child("totalNotes").ref.setValue(noteCount+1)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun removeNoteCount(userId: String) {
        val userRef : DatabaseReference = database.getReference("Users").child(userId)
        userRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val noteCount = snapshot.child("totalNotes").value as Long
                    snapshot.child("totalNotes").ref.setValue(noteCount-1)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun addImageCount(userId: String) {
        val userRef : DatabaseReference = database.getReference("Users").child(userId)
        userRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val noteCount = snapshot.child("totalImages").value as Long
                    snapshot.child("totalImages").ref.setValue(noteCount+1)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun removeImageCount(userId: String) {
        val userRef : DatabaseReference = database.getReference("Users").child(userId)
        userRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val noteCount = snapshot.child("totalImages").value as Long
                    snapshot.child("totalImages").ref.setValue(noteCount-1)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}