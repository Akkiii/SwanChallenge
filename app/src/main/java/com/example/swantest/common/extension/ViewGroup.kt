package com.example.swantest.common.extension

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(@LayoutRes layoutResId: Int) =
    LayoutInflater.from(context).inflate(layoutResId, this, false)
