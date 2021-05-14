package gerard.example.munchkinhelper.core.views

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.EditText
import android.widget.FrameLayout
import gerard.example.munchkinhelper.R
import kotlinx.android.synthetic.main.power_edit_text.view.*
import kotlinx.android.synthetic.main.view_fight_counter.view.*

class FightCounter @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    fun interface ValueChangeListener{
        fun valueChanged(value: Int)
    }

    var valueChangeListener : ValueChangeListener? = null

    init{
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.FightCounter,
            defStyleAttr,
            0
        )
        val orientation = a.getInt(R.styleable.FightCounter_counter_orientation, 0)
        inflate(context,
            if(orientation == 0) R.layout.view_fight_counter else R.layout.view_fight_counter_horizontal,
            this)

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

        a.recycle()
    }

    fun changeValue(changer: Int){
        valueChangeListener?.valueChanged(changer)
    }

}