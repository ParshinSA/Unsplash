package com.example.unsplash._common.helpers.local_like_state

import com.example.unsplash.data.models.converters.toUiModel
import com.example.unsplash.data.network.unsplash.api.LikeServiceRemote
import com.example.unsplash.presentation._common.models.LikeInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class LocalLikeStateProviderImpl(
    private val likeServiceRemote: LikeServiceRemote
) : LocalLikeStateProvider {

    private val collectorLikeInfo = MutableStateFlow<LikeInfo?>(null)

    private suspend fun emitNewLikeInfo(info: LikeInfo) {
	  withContext(Dispatchers.Main) { collectorLikeInfo.emit(info) }
    }

    override suspend fun changeLike(oldLikeInfo: LikeInfo) {
	  val photoId = oldLikeInfo.photoId
	  val targetLikeState = !oldLikeInfo.likedByUser

	  val response = if (targetLikeState) likeServiceRemote.likePhoto(photoId)
	  else likeServiceRemote.unlikePhoto(photoId)

	  val newLikeInfo = if (response.isSuccessful)
		response.body()?.likeInfoDto.toUiModel() else null

	  emitNewLikeInfo(newLikeInfo ?: oldLikeInfo)
    }

    override suspend fun listenToNewLikeInfo(): StateFlow<LikeInfo?> {
	  return collectorLikeInfo.asStateFlow()
    }

}