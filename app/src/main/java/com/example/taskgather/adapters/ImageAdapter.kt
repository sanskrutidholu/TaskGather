package com.example.taskgather.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskgather.R
import com.example.taskgather.conversion.TimeConversion
import com.example.taskgather.models.ImageModel
import com.example.taskgather.utils.FirebaseOperations
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class ImageAdapter(var context: Context, var imageList: ArrayList<ImageModel>): RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.image_layout, parent, false)

        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentPost = imageList[position]

        holder.date.text = TimeConversion.getDate(currentPost.ImageDate)
        holder.caption.text = currentPost.ImageTitle

        try {
            Picasso.get().load(currentPost.ImageUrl).into(holder.image)
        }catch (e: Exception){
            e.stackTrace
        }

        holder.delete.setOnClickListener { v->
            Snackbar.make(v, "Are you sure to delete", Snackbar.LENGTH_SHORT)
                .setAction("Delete", View.OnClickListener {

                    FirebaseOperations().deleteSingleImage(currentPost.ImageId,currentPost.userId)
                    notifyItemRemoved(position)

                }).show()
        }

    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    inner class ImageViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val image : ImageView = itemView.findViewById(R.id.imageUpload)
        val date : TextView = itemView.findViewById(R.id.tv_date)
        val caption : TextView = itemView.findViewById(R.id.tv_title)
        val delete : ImageButton = itemView.findViewById(R.id.btn_delete)
    }
}