package gerard.example.munchkinhelper.ui.activity.load

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.PopupWindow
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.model.Game
import kotlinx.android.synthetic.main.popup_game_options.view.*

class MoreOptionsPopUp(val callback: Callback) : PopupWindow() {

    fun interface Callback{
        fun delete()
    }

    fun show(view: View){
        val popupView = LayoutInflater.from(view.context).inflate(R.layout.popup_game_options, null)
        with(PopupWindow(popupView, FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)){
            this.setBackgroundDrawable(ColorDrawable())
            this.isOutsideTouchable = true
            this.showAsDropDown(view)

            popupView.delete.setOnClickListener {
                callback.delete()
                this.dismiss()
            }
        }

    }

}