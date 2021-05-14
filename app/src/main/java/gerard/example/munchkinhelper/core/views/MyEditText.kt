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


class MyEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init{
        inflate(context, R.layout.my_edit_text, this)

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


}