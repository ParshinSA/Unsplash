package com.example.unsplash.data.paging_source

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.unsplash.data.data_sources.photo.PhotoDatasourceLocal
import com.example.unsplash.presentation.fragment_photos_main.models.Photo
import com.example.unsplash.presentation.fragment_photos_main.pagination.PhotosLocalPagerFactory
import kotlinx.coroutines.flow.Flow

class PhotosLocalPagerFactoryImpl(
    private val datasource: PhotoDatasourceLocal
) : PhotosLocalPagerFactory {

    override fun create(): Flow<PagingData<Photo>> {
	  return Pager(MainItemsDisplayPagingSource.pagerConfig()) {
		ItemsDisplayPagingSourceLocal(request = {
		    datasource.requestPhotos()
		})
	  }.flow
    }

}