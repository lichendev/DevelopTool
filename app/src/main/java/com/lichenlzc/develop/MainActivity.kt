package com.lichenlzc.develop

import android.content.res.Configuration
import android.graphics.Rect
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lichenlzc.develop.base.BaseActivity
import com.lichenlzc.develop.databinding.ActivityMainBinding

@RequiresApi(Build.VERSION_CODES.S)
class MainActivity: BaseActivity() {

    private val viewBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    val decoder by lazy { BigBitmapWrapper(this) }

    private val liveData = MutableLiveData<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        viewBinding.button.setOnClickListener {
            val bitmp = decoder.decode(Rect(100,100,200,200))
            viewBinding.image.setImageBitmap(bitmp)
        }
        viewBinding.seekbar.apply {
            setLightSpotDrawable(getDrawable(R.drawable.ic_jkdsahdfjks)!!)
            setLightSpot(listOf(0.1f,0.3f, 0.7f))
        }
        liveData.observe(this,object :Observer<Int>{
            override fun onChanged(t: Int?) {

            }
        })
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}