package gerard.example.munchkinhelper.core.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.isVisible
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.colorInt
import gerard.example.munchkinhelper.databinding.EditTextCoreBinding

class MyEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), View.OnFocusChangeListener {

    private val binding: EditTextCoreBinding =
        EditTextCoreBinding.inflate(LayoutInflater.from(context), this, true)

    var error: String? = null
        set(value){
            field = value
            showError(value != null)
        }

    init{
        binding.editText.onFocusChangeListener = this

        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.MyEditText,
            defStyleAttr,
            0
        )

        binding.title.text = a.getText(R.styleable.MyEditText_title)
        binding.title.isVisible = !binding.title.text.isNullOrEmpty()

        a.recycle()
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        showError(error != null)
    }

    fun showError(showError: Boolean){
        binding.error.text = error
        binding.error.isVisible = showError
        binding.container.strokeColor = if(showError) R.color.error.colorInt(context) else R.color.colorPrimary.colorInt(context)
    }

    fun applyTheme(){

    }

}