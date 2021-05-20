package gerard.example.munchkinhelper.ui.activity.load


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gerard.example.munchkinhelper.*
import gerard.example.munchkinhelper.core.recycler.CustomViewHolder
import gerard.example.munchkinhelper.core.recycler.SingleRecyclerAdapter
import gerard.example.munchkinhelper.databinding.ItemCardSchemeBinding
import gerard.example.munchkinhelper.model.Scheme
import gerard.example.munchkinhelper.util.Action
import gerard.example.munchkinhelper.util.Callback

class SchemesAdapter(context: Context, val schemes: MutableList<Scheme>, val callback: Callback<Scheme>) : SingleRecyclerAdapter<Scheme, ItemCardSchemeBinding>(context) {

    init{
        register(schemes, ::bind, ItemCardSchemeBinding::inflate)
    }

    fun bind(holder: CustomViewHolder<ItemCardSchemeBinding>, scheme: Scheme){

        holder.binding.players.text = scheme.players.joinToString(", ") { it.name }
        holder.binding.schemeName.text = scheme.schemeName

        holder.binding.schemeRoot.setOnClickListener{
            callback.execute(scheme, Action.OPEN)
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

}