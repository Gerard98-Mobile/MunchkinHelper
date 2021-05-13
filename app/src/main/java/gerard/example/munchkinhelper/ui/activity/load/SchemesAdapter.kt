package gerard.example.munchkinhelper.ui.activity.load


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.ui.activity.GAME_KEY
import gerard.example.munchkinhelper.ui.activity.GameActivity
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.model.Scheme
import gerard.example.munchkinhelper.now
import kotlinx.android.synthetic.main.item_card_scheme_new.view.*

class SchemesAdapter(val context: Context, val schemes: MutableList<Scheme>, val callback: SchemeCallback) : RecyclerView.Adapter<SchemesAdapter.SchemeHolder>() {

    fun interface SchemeCallback{
        fun delete(scheme: Scheme)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchemeHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_card_scheme_new, parent, false)
        return SchemeHolder(view)
    }

    override fun onBindViewHolder(holder: SchemeHolder, position: Int) {
        holder.bind(schemes[position])

        holder.itemView.scheme_root.setOnClickListener{
            val intent = Intent(context, GameActivity::class.java)
            val game = Game(now(), schemes[position].players)
            intent.putExtra(GAME_KEY, game)
            context.startActivity(intent)
        }

        holder.itemView.more_icon_click_area.setOnClickListener {
            MoreOptionsPopUp(listOf(Option.DELETE_SCHEME)) { option ->
                when(option){
                    Option.DELETE_SCHEME -> {
                        callback.delete(schemes[holder.layoutPosition])
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

    class SchemeHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind(scheme : Scheme){
            itemView.players.text = scheme.players.joinToString(", ") { it.name }
            itemView.scheme_name.text = scheme.schemeName
        }
    }
}