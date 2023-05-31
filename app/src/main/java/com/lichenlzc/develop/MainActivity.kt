package com.lichenlzc.develop

import android.graphics.drawable.StateListDrawable
import android.os.Bundle
import com.lichenlzc.develop.base.BaseActivity
import com.lichenlzc.develop.databinding.ActivityMainBinding

class MainActivity: BaseActivity() {

    private val viewBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        viewBinding.button.setOnClickListener {
//            viewBinding.seekbar.isIndeterminate = true
            viewBinding.seekbar.progress = 50
        }
        viewBinding.seekbar.apply {
            setLightSpotDrawable(getDrawable(R.drawable.ic_jkdsahdfjks)!!)
            setLightSpot(listOf(0.1f,0.3f, 0.7f))
        }
//        StateListDrawable().setLevel()
    }
}