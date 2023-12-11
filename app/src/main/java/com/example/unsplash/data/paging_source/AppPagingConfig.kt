package com.example.unsplash.data.paging_source

data class AppPagingConfig(
    val pageNumber: Int,
    val pageSize: Int,
    val param: String = "",
)
