package com.example.unsplash._common.helpers.local_like_state

import com.example.unsplash.presentation._common.models.LikeInfo
import kotlinx.coroutines.flow.StateFlow


interface LocalLikeStateProvider {

    suspend fun listenToNewLikeInfo(): StateFlow<LikeInfo?>

    suspend fun changeLike(oldLikeInfo: LikeInfo)

}
