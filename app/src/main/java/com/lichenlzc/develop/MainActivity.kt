package com.lichenlzc.develop

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.res.Configuration
import android.graphics.Rect
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lichenlzc.develop.base.BaseActivity
import com.lichenlzc.develop.databinding.ActivityMainBinding
import com.lichenlzc.develop.log.CLog
import java.security.Provider.Service
import java.util.Hashtable

@RequiresApi(Build.VERSION_CODES.S)
class MainActivity: BaseActivity() {

    private val viewBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    val decoder by lazy { BigBitmapWrapper(this) }

    private val liveData = MutableLiveData<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        viewBinding.button.setOnClickListener {
            startActivity(Intent(this, MainActivity2::class.java))
            it.postDelayed(object : Runnable{
                override fun run() {
                    CLog.d("requestLayout")
                    viewBinding.seekbar.requestLayout()
                }
            },5000)
        }
    }

    override fun onStop() {
        super.onStop()
        CLog.d("onStop=${window.decorView.visibility}")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}