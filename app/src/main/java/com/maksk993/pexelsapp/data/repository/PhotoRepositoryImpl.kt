package com.maksk993.pexelsapp.data.repository

import android.util.Log
import com.maksk993.pexelsapp.data.models.API_KEY
import com.maksk993.pexelsapp.data.models.BASE_URL
import com.maksk993.pexelsapp.data.models.CURATED_PHOTOS_URL
import com.maksk993.pexelsapp.data.models.Client
import com.maksk993.pexelsapp.data.models.PHOTOS_LIMIT
import com.maksk993.pexelsapp.domain.models.Photo
import com.maksk993.pexelsapp.domain.models.PhotoCallback
import com.maksk993.pexelsapp.domain.models.Title
import com.maksk993.pexelsapp.domain.repository.PhotoRepository
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class PhotoRepositoryImpl : PhotoRepository {
    private val client = Client.instance

    override fun getPhoto(title: Title, callback: PhotoCallback) {
        val request = Request.Builder()
            .url("$BASE_URL$CURATED_PHOTOS_URL?query=${title.title}&per_page=$PHOTOS_LIMIT")
            .addHeader("Authorization", API_KEY)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("Internet", "Photos of ${title.title}: onFailure()")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful){
                    val photos = JSONObject(response.body!!.string()).getJSONArray("photos")
                    for (i in 0 until photos.length()){
                        val photo = photos.getJSONObject(i)
                        callback.call(
                            Photo(
                                id = photo.getInt("id"),
                                url = photo.getJSONObject("src").getString("original"),
                                photographer = photo.getString("photographer")
                            )
                        )
                    }
                }
                else Log.d("Internet", "Photos of ${title.title}: onResponse() -> unsuccessful")
            }

        })

    }
}