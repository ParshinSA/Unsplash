package com.example.unsplash._common.deps_inject.providers

import okhttp3.Interceptor

class HttpLoggingInterceptorProvider(
    val interceptor: Interceptor
)