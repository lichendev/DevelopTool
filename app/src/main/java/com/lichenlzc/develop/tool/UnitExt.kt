package com.lichenlzc.develop.tool

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import kotlin.math.roundToInt

fun Float.dp2px(): Float {
    val resources = Resources.getSystem()
    return resources.displayMetrics.density * this
}

fun Int.dp2px(): Int {
    val f = this.toFloat()
    val resources = Resources.getSystem()
    return (f * resources.displayMetrics.density + 0.5f).toInt()
}

fun String.getColor(): Int {
    return Color.parseColor(this)
}

fun Context.getScreenHeight(): Int {
    val bool: Int
    val resources = this.resources
    val displayMetrics = resources.displayMetrics
    if (displayMetrics != null) {
        bool = displayMetrics.heightPixels
    } else {
        bool = 0
    }
    return bool
}

fun Context.getScreenWidth(): Int {
    val bool: Int
    val resources = this.resources
    val displayMetrics = resources.displayMetrics
    if (displayMetrics != null) {
        bool = displayMetrics.widthPixels
    } else {
        bool = 0
    }
    return bool
}

fun Float.px2dp(paramContext: Context, ): Int {
    val resources = paramContext.resources
    return (this / resources.displayMetrics.density).roundToInt()
}

//fun toInt(paramBoolean: Boolean): Int {
//    return paramBoolean
//}