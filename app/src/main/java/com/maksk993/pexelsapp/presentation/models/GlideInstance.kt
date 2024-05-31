package com.maksk993.pexelsapp.presentation.models

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.maksk993.pexelsapp.R
import com.maksk993.pexelsapp.domain.models.Photo

object GlideInstance {
    private val placeholder = R.drawable.placeholder

    fun loadImage(context: Context,
                  photo: Photo,
                  imageView: ImageView,
                  listener: RequestListener<Drawable>? = null
    ) {
        Glide.with(context)
             .load(photo.src.original)
             .placeholder(placeholder)
             .listener(listener)
             .into(imageView)
    }

    fun downloadToStorage(context: Context, photo: Photo) {
        Glide.with(context)
             .asBitmap()
             .load(photo.src.original)
             .into(object : CustomTarget<Bitmap>(){
                 override fun onResourceReady(
                     resource: Bitmap,
                     transition: Transition<in Bitmap>?
                 ) {
                     val values = ContentValues().apply {
                         put(MediaStore.Images.Media.DISPLAY_NAME, "${photo.photographer}.jpg")
                         put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                     }

                     val imageUri = context.contentResolver.insert(
                         MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                         values
                     )

                     imageUri?.let {
                         val outputStream = context.contentResolver.openOutputStream(it)
                         if (outputStream != null) {
                             resource.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                             Toast.makeText(context, "Photo saved to gallery", Toast.LENGTH_SHORT).show()
                         }
                         outputStream?.close()
                     }
                 }

                 override fun onLoadCleared(placeholder: Drawable?) {}
             })
    }

}