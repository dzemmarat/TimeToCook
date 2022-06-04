package ru.meowtee.timetocook.ui.custom

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import kotlin.properties.Delegates


class TypeWriterView : AppCompatTextView {
    private var mText: CharSequence? = null
    private var mPrintingText: String? = null
    private var mIndex = 0
    private var mDelay: Long = 100 //Default 500ms delay
    private var mContext: Context? = null
    private var mTypeWriterListener: TypeWriterListener? = null
    private var animating = false
    private var mBlinker: Runnable by Delegates.notNull()
    private var i = 0
    private val mHandler: Handler = Handler()
    private val mCharacterAdder: Runnable = object : Runnable {
        override fun run() {
            if (animating) {
                text = mText!!.subSequence(0, mIndex++).toString() + "_"
                //typing typed
                if (mTypeWriterListener != null) mTypeWriterListener!!.onCharacterTyped(
                    mPrintingText,
                    mIndex)
                if (mIndex <= mText!!.length) {
                    mHandler.postDelayed(this, mDelay)
                } else {
                    //typing end
                    animating = false
                    callBlink()
                    if (mTypeWriterListener != null) mTypeWriterListener!!.onTypingEnd(mPrintingText)
                }
            }
        }
    }

    constructor(context: Context) : super(context) {
        mContext = context
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    private fun callBlink() {
        mBlinker = Runnable {
            if (i <= 10) {
                if (i++ % 2 != 0) {
                    text = String.format("%s _", mText)
                    mHandler.postDelayed(mBlinker, 150)
                } else {
                    text = String.format("%s   ", mText)
                    mHandler.postDelayed(mBlinker, 150)
                }
            } else i = 0
        }
        mHandler.postDelayed(mBlinker, 150)
    }

    /**
     * Call this function to display
     *
     * @param text attribute
     */
    fun animateText(text: String?) {
        if (!animating) {
            animating = true
            mText = text
            mPrintingText = text
            mIndex = 0
            setText("")
            mHandler.removeCallbacks(mCharacterAdder)
            //typing start
            if (mTypeWriterListener != null) mTypeWriterListener!!.onTypingStart(mPrintingText)
            mHandler.postDelayed(mCharacterAdder, mDelay)
        } else {
            //CAUTION: Already typing something..
            Toast.makeText(mContext, "Typewriter busy typing: $mText", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Call this function to set delay in MILLISECOND [20..150]
     *
     * @param delay
     */
    fun setDelay(delay: Int) {
        if (delay >= 20 && delay <= 150) mDelay = delay.toLong()
    }

    /**
     * Call this to remove animation at any time
     */
    fun removeAnimation() {
        mHandler.removeCallbacks(mCharacterAdder)
        animating = false
        text = mPrintingText

        //typing removed
        if (mTypeWriterListener != null) mTypeWriterListener!!.onTypingRemoved(mPrintingText)
    }

    /**
     * Set listener to receive typing effects
     *
     * @param typeWriterListener
     */
    fun setTypeWriterListener(typeWriterListener: TypeWriterListener?) {
        mTypeWriterListener = typeWriterListener
    }
}