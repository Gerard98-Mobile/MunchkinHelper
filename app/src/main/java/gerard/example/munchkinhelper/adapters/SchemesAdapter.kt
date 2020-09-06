package gerard.example.munchkinhelper.adapters

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.activity.GAME_KEY
import gerard.example.munchkinhelper.activity.GameActivity
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.model.Scheme
import java.util.*

class SchemesAdapter(val context: Context, val schemes: MutableList<Scheme>, val schemeToDelete: MutableLiveData<Scheme>) : RecyclerView.Adapter<SchemesAdapter.SchemeHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchemeHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_card_scheme, parent, false)
        return SchemeHolder(view)
    }

    override fun onBindViewHolder(holder: SchemeHolder, position: Int) {
        holder.bind(schemes.get(position))
    }

    override fun getItemCount(): Int {
        return schemes.size
    }

    inner class SchemeHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val txtViewSchemePlayers : TextView
        val txtViewSchemeName : TextView
        val imgBtnDeleteScheme : ImageButton
        val linearLayoutScheme : LinearLayout

        init{
            txtViewSchemePlayers = itemView.findViewById(R.id.txtView_scheme_players)
            txtViewSchemeName = itemView.findViewById(R.id.txtView_scheme_name)
            imgBtnDeleteScheme = itemView.findViewById(R.id.imgButton_delete_scheme)
            linearLayoutScheme = itemView.findViewById(R.id.linearLayout_card_scheme)
        }

        fun bind(scheme : Scheme){
            var playersString : String = ""
            var first = true
            // gather all player names in one string to display it on screen
            scheme.players.forEach {
                // adding enter after each player except first
                if (first) {
                    first = !first
                } else {
                    playersString += "\n"
                }
                playersString += it.name
            }
            // displaying data on screen
            txtViewSchemePlayers.setText(playersString)
            txtViewSchemeName.setText(scheme.schemeName)

            imgBtnDeleteScheme.setOnClickListener{
                // showing dialog to check that player want to delete that scheme
                showDialog(scheme)
                notifyDataSetChanged()
            }

            // on click of item we are starting game activity
            linearLayoutScheme.setOnClickListener{
                val intent = Intent(context, GameActivity::class.java)
                val game = Game(Date().time, scheme.players)
                intent.putExtra(GAME_KEY, game)
                context.startActivity(intent)
            }
        }

    }

    fun showDialog(scheme: Scheme){

        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_custom)

        val title = dialog.findViewById(R.id.txtView_dialog_title) as TextView
        title.setText(context.getString(R.string.deleting_scheme_title))
        val body = dialog.findViewById(R.id.txtView_dialog_body) as TextView
        body.setText(context.getString(R.string.deleting_scheme_body))

        val yesBtn = dialog.findViewById(R.id.txtView_dialog_yes) as TextView
        val noBtn = dialog.findViewById(R.id.txtView_dialog_no) as TextView
        // on click yes we are deleting data from db
        yesBtn.setOnClickListener {
            // inform that we want to delete that scheme from db
            schemeToDelete.value = scheme
            // deleting that scheme from adapter item list
            schemes.remove(scheme)
            // notifing that we changed item list
            notifyDataSetChanged()
            // end dialog
            dialog.dismiss()
        }
        noBtn.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

}