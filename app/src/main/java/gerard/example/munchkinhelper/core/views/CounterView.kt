package gerard.example.munchkinhelper.core.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import gerard.example.munchkinhelper.CfgTheme
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.colorInt
import gerard.example.munchkinhelper.colorStateList
import gerard.example.munchkinhelper.databinding.ViewCounterBinding
import gerard.example.munchkinhelper.ui.activity.create.START_LVL

class CounterView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    fun interface OnChangeListener{
        fun onChange(newValue: Int, goUp: Boolean)
    }

    private val binding: ViewCounterBinding =
        ViewCounterBinding.inflate(LayoutInflater.from(context), this, true)

    var value: Int? = null
    var onChangeListener : OnChangeListener? = null

    init{
        val a = context.obtainStyledAttributes(attrs, R.styleable.Counter, defStyleAttr, 0)
        val title = a.getString(R.styleable.Counter_counterTitle)
        binding.txtViewTitle.text = title

        binding.imgButtonReduce.setOnClickListener {
            value?.let {
                val newValue = it-1
                if(newValue < START_LVL) return@let
                value = newValue
                binding.txtViewValue.text = newValue.toString()
                onChangeListener?.onChange(newValue, false)
            }
        }
        binding.imgButtonAdd.setOnClickListener {
            value?.let {
                val newValue = it+1
                value = newValue
                binding.txtViewValue.text = newValue.toString()
                onChangeListener?.onChange(newValue, true)
            }
        }

        a.recycle()
    }

    fun attachValue(value: Int){
        this.value = value
        binding.txtViewValue.text = value.toString()
    }

    fun applyTheme() = binding.run {
        with(CfgTheme.current.primaryColor.colorInt(context)){
            txtViewValue.setTextColor(this)
            imgButtonReduce.strokeColor = this
            imgButtonAdd.strokeColor = this
            txtViewTitle.setTextColor(this)
        }
        with(CfgTheme.current.primaryColor.colorStateList(context)){
            plus.imageTintList = this
            minus.imageTintList = this
        }
        with(CfgTheme.current.backgroundColor.colorInt(context)){
            imgButtonReduce.setCardBackgroundColor(this)
            imgButtonAdd.setCardBackgroundColor(this)
        }
    }

}