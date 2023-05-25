package com.lichenlzc.develop

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.view.children
import androidx.core.widget.NestedScrollView

class FloatScrollView(context: Context, attributes: AttributeSet) :
    FrameLayout(context, attributes) {

     val floatViewContainer = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        layoutParams = generateLayoutParams(
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )
    }

     val contentViewContainer = MyScrollView(context).apply {
        layoutParams = generateLayoutParams(
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
    }

    init {
        addView(contentViewContainer)
        addView(floatViewContainer)
    }

    override fun addView(child: View) {
        contentViewContainer.addView(child)
    }

    override fun addView(child: View?, index: Int) {
        contentViewContainer.addView(child, index)

    }

    override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
        contentViewContainer.addView(child, params)

    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        contentViewContainer.addView(child, index, params)

    }

    override fun addView(child: View?, width: Int, height: Int) {
        contentViewContainer.addView(child, width, height)
    }

    inner class MyScrollView(context: Context) : NestedScrollView(context) {

        private val floatViewInfos = mutableListOf<FloatViewInfo>()

        private val tempRect = Rect()

        private val floatContainerRect = Rect()

        override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
            super.onScrollChanged(l, t, oldl, oldt)
            getRectInAncestor(floatViewContainer, this@FloatScrollView, floatContainerRect)
            floatViewInfos.forEach { floatViewInfo ->
                if(!floatViewInfo.isFloat){
                    getRectInAncestor(floatViewInfo.originView, this@FloatScrollView, tempRect)
                    if((tempRect.top-floatContainerRect.bottom)<0){
                        //float
                        floatViewInfo.float()
                    }
                }else{
                    getRectInAncestor(floatViewInfo.proxyView, this@FloatScrollView, tempRect)
                    if((tempRect.top-floatContainerRect.bottom)>0){
                        //float
                        floatViewInfo.unFloat()
                    }
                }
            }
        }

        override fun dispatchNestedPreScroll(
            dx: Int,
            dy: Int,
            consumed: IntArray?,
            offsetInWindow: IntArray?
        ): Boolean {
            if(dy>0 && this.canScrollVertically(-1)){
                scrollBy(0, dy)
                consumed?.set(0, dx)
                consumed?.set(1, dy)
                return true
            }else{
                return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow)
            }
        }

        override fun dispatchNestedPreScroll(
            dx: Int,
            dy: Int,
            consumed: IntArray?,
            offsetInWindow: IntArray?,
            type: Int
        ): Boolean {
            if(dy>0 && this.canScrollVertically(-1)){
                scrollBy(0, dy)
                consumed?.set(0, dx)
                consumed?.set(1, dy)
                return true
            }else{
                return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type)
            }
        }

        fun collectFloatViews(){
            floatViewInfos.clear()
            collectFloatViewsInner(contentViewContainer)
        }

        private fun collectFloatViewsInner(view: View){
            if(view.tag == "need_float"){
                floatViewInfos.add(FloatViewInfo(view, floatViewContainer))
            }else if(view is ViewGroup){
                view.children.forEach {
                    collectFloatViewsInner(it)
                }
            }
        }

        //child在ancestor坐标中的位置
        fun getRectInAncestor(child: View, ancestor: ViewGroup, rect: Rect){
            rect.set(0,0,child.width, child.height)
            var cd = child
            var parent = child.parent as? ViewGroup
            while (parent!=null && parent!=ancestor) {
                rect.offset(cd.left - parent.scrollX, cd.top - parent.scrollY)
                cd = parent
                parent = cd.parent as? ViewGroup
            }
        }

    }

    class FloatViewInfo(
        val originView: View,
        val floatParent: ViewGroup,
    ){

        var isFloat: Boolean = false
        private set

        var proxyView: View = View(originView.context)

        val originParent: ViewGroup = originView.parent as ViewGroup

        val originIndex = originParent.indexOfChild(originView)

        fun float(){
            proxyView.layoutParams = copyLayoutParams(originView.layoutParams)
            originParent.removeView(originView)
            originParent.addView(proxyView, originIndex)
            floatParent.addView(originView)
            isFloat = true
        }
        // TODO:  originView的布局参数变化
        // TODO:  findViewById的问题

        fun unFloat(){
            floatParent.removeView(originView)
            originParent.removeView(proxyView)
            originParent.addView(originView)
            isFloat = false
        }

        private fun copyLayoutParams(layoutParams: ViewGroup.LayoutParams): ViewGroup.LayoutParams{
            return when{
                layoutParams is MarginLayoutParams->{
                    MarginLayoutParams(layoutParams)
                }
                else->{
                    ViewGroup.LayoutParams(layoutParams)
                }
            }
        }
    }
}