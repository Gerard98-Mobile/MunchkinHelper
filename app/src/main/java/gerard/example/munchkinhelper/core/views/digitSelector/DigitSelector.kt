package gerard.example.munchkinhelper.core.views.digitSelector

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.databinding.DigitSelectorBinding
import gerard.example.munchkinhelper.databinding.EditTextCoreBinding
import gerard.example.munchkinhelper.toTwoDigitList
import gerard.example.munchkinhelper.toTwoDigitString

class DigitSelector @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: DigitSelectorBinding =
        DigitSelectorBinding.inflate(LayoutInflater.from(context), this, true)

    init{
        val a = context.obtainStyledAttributes(attrs, R.styleable.DigitSelector, defStyleAttr, 0)
        val test = a.getText(R.styleable.DigitSelector_selectorTitle)
        binding.title.text = test
        binding.title.isVisible = binding.title.text.isNotEmpty()
        a.recycle()
    }

    fun init(){
        val adapter = DigitSelectorAdapter(context, (0 to 99).toTwoDigitList())
        binding.container.adapter = adapter
        binding.container.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                Log.e("Halo","XY: $dx $dy")
            }
        })
        DigitSelectorLinearSnapHelper().attachToRecyclerView(binding.container)
    }

}