package com.lichenlzc.develop

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.LinearLayoutManager
import com.lichenlzc.develop.base.BaseActivity
import com.lichenlzc.develop.databinding.ActivityMainBinding
import com.lichenlzc.develop.databinding.VhMyBinding
import com.lichenlzc.develop.log.CLog
import com.lichenlzc.develop.recyclerview.BaseViewHolder
import com.lichenlzc.develop.recyclerview.BaseViewItem
import com.lichenlzc.develop.recyclerview.CommonAdapter
import com.lichenlzc.develop.tool.dp2px
import com.lichenlzc.develop.tool.getScreenHeight

class MainActivity: BaseActivity() {

    private val viewBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val rvAdapter by lazy { CommonAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        viewBinding.recyvlerView.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        viewBinding.recyvlerView.updateLayoutParams {
            height = getScreenHeight()
        }
        rvAdapter.addItems(listOf(
            MyVI("00000000000000000000000"),
            MyVI("111111111111111111111"),
            MyVI("2222222222222222222222"),
            MyVI("333333333333333333333333"),
            MyVI("444444444444444444444444"),
            MyVI("555555555555555555555555"),
            MyVI("66666666666666666"),
            MyVI("777777777777777"),
            MyVI("8888888888888888888888"),
            MyVI("9"),
            MyVI("10"),
            MyVI("555555555555555555555555"),
            MyVI("66666666666666666"),
            MyVI("777777777777777"),
            MyVI("8888888888888888888888"),
            MyVI("9"),
            MyVI("10"),
        ))
        viewBinding.root.contentViewContainer.collectFloatViews()
        viewBinding.root.contentViewContainer.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            val detalY = (scrollY - oldScrollY)
            val newHeight = (viewBinding.button.layoutParams.height-detalY).coerceIn(200, 825)
            if(newHeight!=viewBinding.button.layoutParams.height){
                viewBinding.button.updateLayoutParams {
                    height = newHeight
                }
            }
        }
//        findViewById<View>(R.id.button1)
        CLog.d("oncreate")

    }
}

class MyVH(view: View): BaseViewHolder<MyVI>(view){
    private val viewBinding by lazy { VhMyBinding.bind(itemView) }
    override fun onBindViewItem(item: MyVI) {
        viewBinding.textView.text = item.text
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