package gerard.example.munchkinhelper.ui.activity.load

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.PopupWindow
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import gerard.example.munchkinhelper.*
import gerard.example.munchkinhelper.databinding.ItemMenuOptionBinding
import gerard.example.munchkinhelper.databinding.PopupGameOptionsBinding

fun interface OptionCallback{
    fun clicked(option: Option)
}
enum class Option(@StringRes val title : Int, @DrawableRes val icon : Int){
    DELETE_GAME(R.string.delete_game, R.drawable.ic_delete_black),
    DELETE_SCHEME(R.string.delete_scheme, R.drawable.ic_delete_black),
    EDIT(R.string.edit, R.drawable.ic_edit)
}

class MoreOptionsPopUp(val options: List<Option>, val callback: OptionCallback){

    var popupWindow : PopupWindow? = null

    fun show(view: View){
        if(options.isEmpty()) return
        val popupView = createView(view.context)
        popupWindow = PopupWindow(popupView, FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        popupWindow?.let{
            it.setBackgroundDrawable(ColorDrawable())
            it.isOutsideTouchable = true
            it.showAsDropDown(view)
        }
    }

    private fun createView(context: Context) : View{
        val popupBinder = PopupGameOptionsBinding.inflate(LayoutInflater.from(context))

        popupBinder.root.strokeColor = CfgTheme.current.primaryColor.colorInt(context)
        popupBinder.root.setCardBackgroundColor(CfgTheme.current.backgroundColor.colorInt(context))

        options.forEach { option ->
            val itemBinder = ItemMenuOptionBinding.inflate(LayoutInflater.from(context))
            itemBinder.icon.setImageResource(option.icon)
            itemBinder.text.text = context.getString(option.title)
            itemBinder.root.setOnClickListener {
                callback.clicked(option)
                popupWindow?.dismiss()
            }

            itemBinder.icon.imageTintList = CfgTheme.current.primaryColor.colorStateList(context)
            itemBinder.text.setTextColor(CfgTheme.current.primaryColor.colorInt(context))

            popupBinder.optionsContainer.addView(itemBinder.root)
        }
        return popupBinder.root
    }

}