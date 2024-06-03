package com.maksk993.pexelsapp.presentation.screens.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maksk993.pexelsapp.domain.models.Photo
import com.maksk993.pexelsapp.domain.usecases.AddPhotoToBookmarks
import com.maksk993.pexelsapp.domain.usecases.DeletePhotoFromBookmarks
import com.maksk993.pexelsapp.domain.usecases.WasPhotoAddedToBookmarks
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DetailsViewModel(
    private val addPhotoToBookmarks: AddPhotoToBookmarks,
    private val wasPhotoAddedToBookmarks: WasPhotoAddedToBookmarks,
    private val deletePhotoFromBookmarks: DeletePhotoFromBookmarks
) : ViewModel() {
    private val disposable = CompositeDisposable()

    private var _shouldProgressBarBeVisible: MutableLiveData<Boolean> = MutableLiveData(true)
    var shouldProgressBarBeVisible: LiveData<Boolean> = _shouldProgressBarBeVisible

    fun addPhotoToBookmarks(photo: Photo){
        disposable.add(addPhotoToBookmarks.execute(photo)
            .subscribeOn(Schedulers.io())
            .subscribe()
        )
    }

    fun deletePhotoFromBookmarks(photo: Photo){
        disposable.add(deletePhotoFromBookmarks.execute(photo)
            .subscribeOn(Schedulers.io())
            .subscribe()
        )
    }

    private val _wasAdded: MutableLiveData<Boolean> = MutableLiveData(false)
    val wasAdded: LiveData<Boolean> = _wasAdded

    fun wasPhotoAddedToBookmarks(photo: Photo) {
        _shouldProgressBarBeVisible.value = true
        disposable.add(wasPhotoAddedToBookmarks.execute(photo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { bool ->
                    _shouldProgressBarBeVisible.value = false
                    _wasAdded.value = bool
                },
                { throwable -> throwable.printStackTrace() }
            )
        )
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}