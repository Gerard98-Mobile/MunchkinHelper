package gerard.example.munchkinhelper.ui.activity.load

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gerard.example.munchkinhelper.CfgTheme
import gerard.example.munchkinhelper.colorInt
import gerard.example.munchkinhelper.colorStateList
import gerard.example.munchkinhelper.core.recycler.CustomViewHolder
import gerard.example.munchkinhelper.core.recycler.SingleRecyclerAdapter
import gerard.example.munchkinhelper.databinding.ItemCardHistoryBinding
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.util.Action
import gerard.example.munchkinhelper.util.Callback
import gerard.example.munchkinhelper.util.DateUtil
import java.util.Date

class HistoryGameAdapter(context: Context, val games: MutableList<Game>, val callback: Callback<Game>) : SingleRecyclerAdapter<Game, ItemCardHistoryBinding>(context)  {

    init{
        register(games, ::bind, ItemCardHistoryBinding::inflate)
    }

    fun bind(holder: CustomViewHolder<ItemCardHistoryBinding>, game: Game){
        holder.binding.players.text = game.players.joinToString(", ") { "${it.name} ${it.lvl} lvl" }
        val date = DateUtil.dateDDMMYYYYdash().format(Date(game.saveDate))
        holder.binding.gameDate.text = date.toString()

        // listeners for open game
        holder.binding.root.setOnClickListener {
            callback.execute(game, Action.OPEN)
        }

        with(CfgTheme.current.primaryColor.colorInt(context)){
            holder.binding.players.setTextColor(this)
            holder.binding.gameDate.setTextColor(this)
            holder.binding.root.strokeColor = this
        }

        holder.binding.more.imageTintList = CfgTheme.current.primaryColor.colorStateList(context)
        holder.binding.root.setCardBackgroundColor(CfgTheme.current.backgroundColor.colorStateList(context))


        holder.binding.moreIconClickArea.setOnClickListener{
            MoreOptionsPopUp(listOf(
                Option.DELETE_GAME
            )) { option ->
                when(option){
                    Option.DELETE_GAME -> {
                        callback.execute(games[holder.layoutPosition], Action.DELETE)
                        games.removeAt(holder.layoutPosition)
                        notifyItemRemoved(holder.layoutPosition)
                    }
                }
            }.show(it)
        }
    }

}