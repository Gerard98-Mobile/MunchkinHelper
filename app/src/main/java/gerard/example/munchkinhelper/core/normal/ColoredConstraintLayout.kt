package gerard.example.munchkinhelper.core.normal

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import gerard.example.munchkinhelper.CfgTheme
import gerard.example.munchkinhelper.Theme
import gerard.example.munchkinhelper.colorInt

class ColoredConstraintLayout : ConstraintLayout, CfgTheme.ThemeChangedListener{

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, attributeSetId: Int) : super(context, attrs, attributeSetId)

    init{
        if(!isInEditMode) onThemeChanged(CfgTheme.current)
    }

    override fun onThemeChanged(theme: Theme) {
        setBackgroundColor(CfgTheme.current.backgroundColor.colorInt(context))
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