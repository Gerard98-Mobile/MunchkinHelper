package gerard.example.munchkinhelper.ui.fragments.game

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import gerard.example.munchkinhelper.*
import gerard.example.munchkinhelper.core.recycler.CustomViewHolder
import gerard.example.munchkinhelper.core.recycler.SingleRecyclerAdapter
import gerard.example.munchkinhelper.databinding.ItemPlayerBinding
import gerard.example.munchkinhelper.model.Player

class GamePlayerAdapter(context: Context, val players: List<Player>) : SingleRecyclerAdapter<Player, ItemPlayerBinding>(context)  {

    init{
        register(players, ::bind, ItemPlayerBinding::inflate)
    }

    val selectedPlayer : MutableLiveData<Player> by lazy{
        MutableLiveData<Player>();
    }
    var selectedView: View? = null;

    fun bind(holder: CustomViewHolder<ItemPlayerBinding>, player: Player) = holder.binding.run{
        txtViewPlayerNameItem.text = player.name
        txtViewPowerItem.text = player.getAbsolutePower()
        txtViewLevelItem.text = player.lvl.toString()
        imgViewLeader.visibility = if(player.isLeader) View.VISIBLE else View.INVISIBLE
        deathCount.isVisible = player.deaths > 0 && Cfg.showDeathCount.value.get() == true
        deathCount.text = context.getString(R.string.death_count, player.deaths)

        with(CfgTheme.current.primaryColor.colorInt(context)){
            txtViewPlayerNameItem.setTextColor(this)
            txtViewPowerItem.setTextColor(this)
            txtViewLevelItem.setTextColor(this)
        }

        if(selectedView != holder.itemView){
            linearLayoutPlayerContainer.setCardBackgroundColor(CfgTheme.current.backgroundColor.colorInt(context))
            linearLayoutPlayerContainer.strokeColor = CfgTheme.current.backgroundColor.colorInt(context)
        }

        linearLayoutPlayerContainer.setOnClickListener {
            if(selectedView != it){
                linearLayoutPlayerContainer.strokeColor = CfgTheme.current.primaryColor.colorInt(context)
                (selectedView as? MaterialCardView)?.strokeColor = CfgTheme.current.backgroundColor.colorInt(context)
                selectedView = linearLayoutPlayerContainer
                selectedPlayer.value = player
            }
        }
    }
}
