package com.maksk993.pexelsapp.presentation.models.searchview

import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import io.reactivex.Observable

object GetSearchViewObservable {
    var shouldOnQueryTextChangeBeCalled: Boolean = true

    fun execute(searchView: SearchView): Observable<String> {
        return Observable.create { emitter ->
            searchView.setOnQueryTextListener(object : OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    emitter.onNext(query ?: "")
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return if (shouldOnQueryTextChangeBeCalled) onQueryTextSubmit(newText)
                    else {
                        shouldOnQueryTextChangeBeCalled = true
                        false
                    }
                }
            })

            searchView.setOnClickListener {
                shouldOnQueryTextChangeBeCalled = true
            }
        }
    }
}