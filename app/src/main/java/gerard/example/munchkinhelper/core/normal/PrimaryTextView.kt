package gerard.example.munchkinhelper.core.normal

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatTextView
import gerard.example.munchkinhelper.CfgTheme
import gerard.example.munchkinhelper.Theme
import gerard.example.munchkinhelper.colorInt

class PrimaryTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr), CfgTheme.ThemeChangedListener{

    init{
        if(!isInEditMode) onThemeChanged(CfgTheme.current)
    }

    override fun onThemeChanged(theme: Theme) {
        Log.e("PrimaryTextView","OnThemeChanged")
        setTextColor(CfgTheme.current.textViewTheme.textColor.colorInt(context))
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