package gerard.example.munchkinhelper.ui.activity.create

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gerard.example.munchkinhelper.CfgTheme
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.colorInt
import gerard.example.munchkinhelper.colorStateList
import gerard.example.munchkinhelper.core.recycler.CustomViewHolder
import gerard.example.munchkinhelper.core.recycler.SingleRecyclerAdapter
import gerard.example.munchkinhelper.model.Player
import gerard.example.munchkinhelper.databinding.ItemAddedPlayerBinding

class AddedPlayersAdapter(context: Context, val players: MutableList<Player>) : SingleRecyclerAdapter<Player, ItemAddedPlayerBinding>(context) {

    init{
        register(players, ::bind, ItemAddedPlayerBinding::inflate)
    }

    private fun bind(holder: CustomViewHolder<ItemAddedPlayerBinding>, player: Player){

        holder.binding.remove.imageTintList = CfgTheme.current.primaryColor.colorStateList(context)
        holder.binding.name.setTextColor(CfgTheme.current.primaryColor.colorInt(context))

        holder.binding.name.text = player.name

        holder.binding.remove.setOnClickListener {
            players.remove(player)
            notifyItemRemoved(holder.layoutPosition)
        }
    }

}