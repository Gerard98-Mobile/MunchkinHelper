package gerard.example.munchkinhelper.ui.activity


import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import gerard.example.munchkinhelper.Cfg
import gerard.example.munchkinhelper.MainActivity
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.core.dialogs.YesNoDialog
import gerard.example.munchkinhelper.ui.fragments.home.HomeFragment
import gerard.example.munchkinhelper.ui.fragments.settings.SettingsFragment
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.util.NavigationHelper
import gerard.example.munchkinhelper.util.SoundHelper
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.dialog_custom.*

val GAME_KEY = "game_key"

class GameActivity : AppCompatActivity() {

    var game : Game? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        Cfg.init(this)
        setToolbar()

        game = intent.getSerializableExtra(GAME_KEY) as Game?
        changeFragment(HomeFragment.newInstance(game))
    }

    fun setToolbar() {
        setToolbarTitle(getString(R.string.app_name))
        if(Cfg.autoSave.value.get() == true){
            toolbar_autosave.visibility = View.GONE
        }
        else{
            toolbar_autosave.visibility = View.VISIBLE
        }
        toolbar_settings.visibility = View.VISIBLE
        toolbar_back.visibility = View.GONE
        toolbar_settings.setOnClickListener { changeFragment(SettingsFragment.newInstance(game)) }
        toolbar_back.setOnClickListener { onBackPressed() }
    }

    private fun changeFragment(fragment: Fragment) {
        NavigationHelper.changeFragment(supportFragmentManager, fragment)
    }

    override fun onBackPressed() {

        val count = supportFragmentManager.backStackEntryCount
        if(count > 1){
            super.onBackPressed()
            return
        }

        YesNoDialog(
            this,
            R.string.back_pressed_title,
            R.string.back_pressed_body
        ){ value, _ ->
            when(value){
                true -> NavigationHelper.finish(this@GameActivity)
            }
        }.show()
    }

    fun setToolbarTitle(text: String){
        toolbar_text.text = text
    }

    fun hideToolbarButtons() {
        toolbar_back.visibility = View.VISIBLE
        toolbar_settings.visibility = View.GONE
        toolbar_autosave.visibility = View.GONE
    }


    override fun onPause() {
        super.onPause()
        game?.let { Cfg.lastGame.set(it) }
    }

}