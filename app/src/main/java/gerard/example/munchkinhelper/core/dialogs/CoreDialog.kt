package gerard.example.munchkinhelper.core.dialogs

import android.app.Dialog
import android.content.Context
import androidx.annotation.StyleRes

abstract class CoreDialog : Dialog {
    constructor(context: Context) : super(context)
    constructor(context: Context, @StyleRes themeResId: Int) : super(context, themeResId)

    override fun onStart() {
        super.onStart()
        applyTheme()
    }

    abstract fun applyTheme()
}