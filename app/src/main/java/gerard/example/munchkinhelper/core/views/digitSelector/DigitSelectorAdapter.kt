package gerard.example.munchkinhelper.core.views.digitSelector

import android.content.Context
import android.util.Log
import gerard.example.munchkinhelper.core.recycler.CustomViewHolder
import gerard.example.munchkinhelper.core.recycler.MultiRecyclerAdapter
import gerard.example.munchkinhelper.databinding.ItemDigitBinding

class DigitSelectorAdapter(context: Context, data: List<String>) : MultiRecyclerAdapter<Any>(context) {

    init{
        register({it is String}, ::bind, ItemDigitBinding::inflate)
        this.data = data
    }

    fun bind(holder: CustomViewHolder<ItemDigitBinding>, value: String) = holder.binding.run{
        this.txtView.text = if(value.count() == 1) "0$value" else value
        holder.binding.txtView.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            Log.e("Test","Top: $top Bottom: $bottom")
        }
    }

}