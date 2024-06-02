package com.maksk993.pexelsapp.presentation.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maksk993.pexelsapp.domain.models.Collection
import com.maksk993.pexelsapp.domain.models.Photo
import com.maksk993.pexelsapp.domain.usecases.GetCuratedPhotos
import com.maksk993.pexelsapp.domain.usecases.GetFeaturedCollections
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel(
    private val getFeaturedCollections: GetFeaturedCollections,
    private val getCuratedPhotos: GetCuratedPhotos,
) : ViewModel() {

    private val disposable: CompositeDisposable = CompositeDisposable()

    init {
        getFeaturedCollections()
        getPhotos()
    }

    private val _featuredCollections: MutableLiveData<List<Collection>> = MutableLiveData(ArrayList())
    val featuredCollections: LiveData<List<Collection>> = _featuredCollections

    private val _photos: MutableLiveData<List<Photo>?> = MutableLiveData(ArrayList())
    val photos: LiveData<List<Photo>?> = _photos

    fun getFeaturedCollections() {
        disposable.add(getFeaturedCollections.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { collections -> _featuredCollections.value = collections },
                { throwable -> throwable.printStackTrace() }
            )
        )
    }

    fun getPhotos(collection: Collection = Collection("Popular")){
        disposable.add(getCuratedPhotos.execute(collection)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { photos -> _photos.value = photos },
                { throwable -> throwable.printStackTrace() },
                { _photos.value = null }
            )
        )
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}