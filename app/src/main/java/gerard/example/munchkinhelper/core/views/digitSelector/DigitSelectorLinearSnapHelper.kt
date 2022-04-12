package gerard.example.munchkinhelper.core.views.digitSelector

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.colorInt

class DigitSelectorLinearSnapHelper : LinearSnapHelper() {

    var lastSnapedView: TextView? = null

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager?): View? {
        val snappedView = super.findSnapView(layoutManager)

        with(snappedView as? TextView){
            this?.let {
                lastSnapedView?.setTextColor(R.color.colorGrey.colorInt(context))
                this.setTextColor(R.color.colorPrimary.colorInt(context))
                lastSnapedView = this
            }
        }

        return snappedView
    }

}