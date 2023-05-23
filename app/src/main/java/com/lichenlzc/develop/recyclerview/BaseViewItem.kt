package com.lichenlzc.develop.recyclerview

import android.view.View

interface BaseViewItem{

    fun layoutId(): Int

    fun createViewHolder(view: View): BaseViewHolder<*>
}