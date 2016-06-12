package com.spaceo.afedyanov.space_otest.view.customviews

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.PointF
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView

/**
 * Created by Alexandr on 11.06.2016.
 */
class ZoomImageView(context: Context, attributeSet: AttributeSet): ImageView(context, attributeSet), View.OnTouchListener {

    private var mMatrix: Matrix = Matrix()
    private var savedMatrix = Matrix()

    private enum class MODE{
        NONE,
        DRAG,
        ZOOM,
    }
    private var mode = MODE.NONE

    var maxScale = 5f
    var minScale = 1f

    //variables for current image position and zoom
    private var start = PointF()
    private var mid = PointF()
    private var oldDist = 1f
    private val currentMatrixValues = FloatArray(9)
    private var imageXPosition = 0f
    private var imageYPosition = 0f
    private var imageWidth = 0f
    private var imageHeight = 0f
    private var isZooming = false

    init {
        setOnTouchListener(this)
        scaleType = ScaleType.MATRIX
    }

    override fun setImageBitmap(bm: Bitmap?) {
        super.setImageBitmap(bm)
        bm ?: return
        imageWidth = bm.width.toFloat()
        imageHeight = bm.height.toFloat()
        centerImage()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        centerImage()
    }

    /**
     * set current bitmap in center of view
     */
    private fun centerImage() {
        val drawableRect = RectF(0f, 0f, imageWidth, imageHeight)
        val viewRect = RectF(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat())
        mid.set( measuredWidth.toFloat()/2, measuredHeight.toFloat()/2)
        mMatrix.setRectToRect(drawableRect, viewRect, Matrix.ScaleToFit.CENTER)
        mMatrix.getValues(currentMatrixValues)
        minScale = currentMatrixValues[Matrix.MSCALE_X]
        if (minScale != 1f) {
            mMatrix.postScale(1f/minScale, 1f/minScale, mid.x, mid.y)
            mMatrix.getValues(currentMatrixValues)
            minScale = currentMatrixValues[Matrix.MSCALE_X]
        }
        imageXPosition = currentMatrixValues[Matrix.MTRANS_X]
        imageYPosition = currentMatrixValues[Matrix.MTRANS_Y]
        imageMatrix = mMatrix
    }

    /**
     * detect touches and processed multi-touch zooming and dragging
     */
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        event ?: return false;
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                savedMatrix.set(mMatrix)
                start.set(event.x, event.y)
                mode = MODE.DRAG
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                oldDist = spacing(event)
                if (oldDist > 10f) {
                    savedMatrix.set(mMatrix)
                    midPoint(mid, event)
                    mode = MODE.ZOOM
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                mode = MODE.NONE
            }
            MotionEvent.ACTION_MOVE -> {
                if (mode == MODE.DRAG) {
                   moveBy(event.x - start.x, event.y - start.y)
                } else if (mode == MODE.ZOOM) {
                    val newDist = spacing(event)
                    if (newDist > 10f) {
                        val scale = newDist / oldDist
                        scaleBy(scale)
                    }
                }
            }
        }

        imageMatrix = mMatrix
        return true
    }

    private fun moveBy(x: Float, y: Float) {
        mMatrix.set(savedMatrix)
        mMatrix.postTranslate(x, y)
        moveToBounds()
    }

    private fun scaleBy(scale: Float) {
        mMatrix.set(savedMatrix)
        mMatrix.postScale(scale, scale, mid.x, mid.y)
        mMatrix.getValues(currentMatrixValues)
        val currentScaleX = currentMatrixValues[Matrix.MSCALE_X]
        if (currentScaleX <= minScale) {
            mMatrix.postScale(minScale/currentScaleX, minScale/currentScaleX, mid.x, mid.y)
        } else if (currentScaleX >= maxScale) {
            mMatrix.postScale(maxScale/currentScaleX, maxScale/currentScaleX, mid.x, mid.y)
        }
        moveToBounds()
    }

    fun zoomIn() {
        val currentScaleX = currentMatrixValues[Matrix.MSCALE_X]
        if (currentScaleX == maxScale && !isZooming)
            return
        isZooming = true
        savedMatrix.set(mMatrix)
        val interpolator = AccelerateDecelerateInterpolator()
        val startTime = System.currentTimeMillis()
        val duration = 300f
        post(object: Runnable{
            override fun run() {
                var delta = (System.currentTimeMillis() - startTime)/duration
                if (delta > 1f)
                    delta = 1f
                val interpolated = interpolator.getInterpolation(delta)
                val scale = currentScaleX + interpolated*(1f)
                scaleBy(scale/currentScaleX)
                imageMatrix = mMatrix
                if (delta < 1f)
                    post(this)
                else
                    isZooming = false
            }
        })
    }

    /**
     * animate zoom in by 1 point
     */
    fun zoomOut() {
        val currentScaleX = currentMatrixValues[Matrix.MSCALE_X]
        if (currentScaleX == minScale && !isZooming)
            return
        isZooming = true
        savedMatrix.set(mMatrix)
        val interpolator = AccelerateDecelerateInterpolator()
        val startTime = System.currentTimeMillis()
        val duration = 300f
        post(object: Runnable{
            override fun run() {
                var delta = (System.currentTimeMillis() - startTime)/duration
                if (delta > 1f)
                    delta = 1f
                val interpolated = interpolator.getInterpolation(delta)
                val scale = currentScaleX - interpolated*(1f)
                scaleBy(scale/currentScaleX)
                imageMatrix = mMatrix
                if (delta < 1f)
                    post(this)
                else
                    isZooming = false
            }
        })
    }

    /**
     * animate zoom out by one point
     */
    private fun moveToBounds() {
        var needMoveToBounds = false
        mMatrix.getValues(currentMatrixValues)
        val currentX = currentMatrixValues[Matrix.MTRANS_X]
        val currentY = currentMatrixValues[Matrix.MTRANS_Y]
        var moveX = 0f
        var moveY = 0f
        val currentScaleX = currentMatrixValues[Matrix.MSCALE_X]
        val minXPosition = imageXPosition + imageWidth - currentScaleX*imageWidth
        val maxXPosition = imageXPosition
        val minYPosition = imageYPosition + imageHeight - currentScaleX*imageHeight
        val maxYPosition = imageYPosition
        if (currentX < minXPosition) {
            moveX = minXPosition - currentX
            needMoveToBounds = true
        } else if (currentX > maxXPosition) {
            moveX = maxXPosition - currentX
            needMoveToBounds = true
        }
        if (currentY < minYPosition) {
            moveY = minYPosition - currentY
            needMoveToBounds = true
        } else if (currentY > maxYPosition) {
            moveY = maxYPosition - currentY
            needMoveToBounds = true
        }
        if (needMoveToBounds) {
            mMatrix.postTranslate(moveX, moveY)
        }
    }

    private fun spacing(event: MotionEvent): Float {
        val x = event.getX(0) - event.getX(1)
        val y = event.getY(0) - event.getY(1)
        return Math.sqrt((x * x + y * y).toDouble()).toFloat()
    }

    private fun midPoint(point: PointF, event: MotionEvent) {
        val x = event.getX(0) + event.getX(1)
        val y = event.getY(0) + event.getY(1)
        point.set(x / 2, y / 2)
    }

}