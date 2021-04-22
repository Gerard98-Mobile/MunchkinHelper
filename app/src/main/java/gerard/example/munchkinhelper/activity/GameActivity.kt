package gerard.example.munchkinhelper.activity


import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import gerard.example.munchkinhelper.Cfg
import gerard.example.munchkinhelper.MainActivity
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.fragments.DiceFragment
import gerard.example.munchkinhelper.fragments.FightFragment
import gerard.example.munchkinhelper.fragments.GameFragment
import gerard.example.munchkinhelper.fragments.home.HomeFragment
import gerard.example.munchkinhelper.fragments.settings.SettingsFragment
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.viewmodels.GameVM
import kotlinx.android.synthetic.main.activity_game.*

val GAME_KEY = "game_key"

class GameActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        Cfg.init(this)
        setToolbar()

        val game = intent.getSerializableExtra(GAME_KEY) as Game?
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
        toolbar_settings.setOnClickListener { changeFragment(SettingsFragment()) }
        toolbar_back.setOnClickListener { onBackPressed() }
    }

    private fun changeFragment(fragment: Fragment) {
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

    fun setToolbarTitle(text: String){
        toolbar_text.setText(text)
    }

    fun hideToolbarButtons() {
        toolbar_back.visibility = View.VISIBLE
        toolbar_settings.visibility = View.GONE
        toolbar_autosave.visibility = View.GONE
    }

}