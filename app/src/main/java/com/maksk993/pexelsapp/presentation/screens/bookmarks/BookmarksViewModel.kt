package com.maksk993.pexelsapp.presentation.screens.bookmarks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maksk993.pexelsapp.domain.models.Photo
import com.maksk993.pexelsapp.domain.usecases.GetPhotosFromBookmarks
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class BookmarksViewModel(
    private val getPhotosFromBookmarks: GetPhotosFromBookmarks
) : ViewModel() {
    private val disposable = CompositeDisposable()

    private val _bookmarks: MutableLiveData<List<Photo?>> = MutableLiveData(ArrayList())
    val bookmarks: LiveData<List<Photo?>> = _bookmarks

    fun getPhotosFromBookmarks(){
        disposable.add(getPhotosFromBookmarks.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { photos -> _bookmarks.value = photos },
                { throwable -> throwable.printStackTrace() }
            )
        )
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}