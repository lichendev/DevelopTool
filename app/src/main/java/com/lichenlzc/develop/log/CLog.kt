package com.lichenlzc.develop.log

import android.content.Context
import android.util.Log
import android.widget.Toast

object CLog {

    fun d(paramObject: Any) {
        Log.d("test_lqq", paramObject.toString())
    }

    fun dAndToast(paramObject: Any, paramContext: Context) {
        d(paramObject)
        Toast.makeText(paramContext, paramObject.toString(), Toast.LENGTH_SHORT).show()
    }
}
