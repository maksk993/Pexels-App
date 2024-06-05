package com.maksk993.pexelsapp.presentation.screens.bookmarks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maksk993.pexelsapp.domain.models.Photo
import com.maksk993.pexelsapp.domain.usecases.GetPhotosFromBookmarksUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class BookmarksViewModel(
    private val getPhotosFromBookmarksUseCase: GetPhotosFromBookmarksUseCase
) : ViewModel() {
    private val disposable = CompositeDisposable()

    private val _bookmarks: MutableLiveData<List<Photo?>> = MutableLiveData(ArrayList())
    val bookmarks: LiveData<List<Photo?>> = _bookmarks

    private var _shouldProgressBarBeVisible: MutableLiveData<Boolean> = MutableLiveData(true)
    var shouldProgressBarBeVisible: LiveData<Boolean> = _shouldProgressBarBeVisible

    fun getPhotosFromBookmarks(){
        _shouldProgressBarBeVisible.value = true
        disposable.add(getPhotosFromBookmarksUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { photos ->
                    _shouldProgressBarBeVisible.value = false
                    _bookmarks.value = photos
                },
                { throwable -> throwable.printStackTrace() }
            )
        )
    }

    override fun onCleared() {
        disposable.clear()
        disposable.dispose()
        super.onCleared()
    }
}