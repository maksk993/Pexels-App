package com.maksk993.pexelsapp.presentation.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maksk993.pexelsapp.domain.models.FeaturedCollection
import com.maksk993.pexelsapp.domain.models.Photo
import com.maksk993.pexelsapp.domain.usecases.GetCuratedPhotosUseCase
import com.maksk993.pexelsapp.domain.usecases.GetFeaturedCollectionsUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel(
    private val getFeaturedCollectionsUseCase: GetFeaturedCollectionsUseCase,
    private val getCuratedPhotosUseCase: GetCuratedPhotosUseCase,
) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private var _shouldProgressBarBeVisible: MutableLiveData<Boolean> = MutableLiveData(true)
    var shouldProgressBarBeVisible: LiveData<Boolean> = _shouldProgressBarBeVisible

    init {
        getFeaturedCollections()
        getPhotos()
    }

    private val _featuredCollections: MutableLiveData<List<FeaturedCollection>> = MutableLiveData(ArrayList())
    val featuredCollections: LiveData<List<FeaturedCollection>> = _featuredCollections

    private val _photos: MutableLiveData<List<Photo>?> = MutableLiveData(ArrayList())
    val photos: LiveData<List<Photo>?> = _photos

    fun getFeaturedCollections() {
        compositeDisposable.add(getFeaturedCollectionsUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { collections -> _featuredCollections.value = collections },
                { throwable -> throwable.printStackTrace() }
            )
        )
    }

    fun getPhotos(collection: FeaturedCollection = FeaturedCollection("Popular")){
        _shouldProgressBarBeVisible.value = true
        compositeDisposable.add(getCuratedPhotosUseCase.execute(collection)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {  photos ->
                    _shouldProgressBarBeVisible.value = false
                    _photos.value = photos
                },
                { throwable -> throwable.printStackTrace() },
                {
                    _shouldProgressBarBeVisible.value = false
                    _photos.value = null
                }
            )
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        compositeDisposable.dispose()
        super.onCleared()
    }
}