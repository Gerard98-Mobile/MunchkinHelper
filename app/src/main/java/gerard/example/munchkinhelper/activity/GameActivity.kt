package gerard.example.munchkinhelper.activity


import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import gerard.example.munchkinhelper.MainActivity
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.fragments.DiceFragment
import gerard.example.munchkinhelper.fragments.FightFragment
import gerard.example.munchkinhelper.fragments.GameFragment
import gerard.example.munchkinhelper.fragments.settings.SettingsFragment
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.viewmodels.GameVM

val GAME_KEY = "game_key"

class GameActivity : AppCompatActivity() {

    var navigation: BottomNavigationView? = null
    var actuallyFragment: Fragment? = null
    var viewmodel: GameVM? = null
    var game: Game? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        viewmodel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(GameVM::class.java)
        setObservers()

        game = intent.getSerializableExtra(GAME_KEY) as Game?

        changeFragment(GameFragment.newInstance(game))

        navigation = findViewById(R.id.navigation)
        navigation!!.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_game -> changeFragment(GameFragment.newInstance(game))
                R.id.menu_dice -> changeFragment(DiceFragment())
                R.id.menu_fight -> changeFragment(FightFragment.newInstance(game))
            }
            true
        }
    }

    private fun setObservers() {
        viewmodel?.gameUpdated?.observe(this, Observer {
            Toast.makeText(this, getString(R.string.game_updated), Toast.LENGTH_SHORT).show()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.save_nav, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_save_game -> {
                game?.let {
                    viewmodel?.saveGame(it)
                }
                true
            }
            R.id.menu_settings -> {
                changeFragment(SettingsFragment())
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun changeFragment(fragment: Fragment) {
        actuallyFragment = fragment
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {

        val count = supportFragmentManager.backStackEntryCount
        if(count > 1){
            super.onBackPressed()
            return
        }

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_custom)

        val title = dialog.findViewById(R.id.txtView_dialog_title) as TextView
        title.setText(this.getString(R.string.back_pressed_title))
        val body = dialog.findViewById(R.id.txtView_dialog_body) as TextView
        body.setText(this.getString(R.string.back_pressed_body))

        val yesBtn = dialog.findViewById(R.id.txtView_dialog_yes) as TextView
        val noBtn = dialog.findViewById(R.id.txtView_dialog_no) as TextView
        yesBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        noBtn.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    fun showBottomBar(show: Boolean) {
        navigation?.visibility = if(show) View.VISIBLE else View.GONE
    }
}