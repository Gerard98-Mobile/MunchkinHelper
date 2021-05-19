package gerard.example.munchkinhelper.ui.fragments.settings

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gerard.example.munchkinhelper.*
import gerard.example.munchkinhelper.core.recycler.ViewHolder
import gerard.example.munchkinhelper.databinding.ItemBooleanSettingBinding
import gerard.example.munchkinhelper.databinding.ItemPlayerBinding
import gerard.example.munchkinhelper.util.Action
import gerard.example.munchkinhelper.util.Callback


class SettingAdapter(
    val context: Context,
    val data : List<Setting<*>>,
    val callback: Callback<Setting<*>>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            0 -> return SettingBooleanVH(
                ItemBooleanSettingBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                callback
            )
            // 1 TODO
            // 2 TODO
            else -> return SettingBooleanVH(
                ItemBooleanSettingBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                callback
            )
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is SettingBooleanVH -> bindBooleanSetting(holder, data[position] as Setting<Boolean>)
        }
    }

    private fun bindBooleanSetting(holder: SettingBooleanVH, value: Setting<Boolean>){
        holder.bind(value)
        holder.binder.settingImg.imageTintList = CfgTheme.current.primaryColor.colorStateList(context)
        holder.binder.switchButton.setTextColor(CfgTheme.current.primaryColor.colorInt(context))
        holder.binder.switchButton.thumbTintList = CfgTheme.current.switchState.colorStateList(context)
        holder.binder.switchButton.trackTintList = CfgTheme.current.switchTrack.colorStateList(context)

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
        val binder: ItemBooleanSettingBinding,
        val callback: Callback<Setting<*>>
    ) : ViewHolder<Setting<Boolean>>(binder.root){

        override fun bind(value: Setting<Boolean>) {
            binder.switchButton.setText(value.displayName)
            value.displayIcon?.let {
                binder.settingImg.setImageResource(value.displayIcon)
                binder.settingImg.visibility = View.VISIBLE
            }
            binder.switchButton.isChecked = value.value.get() ?: false
            binder.switchButton.setOnCheckedChangeListener { compoundButton, b ->
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