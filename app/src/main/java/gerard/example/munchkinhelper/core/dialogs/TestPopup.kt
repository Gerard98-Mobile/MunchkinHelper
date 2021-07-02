package gerard.example.munchkinhelper.core.dialogs

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.PopupWindow
import gerard.example.munchkinhelper.databinding.PopupTest2Binding
import gerard.example.munchkinhelper.toIntPx
import gerard.example.munchkinhelper.toPx
import gerard.example.munchkinhelper.ui.activity.load.Option
import gerard.example.munchkinhelper.ui.activity.load.OptionCallback

class TestPopup{

    var popupWindow : PopupWindow? = null

    fun show(view: View){
        val popupView = createView(view.context)
        popupWindow = PopupWindow(popupView, 200.toIntPx, FrameLayout.LayoutParams.WRAP_CONTENT)
        popupWindow?.let{
            it.setBackgroundDrawable(ColorDrawable())
            it.isOutsideTouchable = true
            it.showAsDropDown(view)
        }
    }

    private fun createView(context: Context) : View {
        val popupBinder = PopupTest2Binding.inflate(LayoutInflater.from(context))

        popupBinder.confirm.setOnClickListener {
            popupWindow?.dismiss()
        }

        return popupBinder.root
    }

}