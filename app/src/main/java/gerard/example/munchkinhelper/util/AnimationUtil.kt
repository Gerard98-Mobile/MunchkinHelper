package gerard.example.munchkinhelper.util

import android.view.View

object AnimationUtil {

    fun animateAlpha(value: Float, duration: Long, view: View){
        view.animate()
            .alpha(value)
            .setDuration(duration)
            .start()
    }
}