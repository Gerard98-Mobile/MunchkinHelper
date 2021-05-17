package gerard.example.munchkinhelper.ui.activity.load


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.model.Scheme
import gerard.example.munchkinhelper.util.Action
import gerard.example.munchkinhelper.util.Callback
import kotlinx.android.synthetic.main.item_card_scheme_new.view.*

class SchemesAdapter(val context: Context, val schemes: MutableList<Scheme>, val callback: Callback<Scheme>) : RecyclerView.Adapter<SchemesAdapter.SchemeHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchemeHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_card_scheme_new, parent, false)
        return SchemeHolder(view)
    }

    override fun onBindViewHolder(holder: SchemeHolder, position: Int) {
        holder.bind(schemes[position])

        holder.itemView.scheme_root.setOnClickListener{
            callback.execute(schemes[position], Action.OPEN)
        }

        holder.itemView.more_icon_click_area.setOnClickListener {
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

    class SchemeHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind(scheme : Scheme){
            itemView.players.text = scheme.players.joinToString(", ") { it.name }
            itemView.scheme_name.text = scheme.schemeName
        }
    }
}