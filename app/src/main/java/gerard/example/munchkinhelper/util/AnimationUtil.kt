package gerard.example.munchkinhelper.util

import android.R
import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.addListener
import androidx.transition.Fade
import androidx.transition.Transition
import androidx.transition.TransitionManager
import gerard.example.munchkinhelper.util.AnimationUtil.hide


object AnimationUtil {

    fun animateShowHideView(views: List<Pair<View,Boolean>>, duration: Long, animationCallback: AnimationCallback? = null){
        views.forEach { view ->
            view.first.animate()
                .alpha(if(view.second) 1f else 0f)
                .setListener(object: Animator.AnimatorListener{
                    override fun onAnimationStart(animation: Animator?) {

                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        animationCallback?.animationEnded()
                    }

                    override fun onAnimationCancel(animation: Animator?) {

                    }

                    override fun onAnimationRepeat(animation: Animator?) {

                    }


                })
                .setDuration(duration)
                .start()
        }
    }

    fun View.hide(parent: ViewGroup, duration: Long = 700L){ animateVisiblity(this, parent, duration, false) }
    fun View.showOrHide(parent: ViewGroup, show: Boolean, duration: Long = 700L){ animateVisiblity(this, parent, duration, show) }

    private fun animateVisiblity(view: View, parent: ViewGroup, duration: Long, show: Boolean){
        val transition: Transition = Fade()
        transition.duration = duration
        transition.addTarget(view)

        TransitionManager.beginDelayedTransition(parent, transition)
        view.visibility = if (show) View.VISIBLE else View.GONE
    }


    fun animateAlpha(view: View, value: Float, duration: Long){
        view.animate()
            .alpha(value)
            .setDuration(duration)
            .start()
    }


    fun animateHeight(view: View, value: Int, duration: Long, callback: AnimationCallback? = null){
        with(ValueAnimator.ofInt(view.measuredHeight, value)){
            this.addUpdateListener {
                val newValue = it.animatedValue as? Int
                newValue?.let {
                    val lp = view.layoutParams
                    lp.height = it
                    view.layoutParams = lp
                }
            }
            this.addListener({
                callback?.animationEnded()
            })
            this.duration = duration
            this.start()
        }
    }
}

fun interface AnimationCallback{ fun animationEnded() }