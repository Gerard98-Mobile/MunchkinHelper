package gerard.example.munchkinhelper.core.views

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.TransitionDrawable
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import gerard.example.munchkinhelper.R
import kotlinx.android.synthetic.main.power_edit_text.view.*


class EditTextWithTitle @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    fun interface ValueChangeListener{
        fun onValueChange()
    }

    private var state = STATE.DRAW
    private var valueChangeListener : ValueChangeListener? = null

    init{
        inflate(context, R.layout.power_edit_text, this)

        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.EditTextWithTitle,
            defStyleAttr,
            0
        )
        val title = a.getString(R.styleable.EditTextWithTitle_editTextTitle)
        txtView_title.setText(title)

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
            editText_background_card.setCardBackgroundColor(it.animatedValue as Int)
        }
        animator.start()

        this.oldColor = newColor
    }

    fun setOnChangeListener(valueChangeListener: ValueChangeListener) {
        this.valueChangeListener = valueChangeListener
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                valueChangeListener.onValueChange()
            }

            override fun afterTextChanged(s: Editable?) {
                // nothing
            }
        })
    }

    fun getCount(): Int? {
        return editText.text.toString().toIntOrNull()
    }

    fun setCount(value: Int) {
        editText.setText(value.toString())
    }

    enum class STATE(val color: Int){
        WINNER(R.color.winnerColor), LOSSER(R.color.looserColor), DRAW(R.color.drawColor)
    }
}