package com.example.taskgather.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskgather.R
import com.example.taskgather.conversion.TimeConversion
import com.example.taskgather.models.NoteModel
import com.example.taskgather.utils.FirebaseOperations
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import java.util.*
import android.content.Intent
import androidx.core.content.ContextCompat

import androidx.core.content.ContextCompat.startActivity




class NoteAdapter(var context: Context, var noteList : ArrayList<NoteModel>, var listener: ItemClickListener)
    : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val view = NoteHolder(LayoutInflater.from(context).inflate(R.layout.note_layout, parent, false))

        view.edit.setOnClickListener { listener.onEditClick(noteList[view.adapterPosition]) }
        return view
    }

    @SuppressLint("InflateParams")
    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val noteItem = noteList[position]
        val dateTime = noteItem.noteDate

        holder.title.text = noteItem.noteTitle
        holder.desc.text = noteItem.noteDesc
        holder.date.text = TimeConversion.getDate(dateTime)

        holder.delete.setOnClickListener { v->
            Snackbar.make(v, "Are you sure to delete", Snackbar.LENGTH_SHORT)
                .setAction("Delete", View.OnClickListener {

                    FirebaseOperations().deleteSingleNote(noteItem.noteId,noteItem.userId)
                    notifyItemRemoved(position)

                }).show()
        }

        holder.edit.setOnClickListener {
            listener.onEditClick(noteList[position])
        }

        holder.share.setOnClickListener {
            listener.onShareClick(noteList[position])
        }
    }


    override fun getItemCount(): Int {
        return noteList.size
    }

    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.tv_title)
        var desc : TextView = itemView.findViewById(R.id.tv_desc)
        var date: TextView = itemView.findViewById(R.id.tv_date)
        var delete: ImageButton = itemView.findViewById(R.id.btn_delete)
        var edit: ImageButton = itemView.findViewById(R.id.btn_edit)
        var share : ImageButton = itemView.findViewById(R.id.btn_share)

    }

}

interface ItemClickListener{
    fun onEditClick(noteModel: NoteModel)
    fun onShareClick(noteModel: NoteModel)
}