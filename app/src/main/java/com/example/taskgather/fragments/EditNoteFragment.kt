package com.example.taskgather.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.taskgather.databinding.FragmentEditNoteBinding
import com.example.taskgather.models.NoteModel
import com.example.taskgather.utils.FirebaseOperations
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class EditNoteFragment : Fragment() {

    private lateinit var binding : FragmentEditNoteBinding
    private lateinit var noteId: String
    private var note : NoteModel? = null

    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditNoteBinding.inflate(inflater, container, false)

        val bundle = arguments
        noteId = bundle!!.getString("NOTE_ID")!!

        auth = FirebaseAuth.getInstance()

        loadDetails()

        binding.btnCancel.setOnClickListener {
            binding.editFragmentLayout.visibility = View.GONE
        }

        binding.btnSave.setOnClickListener {
            if (note!= null) {
                val title = binding.etTitle.text.toString()
                val desc = binding.etDesc.text.toString()

                FirebaseOperations().updateTextNote(note!!.userId,noteId,title,desc, System.currentTimeMillis())
                binding.editFragmentLayout.visibility = View.GONE
                Toast.makeText(context,"Note Updated...", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun loadDetails() {
        val noteRef : DatabaseReference = FirebaseDatabase.getInstance()
            .getReference("Notes").child("TextNotes").child(noteId)

        noteRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    note = snapshot.getValue(NoteModel::class.java)!!
                    if (note!=null) {
                        binding.etTitle.setText(note!!.noteTitle)
                        binding.etDesc.setText(note!!.noteDesc)
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })



    }


}