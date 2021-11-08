package com.example.taskgather.activities

import android.annotation.SuppressLint
import android.os.Bundle
import com.example.taskgather.R
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.taskgather.databinding.ActivityNoteBinding
import com.example.taskgather.adapters.ItemClickListener
import com.example.taskgather.adapters.NoteAdapter
import com.example.taskgather.fragments.EditNoteFragment
import com.example.taskgather.models.NoteModel
import com.example.taskgather.utils.FirebaseOperations
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class NoteActivity : AppCompatActivity(), ItemClickListener {

    lateinit var binding: ActivityNoteBinding

    lateinit var noteList : ArrayList<NoteModel>
    lateinit var listener : ItemClickListener

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        actionBar!!.title = "Note"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        auth = FirebaseAuth.getInstance()

        binding.notesRecyclerView.setHasFixedSize(true)
        binding.notesRecyclerView.layoutManager = GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false)
        noteList = ArrayList()
        listener = this

        loadNotes()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.note_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> {
                openDialogNote()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("InflateParams")
    private fun openDialogNote() {

        val dialog = BottomSheetDialog(this, R.style.AppBottomSheetDialogTheme)
        val view = layoutInflater.inflate(R.layout.addnote_bottomsheet,null)

        val title = view.findViewById<TextInputEditText>(R.id.et_title)
        val desc = view.findViewById<TextInputEditText>(R.id.et_desc)
        val save = view.findViewById<MaterialButton>(R.id.btn_save)

        save.setOnClickListener {
            FirebaseOperations().saveTextNote(auth.currentUser!!.uid,title.text.toString(), desc.text.toString(),System.currentTimeMillis())
            Toast.makeText(this,"Contents Upload...",Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()

    }

    private fun loadNotes() {
        noteList.clear()
        val noteRef : DatabaseReference = FirebaseDatabase.getInstance()
            .getReference("TextNotes")
        noteRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                noteList.clear()
                for (ds in snapshot.children) {
                    val modelComments: NoteModel? = ds.getValue(NoteModel::class.java)
                    noteList.add(modelComments!!)
                    val adapter =
                        NoteAdapter(applicationContext, noteList,listener)
                    binding.notesRecyclerView.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    override fun onEditClick(noteModel: NoteModel) {
        val mFragmentManager = supportFragmentManager
        val mFragmentTransaction = mFragmentManager.beginTransaction()
        val mFragment = EditNoteFragment()

        val mBundle = Bundle()
        mBundle.putString("NOTE_ID",noteModel.noteId)
        mFragment.arguments = mBundle
        mFragmentTransaction.add(R.id.fragmentTransaction,mFragment).commit()
    }
}