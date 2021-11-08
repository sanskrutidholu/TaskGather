package com.example.taskgather.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toBitmap
import com.example.taskgather.R
import com.example.taskgather.conversion.ImageConversion
import com.example.taskgather.utils.FirebaseOperations
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.util.*

class UploadImagesFragment : Fragment() {

    lateinit var postImageView : ImageView
    private var imageBitmap: Bitmap? = null
    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_upload_images, container, false)
        val layout = rootView.findViewById<FrameLayout>(R.id.frameLayoutUploadImages)
        val postBtn = rootView.findViewById<Button>(R.id.btn_upload)
        val captionEditText = rootView.findViewById<TextInputEditText>(R.id.et_title)

        auth = FirebaseAuth.getInstance()
        postImageView = rootView.findViewById(R.id.newPostImageView)

        postBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            resultLauncher.launch(intent)

            postBtn.text = "Upload"
            postBtn.setOnClickListener {
                val currentTime = System.currentTimeMillis()
                val filename = UUID.randomUUID().toString()
                val ref = FirebaseStorage.getInstance().getReference("Notes/image").child(filename)

                ref.putBytes(ImageConversion.bitmapToByteArray(imageBitmap!!,500000))
                    .addOnSuccessListener {
                        ref.downloadUrl.addOnSuccessListener {
                            FirebaseOperations().saveImageNote(auth.currentUser!!.uid,filename,captionEditText.text.toString(),it.toString(),currentTime)
                            Toast.makeText(this.requireContext(), "Uploading...", Toast.LENGTH_SHORT).show()
                            layout.visibility = View.GONE
                        }

                    }
            }

        }

        return rootView
    }

    var imageUri: Uri? = null
    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data

            if (data != null){
                imageUri = data.data
                postImageView.setImageURI(imageUri)
                postImageView.invalidate()
                val dr =postImageView.drawable
                imageBitmap = dr.toBitmap()
                Picasso.get().load(imageUri).into(postImageView)
            }else{
                parentFragmentManager.popBackStackImmediate()
            }
        }else{
            parentFragmentManager.popBackStack()
        }
    }

}