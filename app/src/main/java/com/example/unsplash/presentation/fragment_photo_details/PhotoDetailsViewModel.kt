package com.example.unsplash.presentation.fragment_photo_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unsplash.R
import com.example.unsplash._common.extensions.launchIO
import com.example.unsplash.data.storages.LoadIdStorage
import com.example.unsplash.presentation._common.models.LikeInfo
import com.example.unsplash.presentation._common.models.LoadingPhoto
import com.example.unsplash.presentation._common.models.PhotoToLoading
import com.example.unsplash.presentation._common.some_components.local_app_messenger.AppMessenger
import com.example.unsplash.presentation._external_dependencies.PhotosRepository
import com.example.unsplash.presentation.fragment_photo_details.models.PhotoDetails
import com.example.unsplash.presentation.fragment_photo_details.models.PhotographerInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

class PhotoDetailsViewModel(
    private val repository: PhotosRepository,
    private val loadIdStorage: LoadIdStorage,
    private val appMessenger: AppMessenger,
) : ViewModel() {

    var photoId = ""
	  private set

    private var _info: PhotoDetails? = null
    val info get() = checkNotNull(_info)

    private val photographerInfoMutStateFlow = MutableStateFlow<PhotographerInfo?>(null)
    private val currentPhotoIsLoadingMutStateFlow = MutableStateFlow<Boolean?>(null)
    private val isErrorOpenedPhotoDetailsMutStateFlow = MutableStateFlow(false)
    private val containsLocationMutStateFlow = MutableStateFlow<Boolean?>(null)
    private val downloadsInfoMutStateFlow = MutableStateFlow<String?>(null)
    private val likeInfoMutStateFlow = MutableStateFlow<LikeInfo?>(null)
    private val commonInfoMutStateFlow = MutableStateFlow<String?>(null)
    private val photoLinkMutStateFlow = MutableStateFlow<String?>(null)

    val isErrorOpenedPhotoDetailsStateFlow get() = isErrorOpenedPhotoDetailsMutStateFlow.asStateFlow()
    val currentPhotoIsLoadingStateFlow get() = currentPhotoIsLoadingMutStateFlow.asStateFlow()
    val photographerInfoStateFlow get() = photographerInfoMutStateFlow.asStateFlow()
    val containsLocationStateFlow get() = containsLocationMutStateFlow.asStateFlow()
    val downloadsInfoStateFlow get() = downloadsInfoMutStateFlow.asStateFlow()
    val commonInfoStateFlow get() = commonInfoMutStateFlow.asStateFlow()
    val photoLinkStateFlow get() = photoLinkMutStateFlow.asStateFlow()
    val likeInfoStateFlow get() = likeInfoMutStateFlow.asStateFlow()

    init {
	  observeToLoadingPhotos()
	  observeToNewLikeInfo()
    }

    private fun observeToLoadingPhotos() {
	  viewModelScope.launchIO(action = {
		loadIdStorage.observeToStorage().onEach { loadingPhotos: List<LoadingPhoto> ->
		    _info?.let {
			  val photoIsLoading = loadingPhotos.firstOrNull { it.photoId == info.id } != null
			  currentPhotoIsLoadingMutStateFlow.value = photoIsLoading
		    }
		}.collect()
	  }, onError = { it.printStackTrace() })
    }

    private fun observeToNewLikeInfo() {
	  viewModelScope.launchIO(
		action = { repository.listenToNewLikeInfo().collect(::checkLikeInfo) },
		onError = { it.printStackTrace() }
	  )
    }

    fun requestPhotoDetailsById() {
	  viewModelScope.launchIO(
		action = {
		    val photoDetails = repository.requestPhotoDetailsById(photoId)
		    withContext(Dispatchers.Main) { retrieveInformation(photoDetails) }
		},
		onError = {
		    isErrorOpenedPhotoDetailsMutStateFlow.value = true
		    appMessenger.sendMessage(R.string.occur_error_for_load_data)
		    it.printStackTrace()
		}
	  )
    }

    fun changeLike() {
	  val oldLikeInfo = likeInfoMutStateFlow.value ?: return
	  viewModelScope.launchIO(
		action = { repository.changeLike(oldLikeInfo) },
		onError = { it.printStackTrace() })
    }

    private suspend fun checkLikeInfo(newLikeInfo: LikeInfo?) {
	  val newInfo = newLikeInfo ?: return

	  val oldInfo = likeInfoMutStateFlow.value ?: return
	  if (newInfo.photoId != oldInfo.photoId) return

	  withContext(Dispatchers.Main) { likeInfoMutStateFlow.value = newInfo }
    }

    private fun retrieveInformation(photoDetails: PhotoDetails?) {
	  saveInfo(photoDetails) ?: return
	  retrievePhotoLink()
	  retrieveLikeInfo()
	  retrieveDownloadInfo()
	  retrievePhotographerInfo()
	  retrieveCommonInformation()
	  checkContainsPhotoLocation()
    }

    private fun retrieveDownloadInfo() {
	  downloadsInfoMutStateFlow.value = info.downloads
    }

    private fun checkContainsPhotoLocation() {
	  val latitude = info.location.latitude
	  val longitude = info.location.longitude

	  containsLocationMutStateFlow.value =
		when {
		    latitude == null || longitude == null -> false
		    latitude == 0.0 && longitude == 0.0 -> false
		    latitude.toInt() in -90 until 90 && longitude.toInt() in -180 until 180 -> true
		    else -> false
		}
    }

    private fun saveInfo(photoDetails: PhotoDetails?): Boolean? {
	  return if (photoDetails == null) {
		appMessenger.sendMessage(R.string.failed_to_display_data)
		null
	  } else {
		_info = photoDetails
		isLoadingPhoto()
		true
	  }
    }

    private fun isLoadingPhoto() {
	  viewModelScope.launchIO(action = {
		currentPhotoIsLoadingMutStateFlow.value = loadIdStorage.isLoadingPhoto(info.id)
	  }, onError = { it.printStackTrace() })
    }

    private fun retrieveCommonInformation() {
	  commonInfoMutStateFlow.value = info.commonInfo
    }

    private fun retrieveLikeInfo() {
	  likeInfoMutStateFlow.value = info.likeInfo
    }

    private fun retrievePhotoLink() {
	  photoLinkMutStateFlow.value = info.urlNormalPhoto
    }

    private fun retrievePhotographerInfo() {
	  photographerInfoMutStateFlow.value = info.photographerInfo
    }

    fun downloadPhoto() {
	  viewModelScope.launchIO(action = {
		val photoToLoading = PhotoToLoading(
		    id = info.id,
		    urlToLoading = info.urlRawPhoto,
		    urlToPreview = info.urlSmallPhoto,
		)
		repository.initStartLoading(photoToLoading)
	  }, onError = {
		currentPhotoIsLoadingMutStateFlow.value = false
		it.printStackTrace()
	  })
    }

    fun savePhotoId(id: String) {
	  photoId = id
    }

}

