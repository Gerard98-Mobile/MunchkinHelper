package gerard.example.munchkinhelper.util

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import androidx.core.animation.addListener
import gerard.example.munchkinhelper.core.views.FightCounter

object AnimationUtil {

    fun animateAlpha(view: View, value: Float, duration: Long){
        view.animate()
            .alpha(value)
            .setDuration(duration)
            .start()
    }


    fun interface AnimationCallback{ fun animationEnded() }
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