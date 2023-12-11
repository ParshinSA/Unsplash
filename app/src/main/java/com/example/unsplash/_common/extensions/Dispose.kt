package com.example.unsplash._common.extensions

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun Disposable.autoAdd(rxContainer: CompositeDisposable) {
    rxContainer.add(this)
}