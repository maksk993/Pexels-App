package com.maksk993.data.repository

import android.util.Log
import com.maksk993.data.models.okhttp.BASE_URL
import com.maksk993.data.models.okhttp.CURATED_PHOTOS_URL
import com.maksk993.data.models.okhttp.Client
import com.maksk993.data.models.okhttp.PHOTOS_LIMIT
import com.maksk993.data.models.okhttp.PhotosList
import com.maksk993.pexelsapp.domain.models.Collection
import com.maksk993.pexelsapp.domain.models.Photo
import com.maksk993.pexelsapp.domain.repository.PhotoRepository
import io.reactivex.Maybe
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class PhotoRepositoryImpl : PhotoRepository {
    private val client = Client.okHttpClient
    private val gson = Client.gson

    override fun getPhoto(title: Collection): Maybe<List<Photo>> {
        val request = Request.Builder()
            .url("$BASE_URL$CURATED_PHOTOS_URL?query=${title.title}&per_page=$PHOTOS_LIMIT")
            .build()

        return Maybe.create { emitter ->
            try {
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        Log.d("Internet", "Photos of ${title.title}: onFailure()")
                        emitter.onComplete()
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (response.isSuccessful){
                            val photos = gson.fromJson(response.body!!.string(), PhotosList::class.java).photos
                            emitter.onSuccess(photos)
                        }
                        else Log.d("Internet", "Photos of ${title.title}: onResponse() -> unsuccessful")
                    }
                })
            }
            catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }
}