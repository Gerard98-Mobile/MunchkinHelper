package gerard.example.munchkinhelper.ui.activity.load

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.model.Scheme
import gerard.example.munchkinhelper.ui.activity.create.AddingPlayersActivity

class LoadGameActivity : AppCompatActivity() {

    var schemes : MutableList<Scheme>? = null
    var historyGames : MutableList<Game>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_game)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView_load_activity)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val viewmodel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(
            LoadGameVM::class.java)

        // when user select game to delete, this will be called
        viewmodel.gameToDelete.observe(this, Observer {
            viewmodel.removeGame(it)
        })
        // when user select scheme to delete, this will be called
        viewmodel.schemeToDelete.observe(this, Observer {
            viewmodel.removeScheme(it)
        })

        // on load schemes from db
        viewmodel.schemes.observe(this, Observer {
            schemes = it.toMutableList()
        })

        // on load games from db
        viewmodel.games.observe(this, Observer {
            historyGames = it.toMutableList()

            val adapter = HistoryGamesAdapter(this, historyGames!!, viewmodel.gameToDelete)
            recyclerView.adapter = adapter
        })
        // call viewmodel to start loading data
        viewmodel.loadAllObjects()

        // text views from navbar
        val txtViewSchemes : TextView = findViewById(R.id.txtView_scheme)
        val txtViewHistory : TextView = findViewById(R.id.txtView_history)

        // chaning displaying data from histories to schemes
        txtViewSchemes.setOnClickListener {
            // set schemes textView selected
            txtViewSchemes.setBackgroundResource(R.drawable.bottom_border_primary_bold)
            txtViewSchemes.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary))
            // set history textView unselected
            txtViewHistory.setBackgroundResource(R.drawable.bottom_border_primary)
            txtViewHistory.setTextColor(ContextCompat.getColor(this,R.color.colorGrey))

            // setting adapter = null to clear data
            recyclerView.adapter = null
            // cheking that there is at least one object of schemes
            if(schemes?.size?.compareTo(0) != 0){
                recyclerView.adapter = SchemesAdapter(this, schemes!!, viewmodel.schemeToDelete)
            }
            else{
                Toast.makeText(this, R.string.no_schemes, Toast.LENGTH_SHORT).show()
            }


        }

        // changing displaying data from schemes to histories
        txtViewHistory.setOnClickListener {
            // selected
            txtViewSchemes.setBackgroundResource(R.drawable.bottom_border_primary)
            txtViewSchemes.setTextColor(ContextCompat.getColor(this,R.color.colorGrey))
            // unselected
            txtViewHistory.setBackgroundResource(R.drawable.bottom_border_primary_bold)
            txtViewHistory.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary))

            // setting adapter = null to clear data
            recyclerView.adapter = null
            // cheking that there is at least one object of history games
            if(historyGames?.size?.compareTo(0) != 0){
                recyclerView.adapter = HistoryGamesAdapter(this, historyGames!!, viewmodel.gameToDelete)
            }
            else{
                Toast.makeText(this, R.string.no_history_games, Toast.LENGTH_SHORT).show()
            }
        }

        // button in bottom of screen, on click going to AddingPLayersActivity
        val startButton : Button = findViewById(R.id.btn_startNewGame)
        startButton.setOnClickListener {
            val intent = Intent(this, AddingPlayersActivity::class.java)
            startActivity(intent)
        }
    }
}