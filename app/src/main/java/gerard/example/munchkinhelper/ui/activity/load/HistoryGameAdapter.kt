package gerard.example.munchkinhelper.ui.activity.load

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gerard.example.munchkinhelper.CfgTheme
import gerard.example.munchkinhelper.colorInt
import gerard.example.munchkinhelper.colorStateList
import gerard.example.munchkinhelper.databinding.ItemCardHistoryBinding
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.util.DateUtil
import java.util.Date

class HistoryGameAdapter(val context: Context, val games: MutableList<Game>, val callback: Callback) : RecyclerView.Adapter<HistoryGameAdapter.HistoryGameHolder>()  {

    enum class Action{ DELETE, OPEN }

    fun interface Callback{
        fun execute(game: Game, action: Action)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryGameHolder {
        val itemBinding = ItemCardHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryGameHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: HistoryGameHolder, position: Int) {
        holder.bind(games[position])

        // listeners for open game
        holder.binding.root.setOnClickListener {
            callback.execute(games[position], Action.OPEN)
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

    override fun getItemCount(): Int {
        return games.size
    }

    class HistoryGameHolder(val binding : ItemCardHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(game : Game){
            binding.players.text = game.players.joinToString(", ") { "${it.name} ${it.lvl} lvl" }
            val date = DateUtil.dateDDMMYYYYdash().format(Date(game.saveDate))
            binding.gameDate.text = date.toString()
        }

    }
}