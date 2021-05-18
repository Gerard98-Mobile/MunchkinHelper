package gerard.example.munchkinhelper.core.views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import gerard.example.munchkinhelper.CfgTheme
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.colorInt
import gerard.example.munchkinhelper.colorStateList
import gerard.example.munchkinhelper.ui.activity.create.START_LVL
import kotlinx.android.synthetic.main.view_counter.view.*

class CounterView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    fun interface OnChangeListener{
        fun onChange(newValue: Int, goUp: Boolean)
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
                if(newValue < START_LVL) return@let
                value = newValue
                txtView_value.text = newValue.toString()
                onChangeListener?.onChange(newValue, false)
            }
        }
        imgButton_add.setOnClickListener {
            value?.let {
                val newValue = it+1
                value = newValue
                txtView_value.text = newValue.toString()
                onChangeListener?.onChange(newValue, true)
            }
        }

        a.recycle()
    }

    fun attachValue(value: Int){
        this.value = value
        txtView_value.text = value.toString()
    }

    fun applyTheme() {
        with(CfgTheme.current.primaryColor.colorInt(context)){
            txtView_title.setTextColor(this)
            imgButton_reduce.strokeColor = this
            imgButton_add.strokeColor = this
            txtView_value.setTextColor(this)
        }
        with(CfgTheme.current.primaryColor.colorStateList(context)){
            plus.imageTintList = this
            minus.imageTintList = this
        }
        with(CfgTheme.current.backgroundColor.colorInt(context)){
            imgButton_reduce.setCardBackgroundColor(this)
            imgButton_add.setCardBackgroundColor(this)
        }
    }

}