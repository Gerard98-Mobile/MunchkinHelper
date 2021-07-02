package gerard.example.munchkinhelper.core.viewpager

import android.content.Context
import android.util.Log
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.widget.ViewPager2
import gerard.example.munchkinhelper.core.recycler.CustomViewHolder
import gerard.example.munchkinhelper.core.recycler.MultiRecyclerAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

abstract class LoopedMultiRecyclerAdapter<Any>(
    context: Context,
    val slideshow: Boolean = false
) : MultiRecyclerAdapter<Any>(context) {

    private val LOOPED_COUNT_SIZE = 10000
    var timer : Disposable? = null
    var display = 3000L

    fun disposeTimer() {
        Log.d("LoopedRecycler","Stop slideshow")
        timer?.dispose()
        timer = null
    }


    fun createTimer(vp: ViewPager2) {
        if(itemCount <= 1) return
        Log.d("LoopedRecycler","Start slideshow")
        timer?.let {
            if(!it.isDisposed) it.dispose()
            timer = null
        }
        timer = Observable.interval(display, TimeUnit.MILLISECONDS)
            .timeInterval()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                slideToNextVP(vp)
            }
    }

    private fun slideToNextVP(vp: ViewPager2){
        vp.setCurrentItem(vp.currentItem + 1, true)
    }

    override fun getItemCount(): Int {
        return if(super.getItemCount() <= 1) super.getItemCount() else LOOPED_COUNT_SIZE
    }

    var realPosition: (Int) -> Int = { pos -> if (data.count() > 0) pos%(data.count()) else 0 }
    override fun onBindViewHolder(holder: CustomViewHolder<ViewBinding>, position: Int) {
        super.onBindViewHolder(holder, realPosition(position))
    }

    override fun getItemViewType(position: Int): Int {
        return if(position > data.count()) position % data.count() else position
    }

}