package com.example.unsplash.data.models.dto

import com.google.gson.annotations.SerializedName

data class AnswerForChangeLikeDto(

    @SerializedName("photo")
    val likeInfoDto: LikeInfoDto

)