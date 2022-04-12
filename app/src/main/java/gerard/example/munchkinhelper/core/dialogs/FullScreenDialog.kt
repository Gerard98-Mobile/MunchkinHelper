package gerard.example.munchkinhelper.core.dialogs

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.viewbinding.ViewBinding
import gerard.example.munchkinhelper.CfgTheme
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.colorInt
import gerard.example.munchkinhelper.colorStateList
import gerard.example.munchkinhelper.databinding.DialogFullscreenBinding


open class FullScreenDialog<BINDING : ViewBinding>(
    context: Context,
    val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BINDING,
    @StringRes val title: Int? = null
) : CoreDialog(context, R.style.FullScreenDialog) {

    var binding: BINDING? = null
    var bindingRoot: DialogFullscreenBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(DialogFullscreenBinding.inflate(LayoutInflater.from(context))){
            setContentView(this.root)
            bindingRoot = this
        }

        bindingRoot?.title?.text = title?.let { context.getText(it) } ?: ""
        bindingRoot?.title?.isVisible = bindingRoot?.title.toString().isNotEmpty()

        bindingRoot?.close?.setOnClickListener {
            dismiss()
        }

        binding = bindingInflater.invoke(LayoutInflater.from(context), bindingRoot?.root, false)

        bindingRoot?.content?.addView(binding?.root)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding = null
    }

    override fun applyTheme() {
        bindingRoot?.root?.setBackgroundColor(CfgTheme.current.backgroundColor.colorInt(context))
        bindingRoot?.close?.imageTintList = CfgTheme.current.primaryColor.colorStateList(context)
        bindingRoot?.title?.setTextColor(CfgTheme.current.primaryColor.colorInt(context))
    }
}