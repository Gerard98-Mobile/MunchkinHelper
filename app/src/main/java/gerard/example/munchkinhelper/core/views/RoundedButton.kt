package gerard.example.munchkinhelper.core.views

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat.getColor
import gerard.example.munchkinhelper.*
import gerard.example.munchkinhelper.databinding.RoundedButtonBinding

class RoundedButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), CfgTheme.ThemeChangedListener{

    private val binding: RoundedButtonBinding =
        RoundedButtonBinding.inflate(LayoutInflater.from(context), this, true)

    init {

        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.RoundedButton,
            defStyleAttr,
            0
        )

        binding.textView.text = a.getString(R.styleable.RoundedButton_android_text)
        val default = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16f, context.resources.displayMetrics)
        val dimension = a.getDimension(R.styleable.RoundedButton_android_textSize, default)
        binding.textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimension)

        if(isInEditMode){
            binding.root.setCardBackgroundColor(a.getColor(R.styleable.RoundedButton_backgroundColor, getColor(context, R.color.colorPrimary)))
            binding.textView.setTextColor(a.getColor(R.styleable.RoundedButton_textColor, getColor(context, R.color.colorWhite)))
        }
        else{
            onThemeChanged(CfgTheme.current)
        }

        a.recycle()
    }

    override fun setOnClickListener(l: OnClickListener?) {
        binding.root.setOnClickListener(l)
    }

    override fun onThemeChanged(theme: Theme) {
        binding.root.setCardBackgroundColor(CfgTheme.current.roundedButtonTheme.cardBackgroundColor.colorInt(context))
        binding.textView.setTextColor(CfgTheme.current.roundedButtonTheme.textColor.colorInt(context))
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if(!isInEditMode) CfgTheme.addListener(this)

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if(!isInEditMode) CfgTheme.addListener(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if(!isInEditMode) CfgTheme.removeListener(this)
    }
}