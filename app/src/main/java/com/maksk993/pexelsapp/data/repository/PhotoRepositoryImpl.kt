package com.maksk993.pexelsapp.data.repository

import android.util.Log
import com.maksk993.pexelsapp.data.models.BASE_URL
import com.maksk993.pexelsapp.data.models.CURATED_PHOTOS_URL
import com.maksk993.pexelsapp.data.models.Client
import com.maksk993.pexelsapp.data.models.PHOTOS_LIMIT
import com.maksk993.pexelsapp.data.models.PhotosList
import com.maksk993.pexelsapp.domain.models.PhotoCallback
import com.maksk993.pexelsapp.domain.models.Collection
import com.maksk993.pexelsapp.domain.repository.PhotoRepository
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class PhotoRepositoryImpl : PhotoRepository {
    private val client = Client.okHttpClient
    private val gson = Client.gson

    override fun getPhoto(title: Collection, callback: PhotoCallback) {
        val request = Request.Builder()
            .url("$BASE_URL$CURATED_PHOTOS_URL?query=${title.title}&per_page=$PHOTOS_LIMIT")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("Internet", "Photos of ${title.title}: onFailure()")
                callback.call(null)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful){
                    val photos = gson.fromJson(response.body!!.string(), PhotosList::class.java).photos
                    callback.call(photos)
                }
                else Log.d("Internet", "Photos of ${title.title}: onResponse() -> unsuccessful")
            }
        })
    }
}