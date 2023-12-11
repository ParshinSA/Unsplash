package com.example.unsplash._common.extensions

import android.view.animation.Animation

// раскоментировать по необходимости
//fun Animation.addStartAnimationListener(afterStart: (() -> Unit)){
//    this.invokeAfterAnimationCallbacks(afterStart = afterStart)
//}

fun Animation.addEndAnimationListener(afterEnd: (() -> Unit)) {
    this.invokeAfterAnimationCallbacks(afterEnd = afterEnd)
}
// раскоментировать по необходимости
//fun Animation.addRepeatAnimationListener(afterRepeat: (() -> Unit)){
//    this.invokeAfterAnimationCallbacks(afterRepeat = afterRepeat)
//}

private fun Animation.invokeAfterAnimationCallbacks(
    afterStart: (() -> Unit)? = null,
    afterEnd: (() -> Unit)? = null,
    afterRepeat: (() -> Unit)? = null,
) {
    this.setAnimationListener(object :
	  Animation.AnimationListener {
	  override fun onAnimationStart(p0: Animation?) {
		afterStart?.invoke()
	  }

	  override fun onAnimationEnd(p0: Animation?) {
		afterEnd?.invoke()
	  }

	  override fun onAnimationRepeat(p0: Animation?) {
		afterRepeat?.invoke()
	  }
    })
}