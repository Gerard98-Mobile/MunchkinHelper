package gerard.example.munchkinhelper.ui.activity.create

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.model.Player
import com.google.android.material.snackbar.Snackbar
import gerard.example.munchkinhelper.model.Scheme
import gerard.example.munchkinhelper.ui.activity.GAME_KEY
import gerard.example.munchkinhelper.ui.activity.GameActivity
import kotlinx.android.synthetic.main.activity_adding_players.*
import kotlinx.android.synthetic.main.dialog_add_scheme_new.*
import java.util.Date
import kotlin.collections.ArrayDeque

const val START_POWER = 0
const val START_LVL = 1

class AddingPlayersActivity : AppCompatActivity() {

    fun interface SchemeCallback{
        fun create(name: String)
    }

    val viewmodel by viewModels<AddingPlayersVM>()
    val playerList = mutableListOf<Player>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding_players)

        val adapter = AddedPlayersAdapter(this, playerList)
        recyclerView_new_players.adapter = adapter

        name_player.setOnKeyListener { v, keyCode, event ->
            if(event.keyCode == KeyEvent.KEYCODE_ENTER){
                if(event.action == KeyEvent.ACTION_DOWN) add_player.performClick()
                return@setOnKeyListener true
            }
            false
        }

        btn_startGame.setOnClickListener {
            if(playerList.size < 2) {
                Snackbar.make(findViewById(android.R.id.content), R.string.player_count_error, Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            when(checkbox_scheme.isChecked){
                true -> {
                    SchemeNameDialog(this) {
                        viewmodel.insertScheme(Scheme(playerList, it))
                        startGameIntent()
                    }.show()
                }
                false -> startGameIntent()
            }
        }


        add_player.setOnClickListener {
            val playerName = name_player.text.toString()
            if(playerName.length in 1..20) {
                playerList.add(0, Player(name_player.text.toString(), START_POWER, START_LVL))
                (recyclerView_new_players.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(0,0)
                adapter.notifyItemInserted(0)
                name_player.setText("")
            }else{
                Snackbar.make(findViewById(android.R.id.content), R.string.name_lenght_error, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    // function starting game activity
    private fun startGameIntent() {
        val game = Game(Date().time , playerList)
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra(GAME_KEY, game)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        name_player.requestFocus()
    }

    // function show dialog for get scheme name from user
    class SchemeNameDialog(context: Context, val callback: SchemeCallback) : Dialog(context) {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setContentView(R.layout.dialog_add_scheme_new)
            yes.setOnClickListener {
                if(scheme_name.text.toString().length < 4) {
                    scheme_name.background = ContextCompat.getDrawable(context, R.drawable.edit_text_error)
                    return@setOnClickListener
                }
                callback.create(scheme_name.text.toString())
                dismiss()
            }
            cancel.setOnClickListener { dismiss() }
        }
    }
}

