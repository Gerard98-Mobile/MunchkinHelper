package gerard.example.munchkinhelper.core.views

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.FrameLayout
import gerard.example.munchkinhelper.R
import kotlinx.android.synthetic.main.rounded_button.view.*

class RoundedButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr){

    init {
        inflate(context, R.layout.rounded_button, this)

        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.RoundedButton,
            defStyleAttr,
            0
        )

        textView.text = a.getString(R.styleable.RoundedButton_android_text)
        val default = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16f, context.resources.displayMetrics)
        val dimension = a.getDimension(R.styleable.RoundedButton_android_textSize, default)
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimension)

        a.recycle()

    }

    override fun setOnClickListener(l: OnClickListener?) {
        Log.e("RoundedButton","setOnClick")
        root.setOnClickListener(l)
    }
}