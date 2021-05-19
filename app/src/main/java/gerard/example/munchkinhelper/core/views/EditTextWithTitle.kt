package gerard.example.munchkinhelper.core.views

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import gerard.example.munchkinhelper.CfgTheme
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.colorInt
import gerard.example.munchkinhelper.databinding.PowerEditTextBinding


class EditTextWithTitle @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: PowerEditTextBinding =
        PowerEditTextBinding.inflate(LayoutInflater.from(context), this, true)

    init{

        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.EditTextWithTitle,
            defStyleAttr,
            0
        )
        val title = a.getString(R.styleable.EditTextWithTitle_editTextTitle)
        binding.txtViewTitle.setText(title)

        a.recycle()
    }

    fun changeState(state: STATE){
        when(state){
            STATE.WINNER -> changeColorWithAnimation(R.color.winnerColor)
            STATE.DRAW -> changeColorWithAnimation(R.color.drawColor)
            STATE.LOSSER -> changeColorWithAnimation(R.color.looserColor)
        }
    }

    var oldColor = ContextCompat.getColor(context, R.color.drawColor)

    fun changeColorWithAnimation(@ColorRes newColorRes: Int){
        val newColor = ContextCompat.getColor(context, newColorRes)

        val animator = ValueAnimator.ofObject(ArgbEvaluator(), oldColor, newColor)
        animator.duration = 500
        animator.addUpdateListener {
            binding.editTextBackgroundCard.setCardBackgroundColor(it.animatedValue as Int)
        }
        animator.start()

        this.oldColor = newColor
    }

    fun getCount(): Int {
        return binding.editText.text.toString().toIntOrNull() ?: 0
    }

    fun setCount(value: Int) {
        binding.editText.setText(value.toString())
    }

    fun applyTheme() {
        binding.txtViewTitle.setTextColor(CfgTheme.current.primaryColor.colorInt(context))
    }

    enum class STATE{
        WINNER, LOSSER, DRAW
    }
}