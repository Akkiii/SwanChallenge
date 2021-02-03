package com.example.swantest.common.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

inline fun <T> LifecycleOwner.observe(data: LiveData<T>, crossinline block: (T?) -> Unit) {
    data.observe(this, Observer { block(it) })
}