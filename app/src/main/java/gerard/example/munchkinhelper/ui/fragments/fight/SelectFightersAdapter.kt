package gerard.example.munchkinhelper.ui.fragments.fight

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gerard.example.munchkinhelper.CfgTheme
import gerard.example.munchkinhelper.colorInt
import gerard.example.munchkinhelper.colorStateList
import gerard.example.munchkinhelper.databinding.ItemFigterBinding

class SelectFightersAdapter(
    val context: Context,
    val fighters: List<SelectFightersFragment.FighterModel>,
    val callback: FighterSelectedCallback
) : RecyclerView.Adapter<SelectFightersAdapter.SelectFighterVH>() {

    fun interface FighterSelectedCallback{
        fun selectionChange(newPlayersPower: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectFighterVH {
        val itemBinding = ItemFigterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SelectFighterVH(itemBinding)
    }

    override fun onBindViewHolder(holder: SelectFighterVH, position: Int) {
        val model = fighters.get(position)

        holder.binding.fighterCheckBox.isChecked = model.selected
        holder.binding.fighterCheckBox.setText(model.player.name)

        holder.binding.fighterCheckBox.setTextColor(CfgTheme.current.primaryColor.colorInt(context))
        holder.binding.fighterCheckBox.buttonTintList = CfgTheme.current.primaryColor.colorStateList(context)

        holder.binding.fighterCheckBox.setOnClickListener {
            model.selected = holder.binding.fighterCheckBox.isChecked
            callback.selectionChange(if(model.selected) model.player.getAbsolutePowerInt() else model.player.getAbsolutePowerInt() *-1)
        }
    }


    override fun getItemCount(): Int {
        return fighters.size
    }

    class SelectFighterVH(val binding: ItemFigterBinding) : RecyclerView.ViewHolder(binding.root)
}