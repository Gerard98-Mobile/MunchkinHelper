package gerard.example.munchkinhelper.core.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class ViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView){
    abstract fun bind(value: T)
}