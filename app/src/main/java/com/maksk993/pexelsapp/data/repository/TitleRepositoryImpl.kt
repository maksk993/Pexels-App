package com.maksk993.pexelsapp.data.repository

import android.util.Log
import com.maksk993.pexelsapp.domain.models.Title
import com.maksk993.pexelsapp.domain.models.TitleCallback
import com.maksk993.pexelsapp.domain.repository.TitleRepository
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import com.maksk993.pexelsapp.data.models.*


class TitleRepositoryImpl : TitleRepository {
    private val client = Client.instance
    private val request = Request.Builder()
        .url("$BASE_URL$FEATURED_COLLECTIONS_URL?per_page=$TITLES_LIMIT")
        .addHeader("Authorization", API_KEY)
        .build()

    override fun getFeaturedTitles(callback: TitleCallback) {
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("Internet", "Titles: onFailure()")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful){
                    val collections = JSONObject(response.body!!.string()).getJSONArray("collections")
                    for (i in 0 until collections.length()){
                        val title = collections.getJSONObject(i).getString("title")
                        callback.call(Title(title))
                    }
                }
                else Log.d("Internet", "Titles: onResponse() -> unsuccessful")
            }
        })
    }
}