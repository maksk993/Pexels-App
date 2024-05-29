package com.maksk993.data.repository

import android.util.Log
import com.maksk993.data.models.okhttp.BASE_URL
import com.maksk993.data.models.okhttp.COLLECTIONS_LIMIT
import com.maksk993.data.models.okhttp.Client
import com.maksk993.data.models.okhttp.CollectionsList
import com.maksk993.data.models.okhttp.FEATURED_COLLECTIONS_URL
import com.maksk993.pexelsapp.domain.models.CollectionCallback
import com.maksk993.pexelsapp.domain.repository.CollectionRepository
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class CollectionRepositoryImpl : CollectionRepository {
    private val client = Client.okHttpClient
    private val gson = Client.gson
    private val request = Request.Builder()
        .url("$BASE_URL$FEATURED_COLLECTIONS_URL?per_page=$COLLECTIONS_LIMIT")
        .build()

    override fun getFeaturedCollections(callback: CollectionCallback) {
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("Internet", "Titles: onFailure()")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful){
                    val collections = gson.fromJson(response.body!!.string(), CollectionsList::class.java).collections
                    callback.call(collections)
                }
                else Log.d("Internet", "Titles: onResponse() -> unsuccessful")
            }
        })
    }
}