package com.lichenlzc.develop.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CommonAdapter: RecyclerView.Adapter<BaseViewHolder<BaseViewItem>>() {

    private val data = mutableListOf<BaseViewItem>()

    private val factory = mutableMapOf<Int, ViewHolderFactory>()

    fun addItem(item: BaseViewItem){
        data.add(item)
        notifyItemInserted(data.size-1)
    }

    fun addItems(items: List<BaseViewItem>){
        val start = items.size-1
        data.addAll(items)
        notifyItemRangeInserted(start, data.size-1)
    }

    @Suppress("UNCHECKED_CAST")
    override fun getItemViewType(position: Int): Int {
        val item = data[position]
        factory.putIfAbsent(item.layoutId(), ViewHolderFactory{view->
            return@ViewHolderFactory item.createViewHolder(view) as BaseViewHolder<BaseViewItem>
        })
        return item.layoutId()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<BaseViewItem> {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return factory[viewType]!!.createViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<BaseViewItem>, position: Int) {
        holder.onBindViewItem(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolderFactory(val create: (view: View)->BaseViewHolder<BaseViewItem>){
        fun createViewHolder(view: View): BaseViewHolder<BaseViewItem>{
            return create(view)
        }
    }
}