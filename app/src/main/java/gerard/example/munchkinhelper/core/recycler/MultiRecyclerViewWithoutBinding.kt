package gerard.example.munchkinhelper.core.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

@SuppressWarnings("unchecked")
open class MultiRecyclerViewWithoutBinding<T>(val context: Context) : RecyclerView.Adapter<CustomViewHolder2>(){

    class RegisteredItem<T>(
        val condition: (Any) -> Boolean,
        val binder: Binder<T>,
        @LayoutRes val layoutRes: Int
    )

    abstract class Binder<T>{
        abstract fun bind(a: CustomViewHolder2, b: T)
    }

    val items = mutableListOf<RegisteredItem<T>>()

    var data = listOf<Any>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun <K> register(
        condition: (Any) -> Boolean,
        bind: (CustomViewHolder2, K) -> Unit,
        layoutRes: Int
    ){
        val binder = object : Binder<K>() {
            override fun bind(a: CustomViewHolder2, b: K){
                bind(a, b)
            }
        }

        items.add(RegisteredItem(condition, binder as Binder<T>, layoutRes))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder2 {
        items.forEach {
            if(it.condition(data[viewType])){
                val view = LayoutInflater.from(context).inflate(it.layoutRes, parent, false)
                return CustomViewHolder2(view)
            }
        }
        throw NotRegisteredException("Siema")
    }

    override fun onBindViewHolder(holder: CustomViewHolder2, position: Int) {
        items.forEach {
            if(it.condition(data[position])){
                it.binder.bind(holder, data[position] as T)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}

class CustomViewHolder2(itemView: View) : RecyclerView.ViewHolder(itemView)