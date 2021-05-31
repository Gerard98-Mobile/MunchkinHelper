package gerard.example.munchkinhelper.core.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

@SuppressWarnings("unchecked")
open class MultiRecyclerAdapter<T>(val context: Context) : RecyclerView.Adapter<CustomViewHolder<ViewBinding>>(){

    class RegisteredItem<T, BINDING : ViewBinding>(
        val condition: (Any) -> Boolean,
        val binder: Binder<T, BINDING>,
        val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BINDING
    )

    abstract class Binder<T, BINDING : ViewBinding>{
        abstract fun binding(a: CustomViewHolder<BINDING>, b: T)
    }

    val items = mutableListOf<RegisteredItem<T, ViewBinding>>()

    var data = listOf<Any>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun <K, BINDING : ViewBinding> register(
        condition: (Any) -> Boolean,
        bind: (CustomViewHolder<BINDING>, K) -> Unit,
        bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BINDING
    ){
        val binder = object : Binder<K, BINDING>() {
            override fun binding(a: CustomViewHolder<BINDING>, b: K){
                bind(a, b)
            }
        }

        items.add(RegisteredItem(condition, binder as Binder<T, ViewBinding>, bindingInflater))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder<ViewBinding> {
        items.forEach {
            if(it.condition(data[viewType])){
                val vh = it.bindingInflater.invoke(LayoutInflater.from(context), parent, false)
                return CustomViewHolder(vh)
            }
        }
        throw NotRegisteredException("Siema")
    }

    override fun onBindViewHolder(holder: CustomViewHolder<ViewBinding>, position: Int) {
        items.forEach {
            if(it.condition(data[position])){
                it.binder.binding(holder, data[position] as T)
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