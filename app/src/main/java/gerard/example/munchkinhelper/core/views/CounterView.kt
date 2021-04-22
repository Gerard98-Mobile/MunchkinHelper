package gerard.example.munchkinhelper.core.views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import gerard.example.munchkinhelper.R
import kotlinx.android.synthetic.main.view_counter.view.*

class CounterView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    fun interface OnChangeListener{
        fun onChange(newValue: Int)
    }

    var value: Int? = null
    var onChangeListener : OnChangeListener? = null

    init{
        inflate(context, R.layout.view_counter, this)

        val a = context.obtainStyledAttributes(attrs, R.styleable.Counter, defStyleAttr, 0)
        val title = a.getString(R.styleable.Counter_title)
        txtView_title.setText(title)

        imgButton_reduce.setOnClickListener {
            value?.let {
                val newValue = it-1
                if(newValue < 0) return@let
                value = newValue
                txtView_value.text = newValue.toString()
                onChangeListener?.onChange(newValue)
            }
        }
        imgButton_add.setOnClickListener {
            value?.let {
                val newValue = it+1
                value = newValue
                txtView_value.text = newValue.toString()
                onChangeListener?.onChange(newValue)
            }
        }

        a.recycle()
    }

    fun attachValue(value: Int){
        this.value = value
        txtView_value.setText(value.toString())
    }

}