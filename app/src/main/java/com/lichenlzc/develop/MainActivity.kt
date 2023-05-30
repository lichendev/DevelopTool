package com.lichenlzc.develop

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
    }
}