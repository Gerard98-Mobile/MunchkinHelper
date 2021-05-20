package gerard.example.munchkinhelper.ui.fragments.fight

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gerard.example.munchkinhelper.CfgTheme
import gerard.example.munchkinhelper.colorInt
import gerard.example.munchkinhelper.colorStateList
import gerard.example.munchkinhelper.core.recycler.CustomViewHolder
import gerard.example.munchkinhelper.core.recycler.SingleRecyclerAdapter
import gerard.example.munchkinhelper.databinding.ItemFigterBinding

class SelectFightersAdapter(
    context: Context,
    fighters: List<SelectFightersFragment.FighterModel>,
    val callback: FighterSelectedCallback
) : SingleRecyclerAdapter<SelectFightersFragment.FighterModel, ItemFigterBinding>(context) {

    fun interface FighterSelectedCallback{
        fun selectionChange(newPlayersPower: Int)
    }

    init{
        register(fighters, ::bind, ItemFigterBinding::inflate)
    }

    fun bind(holder: CustomViewHolder<ItemFigterBinding>, model: SelectFightersFragment.FighterModel){
        holder.binding.fighterCheckBox.isChecked = model.selected
        holder.binding.fighterCheckBox.setText(model.player.name)

        holder.binding.fighterCheckBox.setTextColor(CfgTheme.current.primaryColor.colorInt(context))
        holder.binding.fighterCheckBox.buttonTintList = CfgTheme.current.primaryColor.colorStateList(context)

        holder.binding.fighterCheckBox.setOnClickListener {
            model.selected = holder.binding.fighterCheckBox.isChecked
            callback.selectionChange(if(model.selected) model.player.getAbsolutePowerInt() else model.player.getAbsolutePowerInt() *-1)
        }
    }

}