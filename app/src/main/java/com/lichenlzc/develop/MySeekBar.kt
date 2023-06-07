package com.lichenlzc.develop

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatSeekBar
import com.lichenlzc.develop.log.CLog

class MySeekBar@JvmOverloads constructor (context: Context, attributes: AttributeSet?=null, defStyleAttr: Int=0)
    :AppCompatSeekBar(context, attributes, defStyleAttr) {

    private var mIsDragging = false

    private var lightSpotDrawable: Drawable? = null

    private val lightSpots = mutableListOf<Float>()

    private val lightSpotsPosition = mutableListOf<Int>()

    private val lightSpotsRange = mutableListOf<IntRange>()


    fun setLightSpotDrawable(drawable: Drawable){
        lightSpotDrawable = drawable
        if(!isLaidOut) return
        computeLightSpotDrawableRect()
        computeLightSpotsRange()
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        CLog.d("onMeasure")
    }

    fun setLightSpot(list: List<Float>){
        lightSpots.clear()
        lightSpots.addAll(list)
        if(!isLaidOut) return
        computeLightSpotsPosition(width - paddingLeft-paddingRight)
        computeLightSpotsRange()
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        computeLightSpotsPosition(w - paddingLeft-paddingRight)
        computeLightSpotDrawableRect()
        computeLightSpotsRange()
    }

    private fun computeLightSpotsPosition(width: Int){
        lightSpotsPosition.clear()
        lightSpots.forEach { percent->
            val p = (width*percent).toInt()
            lightSpotsPosition.add(p)
        }
    }

    private fun computeLightSpotDrawableRect(){
        val drawable = lightSpotDrawable?:return
        val dw = drawable.intrinsicWidth.coerceIn(1,(width-paddingLeft-paddingRight).coerceAtLeast(2))
        val dh = drawable.intrinsicHeight.coerceIn(1,(height-paddingBottom-paddingTop).coerceAtLeast(2))
        drawable.setBounds(0,0, dw,dh)
    }

    private fun computeLightSpotsRange(){
        var dw = lightSpotDrawable?.bounds?.width()?:return
        dw = 50
        lightSpotsRange.clear()
        lightSpotsPosition.forEach { p->
            lightSpotsRange.add(IntRange(p-dw,p+dw))
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawLightSpot(canvas!!)
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        if(lightSpotDrawable?.isStateful == true){
            lightSpotDrawable?.state = drawableState
        }
        computeLightSpotDrawableRect()
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }




    private fun drawLightSpot(canvas: Canvas){
        if(lightSpotsPosition.isEmpty()) return
        val drawable = lightSpotDrawable?:return
        val dw = drawable.bounds.width()
        val dh = drawable.bounds.height()
        val saveCount = canvas.save()
        var lastPosition = 0
        canvas.translate(-dw/2f,(height-dh)/2f)
        lightSpotsPosition.forEach { position->
            canvas.translate((position-lastPosition).toFloat(), 0f)
            drawable.draw(canvas)
            lastPosition = position
        }
        canvas.restoreToCount(saveCount)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val e = event?:return true
        if(e.actionMasked==MotionEvent.ACTION_UP && !mIsDragging) {
            val x = e.x
            lightSpotsRange.forEachIndexed { index, intRange ->
                if(x.toInt() in intRange){
                    CLog.d("lightSpotsRange index=$index, r=$intRange")
                    val ne = MotionEvent.obtain(e)
                    ne.offsetLocation((intRange.first+intRange.last)/2f-x,0f)
                    val result = super.onTouchEvent(ne)
                    ne.recycle()
                    return result
                }
            }
        }
        return super.onTouchEvent(event)
    }
}