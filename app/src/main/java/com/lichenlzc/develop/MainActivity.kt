package com.lichenlzc.develop

import android.graphics.RenderNode
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.LinearLayoutManager
import com.lichenlzc.develop.base.BaseActivity
import com.lichenlzc.develop.databinding.ActivityMainBinding
import com.lichenlzc.develop.databinding.VhMyBinding
import com.lichenlzc.develop.log.CLog
import com.lichenlzc.develop.recyclerview.BaseViewHolder
import com.lichenlzc.develop.recyclerview.BaseViewItem
import com.lichenlzc.develop.recyclerview.CommonAdapter
import java.lang.reflect.Field
import java.util.TreeMap

class MainActivity: BaseActivity() {

    private val viewBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val rvAdapter by lazy { CommonAdapter() }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        viewBinding.recyvlerView.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        rvAdapter.addItems(listOf(
            MyVI("00000000000000000000000"),
            MyVI("111111111111111111111"),
            MyVI("2222222222222222222222"),

        ))
        viewBinding.button.setOnClickListener {
//            rvAdapter.addItem(MyVI("对方是否开了个"), 0)
            rvAdapter.notifyItemChanged(0,MyVI::text)
        }
        viewBinding.button2.setOnClickListener {
            viewBinding.button.text = "changed"
            viewBinding.button.requestLayout()
            viewBinding.root.updateLayoutParams {
                height = 100
            }
        }
    }
}

class MyVH(view: View): BaseViewHolder<MyVI>(view){
    private val viewBinding by lazy { VhMyBinding.bind(itemView) }
    override fun onBindViewItem(item: MyVI) {
        viewBinding.textView.text = item.text
        layoutPosition
        adapterPosition
        oldPosition
    }

    override fun onBindViewItem(item: MyVI, payloads: MutableList<Any>) {
        super.onBindViewItem(item, payloads)
        payloads.forEach {
            when{
                it == MyVI::text ->{

                }
                it == MyVI::layoutId ->{

                }
            }
        }
    }

}

class MyVI(val text: String): BaseViewItem{
    override fun layoutId(): Int {
        return R.layout.vh_my
    }

    override fun createViewHolder(view: View): MyVH {
        return MyVH(view)
    }
}