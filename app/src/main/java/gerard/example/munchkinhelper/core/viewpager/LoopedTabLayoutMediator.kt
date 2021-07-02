package gerard.example.munchkinhelper.core.viewpager

import android.annotation.SuppressLint
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout

class LoopedTabLayoutMediator(
    val adapter: LoopedMultiRecyclerAdapter<*>,
    val vp: ViewPager2,
    val tab: TabLayout? = null,
    val startFromFirstItem: Boolean = true
) {

    private val pageCallback = object: ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            tab?.getTabAt(adapter.realPosition(position))?.select()
        }
    }

    fun attach() = adapter.run{
        var startPosition = itemCount / 2
        if(startFromFirstItem && realPosition(startPosition) != 0){
            adapter.data.count().let { startPosition += it - realPosition(startPosition) }
        }
        vp.setCurrentItem(startPosition, false)

        if(slideshow) initSlideshow()
        vp.registerOnPageChangeCallback(pageCallback)

        tab?.apply {
            removeAllTabs()
            val count = data.count() ?: 0
            if(count <= 1) return@apply
            for(i in 0 until count){
                addTab(newTab().setText(""), false)
            }
            if(this.tabCount > 0) this.getTabAt(realPosition(startPosition))?.select()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun initSlideshow() = adapter.run{
        vp.getChildAt(0).setOnTouchListener { v, event ->
            when(event.action){
                MotionEvent.ACTION_DOWN -> disposeTimer()
                MotionEvent.ACTION_UP -> createTimer(vp)
            }
            v.onTouchEvent(event)
        }
        createTimer(vp)
    }

}