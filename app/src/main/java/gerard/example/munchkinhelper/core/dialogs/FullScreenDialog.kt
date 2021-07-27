package gerard.example.munchkinhelper.core.dialogs

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.view.Window
import android.view.WindowManager
import gerard.example.munchkinhelper.R


open class FullScreenDialog(
    context: Context,
    val startAnimationView: View
) : Dialog(context, android.R.style.ThemeOverlay) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setOnShowListener {
//            revealShow(true, this)
//        }
    }

    private fun revealShow(b: Boolean, dialog: Dialog) {
        val view: View = dialog.findViewById(R.id.dialog_parent) ?: return
        val w: Int = view.getWidth()
        val h: Int = view.getHeight()
        val endRadius = Math.hypot(w.toDouble(), h.toDouble()).toInt()
        val cx = (startAnimationView.x + startAnimationView.width / 2).toInt()
        val cy: Int = startAnimationView.y.toInt() + startAnimationView.height + 56
        if (b) {
            val revealAnimator =
                ViewAnimationUtils.createCircularReveal(view, cx, cy, 0f, endRadius.toFloat())
            view.visibility = View.VISIBLE
            revealAnimator.duration = 700
            revealAnimator.start()
        } else {
            val anim =
                ViewAnimationUtils.createCircularReveal(view, cx, cy, endRadius.toFloat(), 0f)
            anim.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    dialog.dismiss()
                    view.visibility = View.INVISIBLE
                }
            })
            anim.duration = 700
            anim.start()
        }
    }
}