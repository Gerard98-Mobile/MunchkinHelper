package gerard.example.munchkinhelper.activity

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.adapters.AddedPlayersAdapter
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.model.Player
import gerard.example.munchkinhelper.viewmodels.AddingPlayersVM
import java.lang.IllegalStateException
import androidx.lifecycle.Observer
import gerard.example.munchkinhelper.model.Scheme
import java.util.*

val START_POWER = 0
val START_LVL = 1

class AddingPlayersActivity : AppCompatActivity() {

    val schemeName : MutableLiveData<String> = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding_players)
        val viewmodel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(AddingPlayersVM::class.java)

        val playerList: MutableList<Player> = mutableListOf()

        val recyclerView : RecyclerView = findViewById(R.id.recyclerView_new_players)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = AddedPlayersAdapter(this, playerList)
        recyclerView.adapter = adapter

        val radioButton: RadioButton = findViewById(R.id.radioButton_scheme)

        val startGame : Button = findViewById(R.id.btn_startGame)
        startGame.setOnClickListener {
            if(playerList.size > 1){
                // if radioButton.isChecked user want to add that players as scheme
                if(radioButton.isChecked){
                    schemeName.observe(this, Observer {
                        // inserting scheme to db
                        val scheme = Scheme(playerList,  it)
                        viewmodel.insertScheme(scheme)
                        // starting game
                        val game = Game(Date().time , playerList)
                        startGameIntent(game)
                    })
                    val dialog = SchemeNameDialog(schemeName)
                    dialog.show(supportFragmentManager,"dialog")
                }
                else{
                    // starting game
                    val game = Game(Date().time , playerList)
                    startGameIntent(game)
                }
            }
            else{
                Toast.makeText(this,
                    R.string.player_count_error, Toast.LENGTH_SHORT).show()
            }
        }
        val playerNameEditText : EditText = findViewById(R.id.name_player_editText)
        val addPlayer : Button = findViewById(R.id.add_player_button)
        addPlayer.setOnClickListener {
            val playerName = playerNameEditText.text.toString()
            if(playerName.length > 0 && playerName.length < 20) {
                val player: Player
                player = Player(
                    playerNameEditText.text.toString(),
                    START_POWER,
                    START_LVL
                )

                playerList.add(player)
                adapter.notifyDataSetChanged()
                playerNameEditText.setText("")

                Toast.makeText(this,
                    R.string.player_added, Toast.LENGTH_SHORT).show()

            }else{
                Toast.makeText(this,
                    R.string.name_lenght_error, Toast.LENGTH_LONG).show()
            }

        }

    }

    // function starting game activity
    private fun startGameIntent(game : Game) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra(GAME_KEY, game)
        startActivity(intent)
    }

    // function show dialog for get scheme name from user
    class SchemeNameDialog(val typedName: MutableLiveData<String>) : DialogFragment(){
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            return activity?.let {
                val builder = AlertDialog.Builder(it)

                val inflater = it.layoutInflater
                val view = inflater.inflate(R.layout.dialog_add_scheme, null)

                val editText : EditText = view.findViewById(R.id.editText_scheme_dialog_name)

                val add : TextView = view.findViewById(R.id.txtView_scheme_dialog_add)
                add.setOnClickListener {
                        val name = editText.text.toString()
                        typedName.value = name
                        dismiss()
                }
                val cancel : TextView = view.findViewById(R.id.txtView_scheme_dialog_cancel)
                cancel.setOnClickListener { dismiss() }

                builder.setView(view).create()
            } ?: throw IllegalStateException("Activity cannot be null")
        }
    }
}

