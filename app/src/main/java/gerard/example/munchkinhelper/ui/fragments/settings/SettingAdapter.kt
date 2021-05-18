package gerard.example.munchkinhelper.ui.fragments.settings

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.core.recycler.ViewHolder
import gerard.example.munchkinhelper.pickLayout
import gerard.example.munchkinhelper.util.Action
import gerard.example.munchkinhelper.util.Callback
import kotlinx.android.synthetic.main.item_boolean_setting.view.*

class SettingAdapter(
    val context: Context,
    val data : List<Setting<*>>,
    val callback: Callback<Setting<*>>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            0 -> return SettingBooleanVH(
                LayoutInflater.from(context).inflate(
                    pickLayout(R.layout.item_boolean_setting, R.layout.item_boolean_setting_dark), parent, false
                ),
                callback
            )
            // 1 TODO
            // 2 TODO
            else -> return SettingBooleanVH(
                LayoutInflater.from(context).inflate(
                    pickLayout(R.layout.item_boolean_setting, R.layout.item_boolean_setting_dark), parent, false
                ),
                callback
            )
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is SettingBooleanVH -> holder.bind(data[position] as Setting<Boolean>)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(data[position].value.clazz.name){
            "java.lang.Boolean","boolean" -> 0
            "java.lang.String","string" -> 1
            "java.lang.Integer","integer"  -> 2
            else -> 0
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class SettingBooleanVH(
        itemView: View,
        val callback: Callback<Setting<*>>
    ) : ViewHolder<Setting<Boolean>>(itemView){

        override fun bind(value: Setting<Boolean>) {
            itemView.switch_button.setText(value.displayName)
            value.displayIcon?.let {
                itemView.setting_img.setImageResource(value.displayIcon)
                itemView.setting_img.visibility = View.VISIBLE
            }
            itemView.switch_button.isChecked = value.value.get() ?: false
            itemView.switch_button.setOnCheckedChangeListener { compoundButton, b ->
                value.value.set(b)
                callback.execute(value, Action.NONE)
            }
        }
    }

    class SettingIntVH(itemView: View) : ViewHolder<Setting<Int>>(itemView){
        override fun bind(value: Setting<Int>) {
            // TODO if needed
        }
    }

    class SettingTextVH(itemView: View) : ViewHolder<Setting<String>>(itemView){
        override fun bind(value: Setting<String>) {
            // TODO if needed
        }
    }

}