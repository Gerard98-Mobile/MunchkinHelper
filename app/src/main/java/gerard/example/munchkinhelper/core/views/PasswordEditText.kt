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
import gerard.example.munchkinhelper.databinding.PasswordEditTextBinding
import gerard.example.munchkinhelper.databinding.PowerEditTextBinding


class PasswordEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: PasswordEditTextBinding =
        PasswordEditTextBinding.inflate(LayoutInflater.from(context), this, true)

    init{
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.PasswordEditText,
            defStyleAttr,
            0
        )

        a.recycle()
    }

}