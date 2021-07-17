package gerard.example.munchkinhelper.ui.fragments.settings

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gerard.example.munchkinhelper.*
import gerard.example.munchkinhelper.core.recycler.CustomViewHolder
import gerard.example.munchkinhelper.core.recycler.MultiRecyclerAdapter
import gerard.example.munchkinhelper.core.recycler.ViewHolder
import gerard.example.munchkinhelper.databinding.ItemBooleanSettingBinding
import gerard.example.munchkinhelper.databinding.ItemPlayerBinding
import gerard.example.munchkinhelper.util.Action
import gerard.example.munchkinhelper.util.Callback


class SettingAdapter(
    context: Context,
    val settings : List<Setting<*>>,
    val callback: Callback<Setting<*>>
) : MultiRecyclerAdapter<Any>(context) {

    init{
        this.data = settings
        register({ checkType<Setting<Boolean>>(it)}, ::bindBooleanSetting, ItemBooleanSettingBinding::inflate)
    }

    private fun bindBooleanSetting(holder: CustomViewHolder<ItemBooleanSettingBinding>, value: Setting<Boolean>) = holder.binding.run{
        switchButton.setText(value.displayName)
        value.displayIcon?.let {
            settingImg.setImageResource(value.displayIcon)
            settingImg.visibility = View.VISIBLE
        }
        switchButton.isChecked = value.value.get() ?: false
        switchButton.setOnCheckedChangeListener { compoundButton, b ->
            value.value.set(b)
            callback.execute(value, Action.NONE)
        }
    }


}