package gerard.example.munchkinhelper.core.views

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat.getColor
import gerard.example.munchkinhelper.CfgTheme
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.colorInt
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
        root.setCardBackgroundColor(a.getColor(R.styleable.RoundedButton_backgroundColor, getColor(context, R.color.colorPrimary)))
        textView.setTextColor(a.getColor(R.styleable.RoundedButton_textColor, getColor(context, R.color.colorWhite)))

        a.recycle()

    }

    override fun setOnClickListener(l: OnClickListener?) {
        Log.e("RoundedButton","setOnClick")
        root.setOnClickListener(l)
    }

    fun applyTheme(){
        root.setCardBackgroundColor(CfgTheme.current.primaryColor.colorInt(context))
        textView.setTextColor(CfgTheme.current.textColorSecondary.colorInt(context))
    }
}