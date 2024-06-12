package com.maksk993.pexelsapp.presentation.screens.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maksk993.pexelsapp.domain.models.Photo
import com.maksk993.pexelsapp.domain.usecases.AddPhotoToBookmarksUseCase
import com.maksk993.pexelsapp.domain.usecases.DeletePhotoFromBookmarksUseCase
import com.maksk993.pexelsapp.domain.usecases.GetFileSizeOfPhotoUseCase
import com.maksk993.pexelsapp.domain.usecases.WasPhotoAddedToBookmarksUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlin.math.floor

class DetailsViewModel(
    private val addPhotoToBookmarksUseCase: AddPhotoToBookmarksUseCase,
    private val wasPhotoAddedToBookmarksUseCase: WasPhotoAddedToBookmarksUseCase,
    private val deletePhotoFromBookmarksUseCase: DeletePhotoFromBookmarksUseCase,
    private val getFileSizeOfPhotoUseCase: GetFileSizeOfPhotoUseCase
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private var _shouldProgressBarBeVisible: MutableLiveData<Boolean> = MutableLiveData(true)
    var shouldProgressBarBeVisible: LiveData<Boolean> = _shouldProgressBarBeVisible

    fun addPhotoToBookmarks(photo: Photo){
        compositeDisposable.add(addPhotoToBookmarksUseCase.execute(photo)
            .subscribeOn(Schedulers.io())
            .subscribe()
        )
    }

    fun deletePhotoFromBookmarks(photo: Photo){
        compositeDisposable.add(deletePhotoFromBookmarksUseCase.execute(photo)
            .subscribeOn(Schedulers.io())
            .subscribe()
        )
    }

    private val _wasAdded: MutableLiveData<Boolean> = MutableLiveData(false)
    val wasAdded: LiveData<Boolean> = _wasAdded

    fun wasPhotoAddedToBookmarks(photo: Photo) {
        _shouldProgressBarBeVisible.value = true
        compositeDisposable.add(wasPhotoAddedToBookmarksUseCase.execute(photo)
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

    private val _fileSizeMegaBytes: MutableLiveData<Float> = MutableLiveData(0F)
    val fileSizeMegaBytes: LiveData<Float> = _fileSizeMegaBytes

    fun getFileSize(photo: Photo?){
        _fileSizeMegaBytes.value = 0F
        photo?.let {
            compositeDisposable.add(getFileSizeOfPhotoUseCase.execute(photo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { sizeBytes ->
                        _fileSizeMegaBytes.value = floor((sizeBytes.toFloat() / 1_000_000) * 100) / 100
                    },
                    { throwable -> throwable.printStackTrace() }
                )
            )
        }
    }

    override fun onCleared() {
        compositeDisposable.clear()
        compositeDisposable.dispose()
        super.onCleared()
    }
}