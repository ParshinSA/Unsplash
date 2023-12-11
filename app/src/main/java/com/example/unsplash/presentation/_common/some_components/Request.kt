package com.example.unsplash.presentation._common.some_components


sealed interface Request {
    val param: String

    //--------------Available type requests---------------------
    sealed interface PhotosRequest : Request

    sealed interface CollectionsRequest : Request
    //----------------------------------------------------------


    //--------------Variable requests---------------------------
    class PhotosByCollectionIdRequest(id: String) : Request, PhotosRequest {
	  override val param = id
    }

    class SearchRequest(query: String) : Request, PhotosRequest, CollectionsRequest {
	  override val param = query
    }

    class LikedPhotosRequest(userName: String) : Request, PhotosRequest {
	  override val param: String = userName
    }

    object SimpleRequest : Request, PhotosRequest, CollectionsRequest {
	  override val param: String = PARAMS_STUB
    }
    //----------------------------------------------------------

    companion object {
	  private const val PARAMS_STUB = ""
    }

}