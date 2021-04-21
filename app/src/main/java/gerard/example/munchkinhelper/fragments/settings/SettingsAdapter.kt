package gerard.example.munchkinhelper.fragments.settings

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.core.recycler.ViewHolder

class SettingsAdapter(
    val context: Context,
    data: List<Any>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val data : List<Any>

    init{
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            1 -> SettingVH(View.inflate(context, R.layout.item_setting, parent))
            else -> SettingVH(View.inflate(context, R.layout.item_setting, parent))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is SettingVH -> holder.bind("Siema xD")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class SettingVH(itemView: View) : ViewHolder<String>(itemView){
        override fun bind(value: String) {

        }
    }

}