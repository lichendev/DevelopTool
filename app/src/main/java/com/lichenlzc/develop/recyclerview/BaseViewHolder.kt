package com.lichenlzc.develop.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<VI: BaseViewItem>(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun onBindViewItem(item: VI)

    open fun onBindViewItem(item: VI, payloads: MutableList<Any>){

    }

    fun onAttachedToWindow(){

    }

    fun onDetachedFromWindow(){

    }
}