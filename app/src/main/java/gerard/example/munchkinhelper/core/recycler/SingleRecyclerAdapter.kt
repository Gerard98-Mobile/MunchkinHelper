package gerard.example.munchkinhelper.core.recycler

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.widget.ViewPager2
import gerard.example.munchkinhelper.BuildConfig
import java.lang.Exception

abstract class SingleRecyclerAdapter<T, BINDING: ViewBinding>(val context: Context) : RecyclerView.Adapter<CustomViewHolder<BINDING>>(){

    var data: List<T>? = null
    lateinit var bind: (holder: CustomViewHolder<BINDING>, item: T) -> Unit
    lateinit var bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BINDING

    fun register(
        data: List<T>,
        bind: (holder: CustomViewHolder<BINDING>, item: T)-> Unit,
        bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BINDING
    ){
        this.data = data
        this.bind = bind
        this.bindingInflater = bindingInflater
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder<BINDING> {
        val vh = bindingInflater.invoke(LayoutInflater.from(context), parent, false)
        return CustomViewHolder(vh)
    }

    override fun onBindViewHolder(holder: CustomViewHolder<BINDING>, position: Int) {
        data?.get(position)?.let { bind(holder, it) }
    }

    override fun getItemCount(): Int {
        if(BuildConfig.DEBUG && data == null) throw NotRegisteredException("Are you forgot about registering view? :D")
        return data?.size ?: 0
    }
}

class CustomViewHolder<BINDING>(val binding: BINDING) : RecyclerView.ViewHolder(binding.root) where BINDING : ViewBinding

class NotRegisteredException(text: String) : Exception(text)