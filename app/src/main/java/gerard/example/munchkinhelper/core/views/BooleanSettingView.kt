package gerard.example.munchkinhelper.core.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.CompoundButton
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import gerard.example.munchkinhelper.*
import gerard.example.munchkinhelper.databinding.ItemBooleanSettingBinding
import gerard.example.munchkinhelper.databinding.PowerEditTextBinding

class BooleanSettingView : FrameLayout, CfgTheme.ThemeChangedListener {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, attributeSetId: Int) : super(context, attrs, attributeSetId)

    val binding: ItemBooleanSettingBinding =
        ItemBooleanSettingBinding.inflate(LayoutInflater.from(context), this, false)

    init{
        if(!isInEditMode) onThemeChanged(CfgTheme.current)
    }

    fun setOnCheckedChangeListener(exec: (buttonView: CompoundButton, isChecked: Boolean) -> Unit){
        binding.switchButton.setOnCheckedChangeListener { buttonView, isChecked -> exec(buttonView, isChecked) }
    }

    fun setText(@StringRes stringRes: Int){
        binding.switchButton.setText(stringRes)
    }

    fun setImageProperties(@DrawableRes imgRes: Int, newVisibility: Int){
        binding.settingImg.setImageResource(imgRes)
        binding.settingImg.visibility = newVisibility
    }

    override fun onThemeChanged(theme: Theme) {
        binding.settingImg.imageTintList = CfgTheme.current.booleanSetting.imageTint.colorStateList(context)
        binding.switchButton.setTextColor(CfgTheme.current.booleanSetting.textColor.colorInt(context))
        binding.switchButton.thumbTintList = CfgTheme.current.booleanSetting.thumbTint.colorStateList(context)
        binding.switchButton.trackTintList = CfgTheme.current.booleanSetting.trackTint.colorStateList(context)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if(!isInEditMode) CfgTheme.addListener(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if(!isInEditMode) CfgTheme.removeListener(this)
    }
}