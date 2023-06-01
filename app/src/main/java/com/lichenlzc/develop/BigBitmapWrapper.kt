package com.lichenlzc.develop

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapRegionDecoder
import android.graphics.Rect
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.S)
class BigBitmapWrapper(val context: Context) {

    private val bitmapInputStream by lazy { context.assets.open("Untitled.png") }

    val decoder by lazy { BitmapRegionDecoder.newInstance(bitmapInputStream, false)!! }

    fun decode(rect: Rect): Bitmap{
        return decoder.decodeRegion(rect, null)
    }

}