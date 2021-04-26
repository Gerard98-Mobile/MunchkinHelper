package gerard.example.munchkinhelper.core.views

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import android.widget.FrameLayout
import gerard.example.munchkinhelper.R
import kotlinx.android.synthetic.main.power_edit_text.view.*
import kotlinx.android.synthetic.main.view_fight_counter.view.*

class FightCounter @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var editText: EditTextWithTitle? = null

    init{
        inflate(context, R.layout.view_fight_counter, this)

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

    fun changeValue(changer: Int){
        var value = editText?.getCount() ?: 0
        value += changer
        editText?.setCount(value)
    }

}