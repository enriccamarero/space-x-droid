package com.ecamarero.spacex.ui.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer


fun <T : Any, L : LiveData<T>> LifecycleOwner.observeNonNull(liveData: L, body: (T) -> Unit) {
    liveData.observe(this, Observer { it?.let(body) })
}