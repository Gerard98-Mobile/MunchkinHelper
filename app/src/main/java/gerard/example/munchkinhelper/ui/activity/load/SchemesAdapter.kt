package gerard.example.munchkinhelper.ui.activity.load


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gerard.example.munchkinhelper.*
import gerard.example.munchkinhelper.databinding.ItemCardSchemeBinding
import gerard.example.munchkinhelper.model.Scheme
import gerard.example.munchkinhelper.util.Action
import gerard.example.munchkinhelper.util.Callback

class SchemesAdapter(val context: Context, val schemes: MutableList<Scheme>, val callback: Callback<Scheme>) : RecyclerView.Adapter<SchemesAdapter.SchemeHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchemeHolder {
        val itemBinding = ItemCardSchemeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SchemeHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: SchemeHolder, position: Int) {
        holder.bind(schemes[position])

        holder.binding.schemeRoot.setOnClickListener{
            callback.execute(schemes[position], Action.OPEN)
        }


        with(CfgTheme.current.primaryColor.colorInt(context)){
            holder.binding.players.setTextColor(this)
            holder.binding.schemeName.setTextColor(this)
            holder.binding.schemeRoot.strokeColor = this
        }

        holder.binding.more.imageTintList = CfgTheme.current.primaryColor.colorStateList(context)
        holder.binding.schemeRoot.setCardBackgroundColor(CfgTheme.current.backgroundColor.colorStateList(context))

        holder.binding.moreIconClickArea.setOnClickListener {
            MoreOptionsPopUp(listOf(Option.DELETE_SCHEME)) { option ->
                when(option){
                    Option.DELETE_SCHEME -> {
                        callback.execute(schemes[holder.layoutPosition], Action.DELETE)
                        schemes.removeAt(holder.layoutPosition)
                        notifyItemRemoved(holder.layoutPosition)
                    }
                    else -> {}
                }

            }.show(it)
        }
    }

    override fun getItemCount(): Int {
        return schemes.size
    }

    class SchemeHolder(val binding : ItemCardSchemeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(scheme : Scheme){
            binding.players.text = scheme.players.joinToString(", ") { it.name }
            binding.schemeName.text = scheme.schemeName
        }
    }
}