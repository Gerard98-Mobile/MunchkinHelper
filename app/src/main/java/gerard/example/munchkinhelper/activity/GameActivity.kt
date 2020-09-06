package gerard.example.munchkinhelper.activity


import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import gerard.example.munchkinhelper.fragments.DiceFragment
import gerard.example.munchkinhelper.fragments.FightFragment
import gerard.example.munchkinhelper.fragments.GameFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import gerard.example.munchkinhelper.MainActivity
import gerard.example.munchkinhelper.R
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

        //actuallyFragment = GameFragment.newInstance(game)
        game?.let {
            actuallyFragment = GameFragment.newInstance(it)
            if (actuallyFragment != null) {
                val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frame, actuallyFragment!!)
                transaction.commit()
            }
        }


        navigation = findViewById(R.id.navigation)
        navigation!!.setOnNavigationItemSelectedListener {
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            when (it.itemId) {
                R.id.menu_game -> {
                    if (game != null) {
                        actuallyFragment = GameFragment.newInstance(game!!)
                        actuallyFragment?.let {
                            transaction.replace(R.id.frame, it)
                            transaction.commit()
                        }
                    }
                }
                R.id.menu_dice -> {
                    actuallyFragment = DiceFragment()
                    actuallyFragment?.let {
                        transaction.replace(R.id.frame, it)
                        transaction.commit()
                    }
                }
                R.id.menu_fight -> {
                    if (game != null) {
                        actuallyFragment = FightFragment.newInstance(game!!)
                        actuallyFragment?.let {
                            transaction.replace(R.id.frame, it)
                            transaction.commit()
                        }
                    }
                }
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

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {

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
}