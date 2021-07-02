package gerard.example.munchkinhelper.core.views

import android.animation.Animator
import android.animation.TimeInterpolator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.TextView
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.databinding.ViewBannerBinding

class Banner @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: ViewBannerBinding =
        ViewBannerBinding.inflate(LayoutInflater.from(context), this, true)

    var texts : Array<CharSequence> = emptyArray()
    // -1 because for first execute nextIdx will +1 that and we get 0 in first time
    var textIdx = -1

    private fun nextIdx(): Int{ return if (textIdx+1 >= texts.size) {
        textIdx = 0
        textIdx
    } else ++textIdx }

    init{
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.Banner, defStyleAttr, 0)
        try{
            texts = a.getTextArray(R.styleable.Banner_android_entries)
            if(!texts.isNotEmpty()) throw EmptyBanner()
        }
        finally {
            a.recycle()
        }
    }

    var display = 4000L

    private var screenWidth = 0
    private var exitScreenX = 0
    private var secondAnimStarted = false
    private var started = false

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (visibility == View.VISIBLE) { if (screenWidth > 0 && !started) start() }
        else stop()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        exitScreenX = w + binding.textFirst.measuredWidth
        screenWidth = w
        if(!started) start()
    }

    var slideAnimation1: ViewPropertyAnimator? = null
    var resetAnimation1: ViewPropertyAnimator? = null

    var slideAnimation2: ViewPropertyAnimator? = null
    var resetAnimation2: ViewPropertyAnimator? = null

    var animations = mutableListOf<Animator?>()

    fun start(){
        Log.d("Banner", "Start")
        started = true
        if(animations.size > 0){
            animations.forEach {
                it?.resume()
            }
            return
        }
        startAnimation(slideAnimation1, resetAnimation1,slideAnimation2,resetAnimation2, binding.textFirst,binding.textSecond)
    }

    fun startAnimation(
        slideAnimation: ViewPropertyAnimator?,
        resetAnimation: ViewPropertyAnimator?,
        nextSlideAnimation: ViewPropertyAnimator?,
        nextResetAnimation: ViewPropertyAnimator?,
        view: TextView,
        nextView: TextView
    ){
        view.text = texts[nextIdx()]

        val animation = view.animate()
            .translationX(exitScreenX.toFloat())
            .setDuration(display)
            .setInterpolator(LinearInterpolator())
            .setListener(object : MyAnimatorListener() {
                override fun onAnimationStart(animation: Animator?) {
                    Log.d("Banner","Animation start, " + animation.toString())
                    animations.add(animation)
                }

                override fun onAnimationEnd(animation: Animator?) {
                    animation?.cancel()
                    animations.remove(animation)
                    resetAnimation(view)
                }
            })
            .setUpdateListener {
                if (it.getAnimatedValue() as Float * exitScreenX >= screenWidth && !secondAnimStarted){
                    secondAnimStarted = true
                    startAnimation(nextSlideAnimation, nextResetAnimation,slideAnimation,resetAnimation, nextView,view)
                }
            }
        animation.start()
    }

    fun resetAnimation(view: View){
        view
            .animate()
            .translationX(0f)
            .setListener(object: MyAnimatorListener(){
                override fun onAnimationEnd(animation: Animator?) {
                    secondAnimStarted = false
                }
            })
            .setDuration(0)
    }

    fun stop(){
        Log.d("Banner","STOP: " + animations.size)
        started = false
        animations.forEach {
            it?.pause()
        }
    }
}

abstract class MyAnimatorListener : Animator.AnimatorListener{
    override fun onAnimationStart(animation: Animator?){}

    abstract override fun onAnimationEnd(animation: Animator?)

    override fun onAnimationCancel(animation: Animator?) {
        Log.d("Banner", "onAnimationCancel")
    }

    override fun onAnimationRepeat(animation: Animator?) {}

}

class EmptyBanner : Exception("Are you forgot to bind texts to banner? Banner must have at least one text")
