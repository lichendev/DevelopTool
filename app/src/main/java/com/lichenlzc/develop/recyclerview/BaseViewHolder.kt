package com.lichenlzc.develop.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<VI: BaseViewItem>(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun onBindViewItem(item: VI)

    fun onAttachedToWindow(){

    }

    fun onDetachedFromWindow(){

    }
}