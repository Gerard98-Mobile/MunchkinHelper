package gerard.example.munchkinhelper.ui.fragments.fight

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gerard.example.munchkinhelper.R
import kotlinx.android.synthetic.main.item_figter.view.*

class SelectFightersAdapter(
    val context: Context,
    val fighters: List<SelectFightersFragment.FighterModel>,
    val callback: FighterSelectedCallback
) : RecyclerView.Adapter<SelectFightersAdapter.SelectFighterVH>() {

    fun interface FighterSelectedCallback{
        fun selectionChange(newPlayersPower: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectFighterVH {
        return SelectFighterVH(LayoutInflater.from(context).inflate(R.layout.item_figter, parent, false))
    }

    override fun onBindViewHolder(holder: SelectFighterVH, position: Int) {
        val model = fighters.get(position)

        holder.itemView.fighter_checkBox.isChecked = model.selected
        holder.itemView.fighter_checkBox.setText(model.player.name)

        holder.itemView.fighter_checkBox.setOnClickListener {
            model.selected = holder.itemView.fighter_checkBox.isChecked
            callback.selectionChange(if(model.selected) model.player.getAbsolutePowerInt() else model.player.getAbsolutePowerInt() *-1)
        }


    }

    fun getPower() : Int{
        var power = 0
        fighters.forEach { if(it.selected) power += it.player.getAbsolutePowerInt() }
        return power
    }

    override fun getItemCount(): Int {
        return fighters.size
    }

    class SelectFighterVH(itemView: View) : RecyclerView.ViewHolder(itemView)
}