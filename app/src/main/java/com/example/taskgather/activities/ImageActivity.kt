package com.example.taskgather.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.example.taskgather.R
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.GridLayoutManager
import com.example.taskgather.databinding.ActivityImageBinding
import com.example.taskgather.adapters.ImageAdapter
import com.example.taskgather.fragments.UploadImagesFragment
import com.example.taskgather.models.ImageModel
import com.google.firebase.database.*

class ImageActivity : AppCompatActivity() {

    lateinit var binding: ActivityImageBinding
    lateinit var imageList : ArrayList<ImageModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        actionBar!!.title = "Image"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        imageList = ArrayList()
        binding.imageRecyclerView.setHasFixedSize(true)
        binding.imageRecyclerView.layoutManager = GridLayoutManager(this,2, GridLayoutManager.VERTICAL,false)

        loadImages()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.image_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.forImageFragment, UploadImagesFragment())
                    .commit()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadImages() {
        imageList.clear()
        val imageRef : DatabaseReference = FirebaseDatabase.getInstance()
            .getReference("ImageNotes")
        imageRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                imageList.clear()
                for (ds in snapshot.children) {
                    val imageModel: ImageModel? = ds.getValue(ImageModel::class.java)
                    imageList.add(imageModel!!)
                    val adapter = ImageAdapter(applicationContext,imageList)
                    binding.imageRecyclerView.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

}