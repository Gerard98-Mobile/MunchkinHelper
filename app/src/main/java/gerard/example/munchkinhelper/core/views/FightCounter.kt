package gerard.example.munchkinhelper.core.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.FrameLayout
import gerard.example.munchkinhelper.CfgTheme
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.colorInt
import gerard.example.munchkinhelper.databinding.ViewFightCounterBinding

class FightCounter @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    fun interface ValueChangeListener{
        fun valueChanged(value: Int)
    }

    private val binding = ViewFightCounterBinding.inflate(LayoutInflater.from(context), this, true)

    var valueChangeListener : ValueChangeListener? = null

    init{
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.FightCounter,
            defStyleAttr,
            0
        )
        val orientation = a.getInt(R.styleable.FightCounter_counter_orientation, 0)

        binding.run {
            grid.columnCount = if(orientation == 0) 2 else 5

            add1.setOnClickListener { changeValue(1) }
            add2.setOnClickListener { changeValue(2) }
            add3.setOnClickListener { changeValue(3) }
            add5.setOnClickListener { changeValue(5) }
            add10.setOnClickListener { changeValue(10) }

            minus1.setOnClickListener { changeValue(-1) }
            minus2.setOnClickListener { changeValue(-2) }
            minus3.setOnClickListener { changeValue(-3) }
            minus5.setOnClickListener { changeValue(-5) }
            minus10.setOnClickListener { changeValue(-10) }
        }

        a.recycle()
    }

    fun changeValue(changer: Int){
        valueChangeListener?.valueChanged(changer)
    }

    fun applyTheme() {
        val color = CfgTheme.current.primaryColor.colorInt(context)
        for(i in 0..binding.grid.childCount){
            (binding.grid.getChildAt(i) as? Button)?.setTextColor(color)
        }
    }

}