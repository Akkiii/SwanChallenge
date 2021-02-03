package com.example.swantest.common

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BindableViewHolder<out B : ViewDataBinding, in VM>(view: View) : RecyclerView.ViewHolder(view) {

    val binding: B? by lazy { DataBindingUtil.bind<B>(view) }

    abstract fun bind(viewModel: VM)

    fun setViewModel(viewModel: VM) {
        bind(viewModel)
        binding?.executePendingBindings()
    }

}
