package com.example.unsplash._common.extensions

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

inline fun CoroutineScope.launchIO(
    crossinline action: suspend () -> Unit,
    crossinline onError: suspend (Throwable) -> Unit,
): Job {

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
	  launch(Dispatchers.Main) {
		onError.invoke(throwable)
	  }
    }

    return this.launch(exceptionHandler + Dispatchers.IO) {
	  action.invoke()
    }
}

inline fun CoroutineScope.launchMain(
    crossinline action: suspend () -> Unit,
    crossinline onError: suspend (Throwable) -> Unit,
): Job {

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
	  launch(Dispatchers.Main) {
		onError.invoke(throwable)
	  }
    }

    return this.launch(exceptionHandler + Dispatchers.Main) {
	  action.invoke()
    }
}

fun <T> CoroutineScope.multiCollectToFlow(vararg flows: Flow<T>) {
    flows.forEach { flow: Flow<T> ->
	  this.launchMain(
		action = { flow.collect() },
		onError = { it.printStackTrace() }
	  )
    }
}