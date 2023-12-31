package com.example.unsplash._common.extensions

import android.os.Bundle
import androidx.fragment.app.Fragment

fun <T : Fragment> T.withArguments(action: Bundle.() -> Unit): T {
    return apply {
	  arguments = Bundle().apply(action)
    }
}